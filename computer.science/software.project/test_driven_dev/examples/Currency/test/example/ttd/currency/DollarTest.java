package example.ttd.currency;

import junit.framework.TestCase;

public class DollarTest extends TestCase {
    public void testMultiplication() throws Exception {
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
    }

    /**
     * forward test, backward test
     */
    public void testEquality() throws Exception {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertTrue(Money.franc(5).equals(Money.franc(5)));
        assertFalse(Money.franc(5).equals(Money.franc(6)));

        assertFalse(Money.dollar(5).equals(Money.franc(5)));
    }

    public void testCurrency() throws Exception {
        assertEquals(Money.dollar(1).currency(), "USD");
        assertEquals(Money.franc(1).currency(), "CHF");
    }

    public void testSimpleAddiction() throws Exception {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        Bank bank = new Bank();
        Money reduced = bank.reduced(sum, "USD");
        assertEquals(reduced, Money.dollar(10));
    }

    public void testReduced() throws Exception {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money reduced = bank.reduced(sum, "USD");
        assertEquals(reduced, Money.dollar(7));
    }

    public void testReducedMoney() throws Exception {
        Bank bank = new Bank();
        Money reduced = bank.reduced(Money.dollar(1), "USD");
        assertEquals(reduced, Money.dollar(1));
    }

    public void testReduceMoneyDifferentCurrency() throws Exception {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money reduced = bank.reduced(Money.franc(2), "USD");
        assertEquals(reduced, Money.dollar(1));
     }

    public void testMixedAddition() throws Exception {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money reduced = bank.reduced(fiveBucks.plus(tenFrancs), "USD");
        assertEquals(reduced, Money.dollar(10));
    }

    public void testSumPlugMoney() throws Exception {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        Money reduced = bank.reduced(sum, "USD");
        assertEquals(reduced, Money.dollar(15));
    }

    public void testSumTimes() throws Exception {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
        Money reduced = bank.reduced(sum, "USD");
        assertEquals(reduced, Money.dollar(20));
    }
}
