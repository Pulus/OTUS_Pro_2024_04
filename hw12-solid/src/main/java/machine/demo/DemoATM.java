package machine.demo;

import machine.data.CashMachine;
import machine.data.MoneyBox;
import machine.service.CashMachineService;
import machine.service.MoneyBoxService;
import machine.service.impl.CashMachineServiceImpl;
import machine.service.impl.MoneyBoxServiceImpl;

import java.util.Arrays;
import java.util.List;

public class DemoATM {
    static MoneyBoxService moneyBoxService;
    static CashMachineService cashMachineService;

    static {
        moneyBoxService = new MoneyBoxServiceImpl();
        cashMachineService = new CashMachineServiceImpl(moneyBoxService);
    }
    public void startShowingFunctional (){
        MoneyBox moneyBox = new MoneyBox();
        CashMachine cashMachine = new CashMachine(moneyBox);

        int initialSum = cashMachineService.checkSum(cashMachine);
        System.out.println("Initial sum " + initialSum);
        System.out.println();

        List<Integer> takenAmount = cashMachineService.getMoney(cashMachine, 7700);
        System.out.println("Withdraw " + 7700);
        System.out.println("Taken notes " + takenAmount);

        initialSum = cashMachineService.checkSum(cashMachine);
        System.out.println("New sum " + initialSum);

        System.out.println();
        cashMachineService.putMoney(cashMachine, Arrays.asList(0, 0, 0, 1));
        initialSum = cashMachineService.checkSum(cashMachine);
        System.out.println("Contribution " + 100);
        System.out.println("New sum " + initialSum);
    }
}
