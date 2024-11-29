package ru.otus.service;

import java.util.Optional;
import ru.otus.model.Address;

public interface DbServiceAddress {
    Address saveAddress(Address address);

    Optional<Address> getAddress(Long id);
}
