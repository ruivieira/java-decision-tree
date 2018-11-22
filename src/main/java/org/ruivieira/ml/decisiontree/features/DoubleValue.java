package org.ruivieira.ml.decisiontree.features;

public class DoubleValue implements Value<Double> {

    private final Double data;

    public DoubleValue(Double data) {
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
    public Double getData() {
        return data;
    }
}
