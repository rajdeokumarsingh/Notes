package example.ttd.currency;

import junit.framework.TestCase;

public class FrancTest extends TestCase {
    public void testMultiplication() throws Exception {
        Franc five = new Franc(5);
        assertEquals(new Franc(10), five.times(2));
        assertEquals(new Franc(15), five.times(3));
    }

    /**
     * forward test, backward test
     */
    public void testEquality() throws Exception {
        assertTrue(new Franc(5).equals(new Franc(5)));
        assertFalse(new Franc(5).equals(new Franc(6)));
    }
}
