package ru.otus.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.ClientRequest;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.service.DBServiceClient;
import ru.otus.service.DbServiceAddress;

@RestController
@AllArgsConstructor
public class ClientRestController {
    private final DBServiceClient clientRepository;
    private final DbServiceAddress addressRepository;

    @PostMapping("/api/clients")
    public Client saveClint(@RequestBody ClientRequest clientRequest) {
        Set<Phone> phoneList =
                Arrays.stream(clientRequest.phones().split(",")).map(Phone::new).collect(Collectors.toSet());

        var savingAddress = addressRepository.saveAddress(new Address(clientRequest.address()));

        Client client = new Client(clientRequest.name(), savingAddress.getId(), phoneList);

        return clientRepository.saveClient(client);
    }
}