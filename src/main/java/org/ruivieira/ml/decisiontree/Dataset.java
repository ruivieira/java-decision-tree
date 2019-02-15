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

    public Object valueFrequency(String attribute) {
        final Map<Object, Integer> counter = getUniqueValues(attribute);

        int mostFrequentCount = 0;
        Object mostFrequentValue = null;

        for (Object value : counter.keySet()) {
            if (counter.get(value) > mostFrequentCount) {
                mostFrequentCount = counter.get(value);
                mostFrequentValue = value;
            }
        }

        return mostFrequentValue;
    }

    public Map<Object, Integer> getUniqueValues(String attribute) {
        final Map<Object, Integer> counter = new HashMap<>();

        for (Item item : items) {
            if (item.contains(attribute)) {
                final Object value = item.get(attribute);
                counter.put(value, counter.getOrDefault(value, 0) + 1);
            }
        }
        return counter;
    }

    public Split calculateSplit(String attribute, Object pivot) {

        final Dataset trueBranch = new Dataset();
        final Dataset falseBranch = new Dataset();

        for (Item item : items) {
            final Object value = item.get(attribute);

            if (value.equals(pivot)) {
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
