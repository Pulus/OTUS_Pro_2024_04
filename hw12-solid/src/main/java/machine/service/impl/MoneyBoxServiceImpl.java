package machine.service.impl;

import machine.data.MoneyBox;
import machine.service.MoneyBoxService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MoneyBoxServiceImpl implements MoneyBoxService {


    @Override
    public int checkSum(MoneyBox moneyBox) {
        return moneyBox.getNote100() * NOMINAL100 +
                moneyBox.getNote500() * NOMINAL500 +
                moneyBox.getNote1000() * NOMINAL1000 +
                moneyBox.getNote5000() * NOMINAL5000;
    }

    @Override
    public void putMoney(MoneyBox moneyBox, int note100, int note500, int note1000, int note5000) {
        if (moneyBox == null) {
            throw new IllegalStateException("No money box");
        }
        moneyBox.setNote100(moneyBox.getNote100() + note100);
        moneyBox.setNote500(moneyBox.getNote500() + note500);
        moneyBox.setNote1000(moneyBox.getNote1000() + note1000);
        moneyBox.setNote5000(moneyBox.getNote5000() + note5000);
    }

    @Override
    public List<Integer> getMoney(MoneyBox moneyBox, int sum) {
        List<Integer> result = new ArrayList<>(Arrays.asList(0, 0, 0, 0));

        if (sum > checkSum(moneyBox)) {
            throw new IllegalStateException("Not enough money");
        }

        if (sum % 100 != 0) {
            throw new IllegalStateException("Can't charge the required sum");
        }

        int chargedNotes;
        int requiredNotes;

        if (sum >= NOMINAL5000) {
            requiredNotes = sum / NOMINAL5000;
            chargedNotes = Math.min(requiredNotes, moneyBox.getNote5000());
            sum -= chargedNotes * NOMINAL5000;
            result.set(0, chargedNotes);
        }

        if (sum >= NOMINAL1000) {
            requiredNotes = sum / NOMINAL1000;
            chargedNotes = Math.min(requiredNotes, moneyBox.getNote1000());
            sum -= chargedNotes * NOMINAL1000;
            result.set(1, chargedNotes);
        }

        if (sum >= NOMINAL500) {
            requiredNotes = sum / NOMINAL500;
            chargedNotes = Math.min(requiredNotes, moneyBox.getNote500());
            sum -= chargedNotes * NOMINAL500;
            result.set(2, chargedNotes);
        }

        if (sum >= NOMINAL100) {
            requiredNotes = sum / NOMINAL100;
            chargedNotes = Math.min(requiredNotes, moneyBox.getNote100());
            sum -= chargedNotes * NOMINAL100;
            result.set(3, chargedNotes);
        }

        if (sum > 0) {
            throw new IllegalStateException("Not enough notes");
        }

        moneyBox.setNote5000(moneyBox.getNote5000() - result.get(0));
        moneyBox.setNote1000(moneyBox.getNote1000() - result.get(1));
        moneyBox.setNote500(moneyBox.getNote500() - result.get(2));
        moneyBox.setNote100(moneyBox.getNote100() - result.get(3));

        return result;
    }
}
