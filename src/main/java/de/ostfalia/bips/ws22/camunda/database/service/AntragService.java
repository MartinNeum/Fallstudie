package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.repository.AntragRepository;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class AntragService {
    private final AntragRepository repository;

    public AntragService(AntragRepository repository) {
        this.repository = repository;
    }

    public AntragRepository getRepository() {
        return repository;
    }

    public void createAntrag(Integer studentID, Integer professorID, String titel, Integer typ) {
        if (studentID == null || professorID == null || titel == null || typ == null) {
            throw new InvalidParameterException("Create Antrag failed. At least one parameter is NULL.");
        }

        repository.createAntrag(studentID, professorID, titel, typ);
    }
}
