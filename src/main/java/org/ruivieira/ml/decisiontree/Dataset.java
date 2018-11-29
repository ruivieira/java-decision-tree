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

import java.util.*;

public class Dataset {

    private final List<Item> items = new ArrayList<>();

    private Dataset() { }

    public static Dataset create() {
        return new Dataset();
    }

    public List<Item> getItems() {
        return items;
    }

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    public void add(Item item) {
        items.add(item);
    }

    public int size() {
        return items.size();
    }

    public Dataset clone() {
        final Dataset clone = new Dataset();
        for (Item entry : items) {
            final Item clonedItem = entry.clone();
            clone.add(clonedItem);
        }
        return clone;
    }

    public Value valueFrequency(String attribute) {
        final Map<Value, Integer> counter = getUniqueValues(attribute);

        int mostFrequentCount = 0;
        Value mostFrequentValue = null;

        for (Value value : counter.keySet()) {
            if (counter.get(value) > mostFrequentCount) {
                mostFrequentCount = counter.get(value);
                mostFrequentValue = value;
            }
        }

        return mostFrequentValue;
    }

    public Map<Value, Integer> getUniqueValues(String attribute) {
        final Map<Value, Integer> counter = new HashMap<>();

        for (Item item : items) {
            if (item.contains(attribute)) {
                final Value value = item.get(attribute);
                counter.put(value, counter.getOrDefault(value, 0) + 1);
            }
        }
        return counter;
    }

    public double entropy(String attribute) {
        final Map<Value, Integer> counter = getUniqueValues(attribute);
        final double size = (double) items.size();
        double entropy = 0.0;

        for (Integer i : counter.values()) {
            double p = (double) i / size;
            entropy += -p * log2(p);
        }

        return entropy;
    }

    public Split calculateSplit(String attribute, Value pivot) {

        final Dataset trueBranch = new Dataset();
        final Dataset falseBranch = new Dataset();

        for (Item item : items) {
            final Value value = item.get(attribute);

            if (value.compare(pivot)) {
                trueBranch.add(item);
            } else {
                falseBranch.add(item);
            }
        }
        return Split.create(trueBranch, falseBranch);
    }

    public static synchronized Dataset sample(Dataset dataset, int n) {
        final Dataset clone = dataset.clone();
        final List<Item> items = clone.getItems();
        Collections.shuffle(items);
        final Dataset newds = Dataset.create();
        final List<Item> nItems = items.subList(0, n);
        for (Item item : nItems) {
            newds.add(item);
        }
        return newds;
    }
}
