package org.ruivieira.ml.decisiontree.features;

public class FloatValue implements Value<Float> {

    private final Float data;

    public FloatValue(Float data) {
        this.data = data;
    }

    @Override
    public boolean compare(Value other) {
        return false;
    }

    @Override
    public Value clone() {
        return null;
    }

    @Override
    public Float getData() {
        return data;
    }
}
