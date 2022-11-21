package de.ostfalia.bips.ws22.camunda.database.service;


import de.ostfalia.bips.ws22.camunda.database.domain.Professor;
import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
import de.ostfalia.bips.ws22.camunda.database.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public ProfessorRepository getRepository() {
        return repository;
    }

    public List<Professor> findAllByStichpunkt(Stichpunkt stichpunkt) {
        if (stichpunkt == null) {
            return Collections.emptyList();
        }

        return repository.findAllByStichpunkt(stichpunkt);
    }
}
