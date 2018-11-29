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

import java.util.Objects;

public class CacheEntry {

    private String attribute;
    private Value value;

    private CacheEntry(String attribute, Value value) {
        this.attribute = attribute;
        this.value = value;
    }

    public static CacheEntry create(String attribute, Value value) {
        return new CacheEntry(attribute, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheEntry that = (CacheEntry) o;
        return Objects.equals(attribute, that.attribute) &&
                Objects.equals(value, that.value);
    }
}
