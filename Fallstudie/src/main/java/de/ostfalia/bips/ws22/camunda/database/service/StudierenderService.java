package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.repository.StudierenderRepository;
import org.springframework.stereotype.Service;

@Service
public class StudierenderService {
    private final StudierenderRepository repository;

    public StudierenderService(StudierenderRepository repository) {
        this.repository = repository;
    }

    public StudierenderRepository getRepository() {
        return repository;
    }

    public void studentEinspeichern(int id_studierender, String email, String vorname, String nachname){
        repository.studentEinspeichern(id_studierender, email, vorname, nachname);
    }
}
