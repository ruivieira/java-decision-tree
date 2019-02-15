/*
 * Copyright (c) 2018 Rui Vieira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ruivieira.ml.decisiontree;

import org.ruivieira.ml.decisiontree.entropy.Entropy;
import org.ruivieira.ml.decisiontree.entropy.ImpurityEstimator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class DecisionTree {

    private static final Logger staticLogger = LoggerFactory.getLogger(DecisionTree.class);

    private Object decision;
    private String attribute;
    private Object pivot;
    private DecisionTree trueBranch;
    private DecisionTree falseBranch;

    private DecisionTree() {

    }

    public static DecisionTree create(TreeConfig config) {
        return create(config, new Entropy());
    }

    public static DecisionTree create(TreeConfig config, ImpurityEstimator estimator) {
        staticLogger.debug("Creating decision tree with:");
        staticLogger.debug(config.toString());

        final Dataset data = config.getData();

        final DecisionTree tree = new DecisionTree();

        if ((config.getMaxDepth() == 0) || (data.size() <= config.getMinCount())) {
            tree.decision = data.valueFrequency(config.getDecision());
            return tree;
        }

        final String _attribute = config.getDecision();
        double entropy = estimator.impurity(_attribute, data);

        if (entropy <= config.getEntropyThreshold()) {
            tree.decision = data.valueFrequency(config.getDecision());
            return tree;
        }
        final Set<CacheEntry> cache = new HashSet<>();

        Split bestSplit = Split.create();

        for (Item item : data.getItems()) {

            for (String attribute : item.getAttributes()) {
                staticLogger.debug("Current attribute is " + attribute);
                if (attribute.equals(config.getDecision()) || config.isIgnored(attribute)) {
                    continue;
                }
                final Object pivot = item.get(attribute);
                final CacheEntry cacheEntry = CacheEntry.create(attribute, pivot);
                if (cache.contains(cacheEntry)) {
                    staticLogger.debug("Already checked.");
                    continue;
                }
                cache.add(cacheEntry);
                final Split split = data.calculateSplit(attribute, pivot);
                staticLogger.debug(split.toString());

                final double trueBranchEntropy = estimator.impurity(_attribute, split.getTrueBranch());
                final double falseBranchEntropy = estimator.impurity(_attribute, split.getFalseBranch());

                double newEntropy = 0.0;
                newEntropy += trueBranchEntropy * (double) split.getTrueBranch().size();
                newEntropy += falseBranchEntropy * (double) split.getFalseBranch().size();
                newEntropy /= (double) data.size();
                double currentGain = entropy - newEntropy;

                if (currentGain > bestSplit.gain) {
                    staticLogger.debug("currentGain = " + currentGain);
                    bestSplit = split;

                    bestSplit.setAttribute(attribute);
                    bestSplit.setPivot(pivot);
                    bestSplit.gain = currentGain;
                }
            }
        }

        if (bestSplit.gain > 0.0) {
            final int childMaxDepth = config.getMaxDepth() - 1;

            final TreeConfig trueBranchConfig = config.clone();
            trueBranchConfig.setMaxDepth(childMaxDepth);
            trueBranchConfig.setData(bestSplit.getTrueBranch());
            tree.trueBranch = create(trueBranchConfig, estimator);

            final TreeConfig falseBranchConfig = config.clone();
            falseBranchConfig.setMaxDepth(childMaxDepth);
            falseBranchConfig.setData(bestSplit.getFalseBranch());
            tree.falseBranch = create(falseBranchConfig, estimator);

            tree.attribute = bestSplit.getAttribute();
            tree.pivot = bestSplit.getPivot();

            return tree;

        } else {
            staticLogger.warn("Can't find best split.");
            tree.decision = data.valueFrequency(config.getDecision());
            return tree;
        }
    }

    public Object predict(Item item) {

        DecisionTree tree = this;

        while (true) {
            if (tree != null) {
                if (tree.decision != null) {
                    return tree.decision;
                }

                final String attribute = tree.attribute;

                final Object value = item.get(attribute);

                final Object pivot = tree.pivot;

                if (value != null && value.equals(pivot)) {
                    tree = tree.trueBranch;
                } else {
                    tree = tree.falseBranch;
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "DecisionTree{" +
                "decision=" + decision +
                ", attribute='" + attribute + '\'' +
                ", pivot=" + pivot +
                ", trueBranch=" + trueBranch +
                ", falseBranch=" + falseBranch +
                '}';
    }
}
