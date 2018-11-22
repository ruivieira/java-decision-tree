package org.ruivieira.ml.decisiontree;

import org.ruivieira.ml.decisiontree.features.Value;

class Split {
    double gain = 0.0; // default gain
    private Dataset trueBranch;
    private Dataset falseBranch;
    private String attribute;
    private Value pivot;

    private Split(Dataset trueBranch, Dataset falseBranch) {
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    private Split() {

    }

    public static Split create(Dataset trueBranch, Dataset falseBranch) {
        return new Split(trueBranch, falseBranch);
    }

    public static Split create() {
        return new Split();
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Value getPivot() {
        return pivot;
    }

    public void setPivot(Value pivot) {
        this.pivot = pivot;
    }

    public Dataset getTrueBranch() {
        return trueBranch;
    }

    public Dataset getFalseBranch() {
        return falseBranch;
    }

    @Override
    public String toString() {
        return "Split{" +
                "trueBranch=" + trueBranch +
                ", falseBranch=" + falseBranch +
                ", attribute='" + attribute + '\'' +
                ", pivot=" + pivot +
                ", gain=" + gain +
                '}';
    }
}
