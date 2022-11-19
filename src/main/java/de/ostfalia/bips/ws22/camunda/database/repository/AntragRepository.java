package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Antrag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AntragRepository extends JpaRepository<Antrag, Integer> {
}
