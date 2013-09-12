package example.ttd.currency;

import java.util.Hashtable;

class Bank {
    private final Hashtable<Pair, Integer> rates = new Hashtable<Pair, Integer>();

    public Money reduced(Expression source, String usd) {
        return source.reduce(this, usd);
    }

    public void addRate(String from, String to, int i) {
        rates.put(new Pair(from, to), i);
    }

    public int rate(String from, String to) {
        if(from.equals(to))
            return 1;

        Integer rate = rates.get(new Pair(from, to));
        if(rate != null)
            return rate;

        return 1;
    }
}
