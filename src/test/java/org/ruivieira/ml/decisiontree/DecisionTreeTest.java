package org.ruivieira.ml.decisiontree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ruivieira.ml.decisiontree.features.DoubleValue;
import org.ruivieira.ml.decisiontree.features.StringValue;
import org.ruivieira.ml.decisiontree.features.Value;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DecisionTreeTest {

    private Dataset data;
    private Dataset dataWithPrice;

    @Before
    public void setUp() {
        data = Dataset.create();

        Item item1 = Item.create();
        item1.add("user", new StringValue("Anna"));
        item1.add("department", new StringValue("engineering"));
        item1.add("office", new StringValue("US"));
        item1.add("brand", new StringValue("Lenovo"));
        data.add(item1);

        Item item2 = Item.create();
        item2.add("user", new StringValue("Anna"));
        item2.add("department", new StringValue("design"));
        item2.add("office", new StringValue("US"));
        item2.add("brand", new StringValue("Apple"));
        data.add(item2);

        Item item3 = Item.create();
        item3.add("user", new StringValue("Bill"));
        item3.add("department", new StringValue("engineering"));
        item3.add("office", new StringValue("US"));
        item3.add("brand", new StringValue("Lenovo"));
        data.add(item3);


        Item item4 = Item.create();
        item4.add("user", new StringValue("Bill"));
        item4.add("department", new StringValue("design"));
        item4.add("office", new StringValue("US"));
        item4.add("brand", new StringValue("Apple"));
        data.add(item4);

        Item item5 = Item.create();
        item5.add("user", new StringValue("Claire"));
        item5.add("department", new StringValue("engineering"));
        item5.add("office", new StringValue("US"));
        item5.add("brand", new StringValue("Lenovo"));
        data.add(item5);

        Item item6 = Item.create();
        item6.add("user", new StringValue("Claire"));
        item6.add("department", new StringValue("design"));
        item6.add("office", new StringValue("US"));
        item6.add("brand", new StringValue("Lenovo"));
        data.add(item6);

        Item item7 = Item.create();
        item7.add("user", new StringValue("Dennis"));
        item7.add("department", new StringValue("engineering"));
        item7.add("office", new StringValue("US"));
        item7.add("brand", new StringValue("Apple"));
        data.add(item7);

        Item item8 = Item.create();
        item8.add("user", new StringValue("Dennis"));
        item8.add("department", new StringValue("design"));
        item8.add("office", new StringValue("US"));
        item8.add("brand", new StringValue("Apple"));
        data.add(item8);

        dataWithPrice = Dataset.create();

        Item item1p = Item.create();
        item1p.add("user", new StringValue("Anna"));
        item1p.add("department", new StringValue("engineering"));
        item1p.add("office", new StringValue("US"));
        item1p.add("brand", new StringValue("Lenovo"));
        item1p.add("price", new DoubleValue(1500.0));
        dataWithPrice.add(item1p);

        Item item2p = Item.create();
        item2p.add("user", new StringValue("Anna"));
        item2p.add("department", new StringValue("design"));
        item2p.add("office", new StringValue("US"));
        item2p.add("brand", new StringValue("Apple"));
        item2p.add("price", new DoubleValue(2500.0));
        dataWithPrice.add(item2p);

        Item item3p = Item.create();
        item3p.add("user", new StringValue("Bill"));
        item3p.add("department", new StringValue("engineering"));
        item3p.add("office", new StringValue("US"));
        item3p.add("brand", new StringValue("Lenovo"));
        item3p.add("price", new DoubleValue(1630.0));
        dataWithPrice.add(item3p);


        Item item4p = Item.create();
        item4p.add("user", new StringValue("Bill"));
        item4p.add("department", new StringValue("design"));
        item4p.add("office", new StringValue("US"));
        item4p.add("brand", new StringValue("Apple"));
        item4p.add("price", new DoubleValue(2640.0));
        dataWithPrice.add(item4p);

        Item item5p = Item.create();
        item5p.add("user", new StringValue("Claire"));
        item5p.add("department", new StringValue("engineering"));
        item5p.add("office", new StringValue("US"));
        item5p.add("brand", new StringValue("Lenovo"));
        item5p.add("price", new DoubleValue(1590.0));
        dataWithPrice.add(item5p);

        Item item6p = Item.create();
        item6p.add("user", new StringValue("Claire"));
        item6p.add("department", new StringValue("design"));
        item6p.add("office", new StringValue("US"));
        item6p.add("brand", new StringValue("Lenovo"));
        item6p.add("price", new DoubleValue(1690.0));
        dataWithPrice.add(item6p);

        Item item7p = Item.create();
        item7p.add("user", new StringValue("Dennis"));
        item7p.add("department", new StringValue("engineering"));
        item7p.add("office", new StringValue("US"));
        item7p.add("brand", new StringValue("Apple"));
        item7p.add("price", new DoubleValue(2580.0));
        dataWithPrice.add(item7p);

        Item item8p = Item.create();
        item8p.add("user", new StringValue("Dennis"));
        item8p.add("department", new StringValue("design"));
        item8p.add("office", new StringValue("US"));
        item8p.add("brand", new StringValue("Apple"));
        item8p.add("price", new DoubleValue(2587.0));
        dataWithPrice.add(item8p);

    }


    @Test
    public void calculateValueFrequency() {
        Value brand = data.valueFrequency("brand");
        System.out.println(brand);
        Assert.assertEquals(new StringValue("Lenovo"), brand);
    }

    @Test
    public void countUniqueValues() {
        Map<Value, Integer> department = data.getUniqueValues("department");
        System.out.println(department);
        assertEquals(2, department.size());
        assertEquals(new Integer(4), department.get(new StringValue("design")));

    }

    @Test
    public void entropy() {
        double entropyUser = data.entropy("user");
        assertEquals(2.0, entropyUser, 1e-9);
    }

    @Test
    public void buildDecisionTree() {
    }

    @Test
    public void testAttribute() {
        TreeConfig config = TreeConfig.create();
        config.setData(data);
        config.setDecision("brand");
        DecisionTree dt = DecisionTree.create(config);

        final Item question = Item.create();
        question.add("department", new StringValue("engineering"));

        System.out.println(dt.predict(question));
    }

    @Test
    public void randomForest() {
        TreeConfig config = TreeConfig.create();
        config.setData(data);
        config.setDecision("department");

        final Item question = Item.create();
        question.add("brand", new StringValue("Apple"));

        final RandomForest forest = RandomForest.create(config, 10, 4);

        System.out.println(forest.predict(question));
    }

    @Test
    public void randomForestPrice() {
        TreeConfig config = TreeConfig.create();
        config.setData(dataWithPrice);
        config.setDecision("department");

        final Item question = Item.create();
        question.add("price", new DoubleValue(2600.0));

        final RandomForest forest = RandomForest.create(config, 10, 4);

        System.out.println(forest.predict(question));
    }

}