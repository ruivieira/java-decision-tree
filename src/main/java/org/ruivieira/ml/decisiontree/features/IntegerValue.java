package org.ruivieira.ml.decisiontree.features;

public class IntegerValue implements Value<Integer> {

    private final Integer data;

    public IntegerValue(Integer data) {
        this.data = data;
    }

    @Override
    public boolean compare(Value other) {
        return data >= (Integer) other.getData();
    }

    @Override
    public Value clone() {
        return null;
    }

    @Override
    public Integer getData() {
        return data;
    }
}
