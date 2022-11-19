package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.repository.StudierenderRepository;

public class StudierenderService {
    private final StudierenderRepository repository;

    public StudierenderService(StudierenderRepository repository) {
        this.repository = repository;
    }

    public StudierenderRepository getRepository() {
        return repository;
    }
}
