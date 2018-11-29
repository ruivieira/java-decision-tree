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

import org.ruivieira.ml.decisiontree.features.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class DecisionTree {

    private static final Logger staticLogger = LoggerFactory.getLogger(DecisionTree.class);

    private Value decision;
    private String attribute;
    private Value pivot;
    private DecisionTree trueBranch;
    private DecisionTree falseBranch;

    private DecisionTree() {

    }

    public static DecisionTree create(TreeConfig config) {
        staticLogger.debug("Creating decision tree with:");
        staticLogger.debug(config.toString());

        final Dataset data = config.getData();

        final DecisionTree tree = new DecisionTree();

        if ((config.getMaxDepth() == 0) || (data.size() <= config.getMinCount())) {
            tree.decision = data.valueFrequency(config.getDecision());
            return tree;
        }

        double entropy = data.entropy(config.getDecision());

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
                final Value pivot = item.get(attribute);
                final CacheEntry cacheEntry = CacheEntry.create(attribute, pivot);
                if (cache.contains(cacheEntry)) {
                    staticLogger.debug("Already checked.");
                    continue;
                }
                cache.add(cacheEntry);
                final Split split = data.calculateSplit(attribute, pivot);
                staticLogger.debug(split.toString());
                final double trueBranchEntropy = split.getTrueBranch().entropy(config.getDecision());
                final double falseBranchEntropy = split.getFalseBranch().entropy(config.getDecision());

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
            tree.trueBranch = create(trueBranchConfig);

            final TreeConfig falseBranchConfig = config.clone();
            falseBranchConfig.setMaxDepth(childMaxDepth);
            falseBranchConfig.setData(bestSplit.getFalseBranch());
            tree.falseBranch = create(falseBranchConfig);

            tree.attribute = bestSplit.getAttribute();
            tree.pivot = bestSplit.getPivot();

            return tree;

        } else {
            staticLogger.warn("Can't find best split.");
            tree.decision = data.valueFrequency(config.getDecision());
            return tree;
        }
    }

    public Value predict(Item item) {

        DecisionTree tree = this;

        while (true) {
            if (tree != null) {
                if (tree.decision != null) {
                    return tree.decision;
                }

                final String attribute = tree.attribute;

                final Value value = item.get(attribute);

                final Value pivot = tree.pivot;

                if (value != null && pivot != null && value.compare(pivot)) {
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
