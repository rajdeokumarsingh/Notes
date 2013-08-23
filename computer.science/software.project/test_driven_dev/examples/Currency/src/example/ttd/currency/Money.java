package example.ttd.currency;

public abstract class Money {

    // TODO: amount must be an integer?
    protected int amount;

    static Money dollar(int amount) {
        return new Dollar(amount);
    }

    public static Franc franc(int amount) {
        return new Franc(amount);
    }

    // TODO: equals with different money types
    @Override
    public boolean equals(Object o) {
        Debug.logVerbose("enter Money.equals()");
        if (this == o) return true;
        if (!(o instanceof Money)) return false;

        if(!this.getClass().equals(o.getClass())) return false;

        Money money = (Money) o;

        Debug.logVerbose("Money.equals() 2");
        if (amount != money.amount) return false;

        Debug.logVerbose("Money.equals() 3");
        return true;
    }

    @Override
    public int hashCode() {
        return amount;
    }

    abstract public Money times(int i);


}
