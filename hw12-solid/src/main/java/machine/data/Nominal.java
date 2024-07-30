package machine.data;

import lombok.Getter;

@Getter
public enum Nominal {
    NOMINAL5000(5000),
    NOMINAL1000(1000),
    NOMINAL500(500),
    NOMINAL100(100);

    private final int val;

    Nominal(int val) {
        this.val = val;
    }

}
