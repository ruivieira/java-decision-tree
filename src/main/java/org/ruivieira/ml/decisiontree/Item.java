package org.ruivieira.ml.decisiontree;

import org.ruivieira.ml.decisiontree.features.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Item {

    private final Map<String, Value> values = new HashMap<>();

    private Item() {

    }

    public static Item create() {
        return new Item();
    }

    public boolean contains(String attribute) {
        return values.containsKey(attribute);
    }

    public Value get(String attribute) {
        return values.get(attribute);
    }

    public void add(String attribute, Value value) {
        values.put(attribute, value);
    }

    public Set<String> getAttributes() {
        return values.keySet();
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(values, item.values);
    }

    public Item clone() {
        final Item clone = new Item();
        for (String key : values.keySet()) {
            clone.add(key, values.get(key).clone());
        }
        return clone;
    }
}
