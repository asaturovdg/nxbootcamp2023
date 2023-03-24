package ru.wostarnn;

public class AbonentWithExtraMinutes extends Abonent{
        private long ExtraMinutes;

    public AbonentWithExtraMinutes(String phoneNumber, String tariff, long ExtraMinutes) {
        super(phoneNumber, tariff);
        this.ExtraMinutes = ExtraMinutes;
        if (tariff.equals("06")) {
            this.addTotalRoubles(100);
        }
    }

    public void substractExtraMinutes(long amount) {
        this.ExtraMinutes -= amount;
    }

    public long getExtraMinutes() {
        return ExtraMinutes;
    }
}
