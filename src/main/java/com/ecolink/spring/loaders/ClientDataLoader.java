package com.ecolink.spring.loaders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ecolink.spring.entity.Client;
import com.ecolink.spring.service.ClientService;
import com.ecolink.spring.service.OdsService;

@Component
@Order(10)
public class ClientDataLoader implements CommandLineRunner {
    @Autowired
    private OdsService odsService;
    @Autowired
    private ClientService service;

    @Override
    public void run(String... args) throws Exception {
        List<Client> clients = Arrays.asList(
                new Client("Alice Johnson",
                        Arrays.asList(odsService.findByName("Affordable Housing"),
                                odsService.findByName("Clean Energy")),
                        "alice@example.com", "Tech startup innovator"),
                new Client("Bob Smith",
                        Arrays.asList(odsService.findByName("Sustainable Energy"),
                                odsService.findByName("Climate Action")),
                        "bob@example.com", "Sustainable energy entrepreneur"),
                new Client("Charlie Brown",
                        Arrays.asList(odsService.findByName("AI for Good"),
                                odsService.findByName("Industry Innovation")),
                        "charlie@example.com", "AI and robotics specialist"),
                new Client("Diana Prince",
                        Arrays.asList(odsService.findByName("Women Empowerment"),
                                odsService.findByName("Quality Education")),
                        "diana@example.com", "Advocate for women in STEM"),
                new Client("Ethan Hunt",
                        Arrays.asList(odsService.findByName("Cybersecurity"),
                                odsService.findByName("Innovation and Infrastructure")),
                        "ethan@example.com", "Expert in digital security"),
                new Client("Fiona Gallagher",
                        Arrays.asList(odsService.findByName("Sustainable Cities"),
                                odsService.findByName("Zero Hunger")),
                        "fiona@example.com", "Urban development specialist"),
                new Client("George Miller",
                        Arrays.asList(odsService.findByName("Good Health"), odsService.findByName("Clean Water")),
                        "george@example.com", "Healthcare entrepreneur"),
                new Client("Hannah Baker",
                        Arrays.asList(odsService.findByName("Responsible Consumption"),
                                odsService.findByName("Life on Land")),
                        "hannah@example.com", "Environmental activist"));

        clients.forEach(client-> {
            if (!service.existByEmail(client.getEmail())) {
                service.save(client);
            }
        });
    }

}
