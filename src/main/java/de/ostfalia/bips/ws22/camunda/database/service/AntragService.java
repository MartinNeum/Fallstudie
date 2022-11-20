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

    public int createAntrag(Integer studentID, Integer professorID, String titel, Integer typ) {
        if (studentID == null || professorID == null || titel == null || typ == null) {
            throw new InvalidParameterException("Create Antrag failed. At least one parameter is NULL.");
        }

        return repository.createAntrag(studentID, professorID, titel, typ);
    }

    public void setAntragGenehmigungStatusProf(Boolean genehmigt, Integer studentID) {
        if (genehmigt == null) {
            throw new InvalidParameterException("Update Antrag Genehmigung failed. At least one parameter is NULL.");
        }

        if (genehmigt) {
            repository.updateAntragGenehmigungProf(1, studentID);
        } else {
            repository.updateAntragGenehmigungProf(0, studentID);
        }

    }
}
