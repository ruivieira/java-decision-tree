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

package org.ruivieira.ml.decisiontree.entropy;

import org.ruivieira.ml.decisiontree.Dataset;

public class Entropy implements ImpurityEstimator {

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    @Override
    public double impurity(String attribute, Dataset dataset) {
        double impurity = 0.0;
        for (Integer i : dataset.getUniqueValues(attribute).values()) {
            double p = (double) i / (double) dataset.size();
            impurity += -p * log2(p);
        }
        return impurity;
    }
}
