package ru.otus.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.model.Address;

public interface AddressRepository extends ListCrudRepository<Address, Long> {}
