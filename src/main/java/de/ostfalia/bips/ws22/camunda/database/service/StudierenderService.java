package de.ostfalia.bips.ws22.camunda.database.service;

import de.ostfalia.bips.ws22.camunda.database.repository.StudierenderRepository;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class StudierenderService {
    private final StudierenderRepository repository;

    public StudierenderService(StudierenderRepository repository) {
        this.repository = repository;
    }

    public StudierenderRepository getRepository() {
        return repository;
    }

    public void createStudent(Integer studentID, String studentMail, String studentPrename, String studentName) {
        if (studentID == null || studentMail == null || studentPrename == null || studentName == null) {
            throw new InvalidParameterException("Create Student failed. At least one parameter is NULL.");
        }

        repository.createStudent(studentID, studentMail, studentPrename, studentName);
    }
}
