/*
 * Copyright (c) 2019 Rui Vieira
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

package org.ruivieira.ml.decisiontree.features.helpers;

import java.util.Objects;

public class Purchase {

    private final Brand brand;
    private final Department department;
    private final Country country;

    public Purchase(Brand brand, Department department, Country country) {

        this.brand = brand;
        this.department = department;
        this.country = country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, department, country);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return brand == purchase.brand &&
                department == purchase.department &&
                country == purchase.country;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "brand=" + brand +
                ", department=" + department +
                ", country=" + country +
                '}';
    }
}
