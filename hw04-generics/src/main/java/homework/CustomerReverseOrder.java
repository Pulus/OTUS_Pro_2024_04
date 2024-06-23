package homework;

import java.util.Comparator;
import java.util.TreeMap;

public class CustomerReverseOrder {

    private final TreeMap<Integer, Customer> map = new TreeMap<>(Comparator.comparingInt(value -> value));

    public void add(Customer customer) {
        map.put(map.size() + 1, customer);
    }

    public Customer take() {
        return map.pollLastEntry().getValue();
    }
}
