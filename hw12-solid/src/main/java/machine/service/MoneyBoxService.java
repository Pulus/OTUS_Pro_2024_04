package machine.service;

import machine.data.MoneyBox;

import java.util.List;


public interface MoneyBoxService {
    int NOMINAL100 = 100;
    int NOMINAL500 = 500;
    int NOMINAL1000 = 1000;
    int NOMINAL5000 = 5000;
    int checkSum(MoneyBox moneyBox);

    void putMoney(MoneyBox moneyBox, int note100, int note500, int note1000, int note5000);

    List<Integer> getMoney(MoneyBox moneyBox, int sum);

}
