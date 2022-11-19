package de.ostfalia.bips.ws22.camunda.database.repository;


import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StichpunktRepository extends JpaRepository<Stichpunkt, Integer> {
}
