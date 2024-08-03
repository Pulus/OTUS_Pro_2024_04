package machine.service;

import machine.data.MoneyBox;

public interface MoneyBoxService {

    int checkSum(MoneyBox moneyBox);

    void putMoney(MoneyBox moneyBox, Integer note);

    int getMoney(MoneyBox moneyBox, Integer sum);
}
