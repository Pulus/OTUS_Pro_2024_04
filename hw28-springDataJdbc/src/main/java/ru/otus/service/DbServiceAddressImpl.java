package ru.otus.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Address;
import ru.otus.repository.AddressRepository;
import ru.otus.sessionmanager.TransactionManager;

@Service
@Slf4j
@AllArgsConstructor
public class DbServiceAddressImpl implements DbServiceAddress {
    private final TransactionManager transactionManager;
    private final AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        return transactionManager.doInTransaction(() -> {
            var savedAddress = addressRepository.save(address);
            log.info("saved address: {}", savedAddress);
            return savedAddress;
        });
    }

    @Override
    public Optional<Address> getAddress(Long id) {
        var addressOptional = addressRepository.findById(id);
        log.info("address: {}", addressOptional);
        return addressOptional;
    }
}
