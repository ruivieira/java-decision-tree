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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class RandomForest {

    private final List<DecisionTree> forest= new CopyOnWriteArrayList<>();

    private  RandomForest(TreeConfig config, int number, int samples, ImpurityEstimator estimator) {
        IntStream.range(0, number).parallel().forEach((i) -> {
            final TreeConfig clonedConfig = config.clone();
            clonedConfig.setData(Dataset.sample(config.getData(), samples));
            final DecisionTree tree = DecisionTree.create(clonedConfig, estimator);
            forest.add(tree);
        });
    }

    public Map<Object, Integer> predict(Item item) {
        Map<Object, Integer> result = new HashMap<>();
        for (DecisionTree tree : forest) {
            Object decision = tree.predict(item);
            if (decision != null) {
                Integer newValue = result.getOrDefault(decision, 0) + 1;
                result.put(decision, newValue);
            }
        }
        return result;
    }

    public static RandomForest create(TreeConfig config, int number, int samples) {
        return create(config, number, samples, new Entropy());
    }

    public static RandomForest create(TreeConfig config, int number, int samples, ImpurityEstimator estimator) {
        return new RandomForest(config, number, samples, estimator);
    }

}
