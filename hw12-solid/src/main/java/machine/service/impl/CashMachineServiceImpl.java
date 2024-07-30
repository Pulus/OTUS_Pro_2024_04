package machine.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import machine.data.CashMachine;
import machine.data.MoneyBox;
import machine.data.Nominal;
import machine.service.CashMachineService;
import machine.service.MoneyBoxService;

public class CashMachineServiceImpl implements CashMachineService {

    private final MoneyBoxService moneyBoxService;
    private final List<Nominal> arrangedNominal = Arrays.asList(Nominal.values());

    public CashMachineServiceImpl(final MoneyBoxService moneyBoxService) {
        this.moneyBoxService = moneyBoxService;
    }

    public List<Integer> getMoney(CashMachine machine, Integer sum) {
        List<Integer> result = new ArrayList<>();

        if (sum > checkSum(machine)) {
            throw new IllegalStateException("There's not that much money in the machine");
        }
        while (sum > 0) {
            Set<MoneyBox> mdList = machine.getMoneyBoxes();
            for (MoneyBox mb : mdList) {
                int notes = moneyBoxService.getMoney(mb, sum);
                result.add(notes);
                sum -= notes * mb.getNominal().getVal();
            }
        }
        return result;
    }

    public void putMoney(CashMachine machine, List<Integer> notes) {
        if (notes.size() > arrangedNominal.size()) {
            throw new IllegalStateException("Mistake! A banknote of unknown denomination has been found.");
        }
        for (int i = 0; i < notes.size(); i++) {
            MoneyBox md = machine.getMoneyBox(arrangedNominal.get(i));
            moneyBoxService.putMoney(md, notes.get(i));
        }
    }

    public int checkSum(CashMachine machine) {
        int sum = 0;
        for (MoneyBox mb : machine.getMoneyBoxes()) {
            sum += moneyBoxService.checkSum(mb);
        }
        return sum;
    }
}
