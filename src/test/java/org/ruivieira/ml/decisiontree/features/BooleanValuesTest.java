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

package org.ruivieira.ml.decisiontree.features;

import org.junit.Assert;
import org.junit.Test;
import org.ruivieira.ml.decisiontree.Dataset;
import org.ruivieira.ml.decisiontree.Item;
import org.ruivieira.ml.decisiontree.RandomForest;
import org.ruivieira.ml.decisiontree.TreeConfig;

import java.util.Map;

public class BooleanValuesTest {

    @Test
    public void singleItemPrediction() {
        final Dataset dataset = Dataset.create();

        final Item item = Item.create();
        item.add("User", new StringValue("John"));
        item.add("Level", new IntegerValue(5));
        item.add("outcome", new BooleanValue(true));

        dataset.add(item);

        final Item question = Item.create();
        question.add("User", new StringValue("John"));
        question.add("Level", new IntegerValue(5));

        final TreeConfig config = TreeConfig.create();
        config.setDecision("outcome");
        config.setData(dataset);

        final RandomForest forest = RandomForest.create(config, 100, 1);
        final Map<Value, Integer> prediction = forest.predict(question);

        System.out.println(prediction);
        Assert.assertEquals(prediction.keySet().size(), 1);
        Assert.assertTrue(prediction.keySet().contains(new BooleanValue(true)));
        Assert.assertEquals(prediction.get(new BooleanValue(true)).intValue(), 100);
    }

    @Test
    public void TwoItemPrediction() {
        final Dataset dataset = Dataset.create();

        final Item item = Item.create();
        item.add("User", new StringValue("John"));
        item.add("Level", new IntegerValue(5));
        item.add("outcome", new BooleanValue(true));
        dataset.add(item);

        final Item item2 = Item.create();
        item2.add("User", new StringValue("John"));
        item2.add("Level", new IntegerValue(10));
        item2.add("outcome", new BooleanValue(false));
        dataset.add(item2);

        final Item question = Item.create();
        question.add("User", new StringValue("John"));
        question.add("Level", new IntegerValue(5));

        final TreeConfig config = TreeConfig.create();
        config.setDecision("outcome");
        config.setData(dataset);

        final RandomForest forest = RandomForest.create(config, 100, 1);
        final Map<Value, Integer> prediction = forest.predict(question);

        Assert.assertEquals(prediction.keySet().size(), 2);
    }

    @Test
    public void ThreeItemPrediction() {
        final Dataset dataset = Dataset.create();

        final Item item = Item.create();
        item.add("User", new StringValue("John"));
        item.add("Level", new IntegerValue(5));
        item.add("outcome", new BooleanValue(true));
        dataset.add(item);

        final Item item2 = Item.create();
        item2.add("User", new StringValue("John"));
        item2.add("Level", new IntegerValue(10));
        item2.add("outcome", new BooleanValue(false));
        dataset.add(item2);

        final Item item3 = Item.create();
        item2.add("User", new StringValue("John"));
        item2.add("Level", new IntegerValue(5));
        item2.add("outcome", new BooleanValue(true));
        dataset.add(item3);

        final Item question = Item.create();
        question.add("User", new StringValue("John"));
        question.add("Level", new IntegerValue(5));

        final TreeConfig config = TreeConfig.create();
        config.setDecision("outcome");
        config.setData(dataset);

        final RandomForest forest = RandomForest.create(config, 100, 1);
        final Map<Value, Integer> prediction = forest.predict(question);
        System.out.println(prediction);
        Assert.assertEquals(prediction.keySet().size(), 1);
        Assert.assertTrue(prediction.get(new BooleanValue(true)) > 50);

    }

}
