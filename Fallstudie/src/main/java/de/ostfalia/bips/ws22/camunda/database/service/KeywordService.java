package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.repository.KeywordRepository;
import org.springframework.stereotype.Service;

@Service
public class KeywordService {
    private final KeywordRepository repository;

    public KeywordService(KeywordRepository repository) {
        this.repository = repository;
    }

    public KeywordRepository getRepository() {
        return repository;
    }
}
