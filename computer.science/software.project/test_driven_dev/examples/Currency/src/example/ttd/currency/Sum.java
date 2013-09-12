package example.ttd.currency;

public class Sum implements Expression {
    private final Expression augend;
    private final Expression addend;

    public Sum(Expression augend, Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }

    public Money reduce(Bank bank, String to) {
        Money money1 = bank.reduced(augend, to);
        Money money2 = bank.reduced(addend, to);
        return new Money(money1.getAmount() + money2.getAmount(), to);
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Expression times(int i) {
        return new Sum(augend.times(i), addend.times(i));
    }
}
