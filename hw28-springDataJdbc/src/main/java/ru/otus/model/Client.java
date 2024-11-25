package ru.otus.model;

import java.util.Set;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "client")
@Getter
@ToString
public class Client {
    @Id
    @Column("id")
    private final Long id;

    @Column("name")
    private final String name;

    private final Long addressId;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    public Client(String name, Long addressId, Set<Phone> phones) {
        this(null, name, addressId, phones);
    }

    @PersistenceCreator
    public Client(Long id, String name, Long addressId, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.phones = phones;
    }
}
