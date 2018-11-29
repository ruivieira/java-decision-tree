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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeConfig {

    private int minCount = 1;
    private String decision = "category";
    private double entropyThreshold = 0.01;
    private int maxDepth = 70;
    private Dataset data;
    private Set<String> ignore = new HashSet<>();

    private TreeConfig() {

    }

    public static TreeConfig create() {
        return new TreeConfig();
    }

    @Override
    protected TreeConfig clone() {
        final TreeConfig clone = new TreeConfig();
        clone.minCount = this.minCount;
        clone.decision = this.decision;
        clone.entropyThreshold = this.entropyThreshold;
        clone.maxDepth = this.maxDepth;
        clone.data = this.data.clone();
        clone.ignore = new HashSet<>();
        clone.ignore.addAll(this.ignore);
        return clone;
    }

    @Override
    public String toString() {
        return "TreeConfig{" +
                "minCount=" + minCount +
                ", decision='" + decision + '\'' +
                ", entropyThreshold=" + entropyThreshold +
                ", maxDepth=" + maxDepth +
                ", data=" + data +
                ", ignore=" + ignore +
                '}';
    }

    public Dataset getData() {
        return data;
    }

    public void setData(Dataset data) {
        this.data = data;
    }

    public Set<String> getIgnore() {
        return ignore;
    }

    public TreeConfig setIgnore(List<String> att) {
        this.ignore.addAll(att);
        return this;
    }

    public boolean isIgnored(String attribute) {
        return ignore.contains(attribute);
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public double getEntropyThreshold() {
        return entropyThreshold;
    }

    public void setEntropyThreshold(double entropyThreshold) {
        this.entropyThreshold = entropyThreshold;
    }

    public String getDecision() {
        return decision;
    }

    public TreeConfig setDecision(String decision) {
        this.decision = decision;
        return this;
    }

    public int getMinCount() {
        return this.minCount;
    }

    public TreeConfig setMinCount(int value) {
        this.minCount = value;
        return this;
    }
}
