/*
 * Copyright (c) 2019 Rui Vieira
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

import org.junit.Before;
import org.junit.Test;
import org.ruivieira.ml.decisiontree.Dataset;
import org.ruivieira.ml.decisiontree.Item;
import org.ruivieira.ml.decisiontree.RandomForest;
import org.ruivieira.ml.decisiontree.TreeConfig;
import org.ruivieira.ml.decisiontree.features.helpers.*;

import java.util.Map;

public class ComplexObjects {

    final Person anna = new Person("Anna", "Smith");
    final Person bill = new Person("Bill", "Williams");
    final Person claire = new Person("Claire", "Miller");
    final Person dennis = new Person("Dennis", "Wilson");
    private Dataset data;

    @Before
    public void setUp() {
        data = Dataset.create();

        Item item1 = Item.create();
        item1.add("user", anna);
        item1.add("purchase", new Purchase(Brand.LENOVO, Department.ENGINEERING, Country.US));
        data.add(item1);

        Item item2 = Item.create();
        item2.add("user", anna);
        item2.add("purchase", new Purchase(Brand.APPLE, Department.MARKETING, Country.US));
        data.add(item2);

        Item item3 = Item.create();
        item3.add("user", bill);
        item3.add("purchase", new Purchase(Brand.LENOVO, Department.ENGINEERING, Country.US));
        data.add(item3);


        Item item4 = Item.create();
        item4.add("user", bill);
        item4.add("purchase", new Purchase(Brand.APPLE, Department.MARKETING, Country.US));
        data.add(item4);

        Item item5 = Item.create();
        item5.add("user", claire);
        item5.add("purchase", new Purchase(Brand.LENOVO, Department.ENGINEERING, Country.US));
        data.add(item5);

        Item item6 = Item.create();
        item6.add("user", claire);
        item6.add("purchase", new Purchase(Brand.LENOVO, Department.MARKETING, Country.US));
        data.add(item6);

        Item item7 = Item.create();
        item7.add("user", dennis);
        item7.add("purchase", new Purchase(Brand.APPLE, Department.ENGINEERING, Country.US));
        data.add(item7);

        Item item8 = Item.create();
        item8.add("user", dennis);
        item8.add("purchase", new Purchase(Brand.APPLE, Department.MARKETING, Country.US));
        data.add(item8);
    }

    @Test
    public void singleItemPrediction() {
        TreeConfig config = TreeConfig.create();
        config.setData(data);
        config.setDecision("purchase");

        final Item question = Item.create();
        question.add("user", claire);

        final RandomForest forest = RandomForest.create(config, 100, 4);
        final Map<Object, Integer> prediction = forest.predict(question);
        System.out.println(prediction);
    }
}