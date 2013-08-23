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

        // TODO:
        // assertFalse(Money.dollar(5).equals(Money.franc(5)));
    }

    public void testCurrency() throws Exception {
        assertEquals(Money.dollar(1).currency(), "USD");
        assertEquals(Money.franc(1).currency(), "CHF");
    }

    public void testSimpleAddiction() throws Exception {
        Money five = Money.dollar(5);
        Expression sum = five.plus(five);
        Bank bank = new Bank();
        Money reduced = bank.reduced(sum, "USD");
        assertEquals(reduced, Money.dollar(10));
    }
}
