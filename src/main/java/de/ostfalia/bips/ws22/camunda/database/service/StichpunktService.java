package de.ostfalia.bips.ws22.camunda.database.service;


import de.ostfalia.bips.ws22.camunda.database.repository.StichpunktRepository;
import org.springframework.stereotype.Service;

@Service
public class StichpunktService {
    private final StichpunktRepository repository;

    public StichpunktService(StichpunktRepository repository) {
        this.repository = repository;
    }

    public StichpunktRepository getRepository() {
        return repository;
    }
}
