package de.ostfalia.bips.ws22.camunda.database.repository;


import de.ostfalia.bips.ws22.camunda.database.domain.Professor;
import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Query ("SELECT DISTINCT p FROM Professor p " +
            "INNER JOIN ProfessorHatStichpunkt phs " +
            "ON phs.id.professor = p " +
            "WHERE phs.id.stichpunkt in(:stichpunkt) " +
            "ORDER BY phs.id.stichpunkt.id ASC ")
    List<Professor> findAllByStichpunkt (@Param("stichpunkt") Stichpunkt stichpunkt);
}
