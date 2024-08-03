package machine.data;

import lombok.Getter;

import java.util.*;

@Getter
public class CashMachine {
    private final Set<MoneyBox> moneyBoxes = new TreeSet<>(new LengthComparator());

    public CashMachine() {
        for (Nominal nominal : Nominal.values()) {
            this.moneyBoxes.add(new MoneyBox(nominal));
        }
    }

    static class LengthComparator implements Comparator<MoneyBox> {
        @Override
        public int compare(MoneyBox s1, MoneyBox s2) {
            return Integer.compare(s2.getNominal().getVal(), s1.getNominal().getVal());
        }
    }

    public MoneyBox getMoneyBox(Nominal nominal) {
        return moneyBoxes.stream().filter(p -> p.getNominal() == nominal).findFirst().get();
    }
}
