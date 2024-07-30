package machine.demo;

import java.util.Arrays;
import java.util.List;

import machine.data.CashMachine;
import machine.service.CashMachineService;
import machine.service.MoneyBoxService;
import machine.service.impl.CashMachineServiceImpl;
import machine.service.impl.MoneyBoxServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoATM {
    static MoneyBoxService moneyBoxService;
    static CashMachineService cashMachineService;
    private static final Logger logger = LoggerFactory.getLogger(DemoATM.class);

    static {
        moneyBoxService = new MoneyBoxServiceImpl();
        cashMachineService = new CashMachineServiceImpl(moneyBoxService);
    }

    public void startShowingFunctional() {
        try {
            CashMachine cashMachine = new CashMachine();
            cashMachineService.putMoney(cashMachine, Arrays.asList(1, 3, 4, 10, 10));

            int initialSum = cashMachineService.checkSum(cashMachine);
            logger.info("Initial sum {}", initialSum);

            int amount = 7800;
            List<Integer> takenAmount = cashMachineService.getMoney(cashMachine, amount);
            logger.info("Withdraw {}", amount);
            logger.info("Taken notes {}", takenAmount);

            initialSum = cashMachineService.checkSum(cashMachine);
            logger.info("New sum {}", initialSum);

            cashMachineService.putMoney(cashMachine, Arrays.asList(1, 0, 0, 1));
            initialSum = cashMachineService.checkSum(cashMachine);
            logger.info("Contribution {}", 5100);
            logger.info("New sum {}", initialSum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
