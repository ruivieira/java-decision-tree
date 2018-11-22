package org.ruivieira.ml.decisiontree;

import org.ruivieira.ml.decisiontree.features.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomForest {

    private final List<DecisionTree> forest= new ArrayList<>();

    private  RandomForest(TreeConfig config, int number, int samples) {
        for (int i = 0 ; i < number ; i++) {
            final TreeConfig clonedConfig = config.clone();
            clonedConfig.setData(Dataset.sample(config.getData(), samples));
            final DecisionTree tree = DecisionTree.create(clonedConfig);
            forest.add(tree);
        }
    }

    public Map<Value, Integer> predict(Item item) {
        Map<Value, Integer> result = new HashMap<>();
        for (DecisionTree tree : forest) {
            Value decision = tree.predict(item);
            if (decision != null) {
                Integer newValue = result.getOrDefault(decision, 0) + 1;
                result.put(decision, newValue);
            }
        }
        return result;
    }

    public static RandomForest create(TreeConfig config, int number, int samples) {
        return new RandomForest(config, number, samples);
    }
}
