package machine.service.impl;

import machine.data.CashMachine;
import machine.service.CashMachineService;
import machine.service.MoneyBoxService;

import java.util.ArrayList;
import java.util.List;


public class CashMachineServiceImpl implements CashMachineService {

    private final MoneyBoxService moneyBoxService;

    public CashMachineServiceImpl(final MoneyBoxService moneyBoxService) {
        this.moneyBoxService = moneyBoxService;
    }

    @Override
    public List<Integer> getMoney(CashMachine machine, Integer amount) {
        try {
            return moneyBoxService.getMoney(machine.getMoneyBox(), amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putMoney(CashMachine machine, List<Integer> notes) {

        List<Integer> arrangedNotes = new ArrayList<>(notes);
        for (int i = 0; i < 4 - arrangedNotes.size(); i++) {
            arrangedNotes.add(0);
        }

        moneyBoxService.putMoney(machine.getMoneyBox(), arrangedNotes.get(3), arrangedNotes.get(2), arrangedNotes.get(1), arrangedNotes.get(0));
    }

    @Override
    public int checkSum(CashMachine machine) {
        return moneyBoxService.checkSum(machine.getMoneyBox());
    }
}