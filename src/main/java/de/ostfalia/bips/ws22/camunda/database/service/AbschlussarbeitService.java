package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.domain.Abschlussarbeit;
import de.ostfalia.bips.ws22.camunda.database.repository.AbschlussarbeitRepository;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class AbschlussarbeitService {
    private final AbschlussarbeitRepository repository;

    public AbschlussarbeitService(AbschlussarbeitRepository repository) {
        this.repository = repository;
    }

    public AbschlussarbeitRepository getRepository() {
        return repository;
    }

    public List<Abschlussarbeit> getAbschlussarbeitByAntragID(Integer antragID) {
        if(antragID== null) {
            throw new InvalidParameterException("Get Abschlussarbeit failed. At least one parameter is NULL.");
        }

        return repository.getAbschlussarbeitByAntragID(antragID);
    }

    public void createAbschlussarbeit(Integer idAbschlussarbeit, String endeDatum, String beginnDatum, Integer antragID) {
        if (idAbschlussarbeit == null || endeDatum == null || beginnDatum == null) {
            throw new InvalidParameterException("Create Abschlussarbeit failed. At least one parameter is NULL.");
        }

        repository.createAbschlussarbeit(idAbschlussarbeit, endeDatum, beginnDatum, antragID);
    }
}
