package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Abschlussarbeit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AbschlussarbeitRepository extends JpaRepository<Abschlussarbeit, Integer> {

    @Modifying
    @Query(value = "SELECT  * FROM Abschlussarbeit WHERE id_antrag = :idAntrag", nativeQuery = true)
    @Transactional
    List<Abschlussarbeit> getAbschlussarbeitByAntragID (
            @Param("idAntrag") Integer idAntrag
    );

    @Modifying
    @Query(value = "INSERT INTO Abschlussarbeit (id_abschlussarbeit, ende_datum, beginn_datum, id_antrag) VALUES (:idAbschlussarbeit, :endeDatum, :beginnDatum, :idAntrag)", nativeQuery = true)
    @Transactional
    int createAbschlussarbeit (
            @Param("idAbschlussarbeit") Integer idAbschlussarbeit,
            @Param("endeDatum") String endeDatum,
            @Param("beginnDatum") String beginnDatum,
            @Param("idAntrag") Integer idAntrag
    );
}
