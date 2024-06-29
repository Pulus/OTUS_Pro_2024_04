package machine.service;

import machine.data.CashMachine;

import java.util.List;

public interface CashMachineService {
    List<Integer> getMoney(CashMachine machine, Integer amount);

    void putMoney(CashMachine machine, List<Integer> notes);

    int checkSum(CashMachine machine);


}
