package machine.service;

import java.util.List;
import machine.data.CashMachine;

public interface CashMachineService {
    List<Integer> getMoney(CashMachine machine, Integer amount);

    void putMoney(CashMachine machine, List<Integer> notes);

    int checkSum(CashMachine machine);
}
