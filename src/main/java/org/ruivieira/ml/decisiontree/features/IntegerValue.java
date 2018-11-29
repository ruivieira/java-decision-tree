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
