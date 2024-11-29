package ru.otus.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientView;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.service.DBServiceClient;
import ru.otus.service.DbServiceAddress;

@Controller
@AllArgsConstructor
public class ClientController {
    private final DBServiceClient clientRepository;
    private final DbServiceAddress addressRepository;

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        List<Client> clientList = clientRepository.findAll();

        List<ClientView> clientViews = new ArrayList<>();
        for (var client : clientList) {
            Optional<Address> address = addressRepository.getAddress(client.getAddressId());
            if (address.isPresent()) {
                clientViews.add(ClientView.mapClient(client, address.get()));
            } else {
                clientViews.add(ClientView.mapClient(client));
            }
        }

        model.addAttribute("clients", clientViews);
        return "clients";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(Client client) {
        clientRepository.saveClient(client);
        return new RedirectView("/", true);
    }
}
