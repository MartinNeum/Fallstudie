package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.domain.Keyword;
import de.ostfalia.bips.ws22.camunda.database.domain.Supervisor;
import de.ostfalia.bips.ws22.camunda.database.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SupervisorService {
    private final SupervisorRepository repository;

    public SupervisorService(SupervisorRepository repository) {
        this.repository = repository;
    }

    public SupervisorRepository getRepository() {
        return repository;
    }

    public List<Supervisor> findAllByKeyword(Keyword keyword) {
        if (keyword == null) {
            return Collections.emptyList();
        }

        return repository.findAllByKeyword(keyword);
    }
}
