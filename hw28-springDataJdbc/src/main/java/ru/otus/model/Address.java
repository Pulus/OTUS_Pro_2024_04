package ru.otus.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "address")
@ToString
public class Address {
    @Id
    private final Long id;

    private final String street;

    @MappedCollection(idColumn = "address_id")
    private final Client client;

    public Address(String street) {
        this(null, street, null);
    }

    @PersistenceCreator
    public Address(Long id, String street, Client client) {
        this.id = id;
        this.street = street;
        this.client = client;
    }
}
