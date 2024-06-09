package homework;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Entry<Customer, String> getSmallest() {
        return new Entry<>() {
            @Override
            public Customer getKey() {
                Customer nwSmallest = map.firstEntry().getKey();
                return new Customer(nwSmallest.getId(), nwSmallest.getName(), nwSmallest.getScores());
            }

            @Override
            public String getValue() {
                return map.firstEntry().getValue();
            }

            @Override
            public String setValue(String value) {
                return map.firstEntry().setValue(value);
            }
        };
    }

    public Entry<Customer, String> getNext(Customer customer) {
        if (map.higherEntry(customer) != null) {
            return new Entry<>() {
                @Override
                public Customer getKey() {
                    Customer nwBiggest = map.higherEntry(customer).getKey();
                    return new Customer(nwBiggest.getId(), nwBiggest.getName(), nwBiggest.getScores());
                }

                @Override
                public String getValue() {
                    return map.higherEntry(customer).getValue();
                }

                @Override
                public String setValue(String value) {
                    return map.higherEntry(customer).setValue(value);
                }
            };
        } else {
            return null;
        }
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
