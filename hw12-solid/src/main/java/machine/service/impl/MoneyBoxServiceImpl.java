package machine.service.impl;

import machine.data.MoneyBox;
import machine.service.MoneyBoxService;

public class MoneyBoxServiceImpl implements MoneyBoxService {

    public int checkSum(MoneyBox moneyBox) {
        return moneyBox.getNote() * moneyBox.getNominal().getVal();
    }

    public void putMoney(MoneyBox moneyBox, Integer note) {
        if (moneyBox == null) {
            throw new IllegalStateException("No money box");
        }
        moneyBox.setNote(moneyBox.getNote() + note);
    }

    public int getMoney(MoneyBox moneyBox, Integer sum) {

        int boxNominal = moneyBox.getNominal().getVal();
        int result = 0;
        if (sum >= boxNominal) {
            int requiredNotes = sum / boxNominal;
            result = Math.min(requiredNotes, moneyBox.getNote());
        }

        if ((sum < boxNominal) & result==0) {
            throw new IllegalStateException("There is no change note for " + sum);
        }

        moneyBox.setNote(moneyBox.getNote() - result);
        return result;
    }
}
