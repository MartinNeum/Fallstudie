package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.repository.AbschlussarbeitRepository;

public class AbschlussarbeitService {
    private final AbschlussarbeitRepository repository;

    public AbschlussarbeitService(AbschlussarbeitRepository repository) {
        this.repository = repository;
    }

    public AbschlussarbeitRepository getRepository() {
        return repository;
    }
}
