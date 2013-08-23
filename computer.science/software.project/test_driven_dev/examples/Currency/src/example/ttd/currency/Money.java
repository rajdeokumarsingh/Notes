package example.ttd.currency;

public class Money implements Expression {
    // TODO: amount must be an integer?
    protected int amount;

    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    // TODO: normal multiple
    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public int getAmount() {
        return amount;
    }

    public String currency() {
        return currency;
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

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Expression plus(Money added) {
        return new Money(this.amount + added.amount, currency);
    }
}
