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

import java.util.Objects;

public class DoubleValue implements Value<Double> {

    private final Double data;

    public DoubleValue(Double data) {
        this.data = data;
    }

    @Override
    public boolean compare(Value other) {
        if (other instanceof DoubleValue) {
            return this.data >= (Double) other.getData() ;
        } else {
            return false;
        }
    }

    @Override
    public Value clone() {
        return new DoubleValue(data);
    }

    @Override
    public Double getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleValue that = (DoubleValue) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
