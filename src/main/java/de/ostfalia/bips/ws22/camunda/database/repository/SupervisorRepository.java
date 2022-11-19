package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Keyword;
import de.ostfalia.bips.ws22.camunda.database.domain.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {
    @Query("SELECT DISTINCT s FROM Supervisor s " +
            "INNER JOIN SupervisorHasKeyword shk ON shk.id.supervisor = s " +
            "WHERE s.keyword = :keyword " +
            "OR shk.id.keyword = :keyword " +
            "ORDER BY s.name")
    List<Supervisor> findAllByKeyword(@Param("keyword")Keyword keyword);
}
