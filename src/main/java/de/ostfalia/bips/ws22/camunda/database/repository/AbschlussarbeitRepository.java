package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Abschlussarbeit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbschlussarbeitRepository extends JpaRepository<Abschlussarbeit, Integer> {
}
