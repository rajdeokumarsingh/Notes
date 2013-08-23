package example.ttd.currency;

// TODO: repetitive code between Dollar and Franc
public class Franc extends Money {

    public Franc(int amount) {
        this.amount = amount;
    }

    public Money times(int multiplier) {
        return new Franc(amount * multiplier);
    }

    public int getAmount() {
        return amount;
    }
}
