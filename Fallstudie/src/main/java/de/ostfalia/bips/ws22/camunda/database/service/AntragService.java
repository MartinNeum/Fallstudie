package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.repository.AntragRepository;

public class AntragService {
    private final AntragRepository repository;

    public AntragService(AntragRepository repository) {
        this.repository = repository;
    }

    public AntragRepository getRepository() {
        return repository;
    }
}
