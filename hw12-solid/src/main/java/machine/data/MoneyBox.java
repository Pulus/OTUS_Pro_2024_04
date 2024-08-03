package machine.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class MoneyBox {
    private final Nominal nominal;

    @Setter
    private int note = 0;

    public MoneyBox(Nominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyBox moneyBox = (MoneyBox) o;
        return nominal == moneyBox.nominal;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nominal);
    }
}
