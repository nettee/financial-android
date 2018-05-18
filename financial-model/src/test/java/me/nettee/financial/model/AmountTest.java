package me.nettee.financial.model;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

public class AmountTest {

    private void assertAllEqual(Amount[] amounts) {
        for (Amount a1 : amounts) {
            for (Amount a2 : amounts) {
                Assert.assertEquals(a1, a2);
            }
        }
    }

    private int randomYuan() {
        return RandomUtils.nextInt(0, 200) - 100;
    }

    private int randomCent() {
        return RandomUtils.nextInt(0, 99);
    }

    private Amount randomAmount() {
        return Amount.decimal(randomYuan(), randomCent());
    }

    @Test
    public void testZero() {
        Amount[] amounts = {
                Amount.zero(),
                Amount.integer(0),
                Amount.decimal(0, 0),
                Amount.valueOf("0"),
                Amount.valueOf("0.00"),
        };
        assertAllEqual(amounts);
    }

    @Test
    public void testInteger() {
        int yuan = randomYuan();
        Amount[] amounts = {
                Amount.integer(yuan),
                Amount.decimal(yuan, 0),
                Amount.valueOf(String.format("%d", yuan)),
                Amount.valueOf(String.format("%d.00", yuan)),
        };
        assertAllEqual(amounts);
    }

    @Test
    public void testDecimal() {
        int yuan = randomYuan();
        int cent = randomCent();
        Amount[] amounts = {
                Amount.decimal(yuan, cent),
                Amount.valueOf(String.format("%d.%02d", yuan, cent)),
        };
        assertAllEqual(amounts);
    }

    @Test
    public void testAbsNeg() {

        Amount amount1 = randomAmount();
        Amount[] amounts = {amount1, amount1.neg()};

        for (Amount amount : amounts) {
            Assert.assertTrue(amount.isPositive() != amount.isNegative());
            Assert.assertTrue(amount.abs().isPositive());
            Assert.assertTrue(amount.abs().abs().isPositive());
            Assert.assertTrue(amount.isPositive() != amount.neg().isPositive());
            Assert.assertTrue(amount.isPositive() == amount.neg().neg().isPositive());
            Assert.assertTrue(amount.abs().neg().isNegative());
            Assert.assertTrue(amount.neg().abs().isPositive());

            if (amount.isPositive()) {
                Assert.assertEquals(amount, amount.abs());
            }

            Assert.assertEquals(amount, amount.neg().neg());
        }
    }

    @Test
    public void testAddSub() {
        Amount amount = randomAmount();
        Amount zero = Amount.zero();
        Amount one = Amount.integer(1);

        Assert.assertEquals(amount, amount.add(zero));
        Assert.assertEquals(amount, zero.add(amount));

        Assert.assertEquals(amount.neg(), zero.sub(amount));

        Assert.assertNotEquals(amount, amount.add(one));
        Assert.assertTrue(amount.compareTo(amount.add(one)) < 0);
        Assert.assertTrue(amount.compareTo(amount.sub(one)) > 0);
        Assert.assertEquals(amount, amount.add(one).sub(one));
        Assert.assertEquals(amount, amount.sub(one).add(one));

        Amount amount2 = randomAmount();
        Assert.assertEquals(amount.add(amount2), amount2.add(amount));
        Assert.assertEquals(amount.sub(amount2), amount2.sub(amount).neg());
    }

}
