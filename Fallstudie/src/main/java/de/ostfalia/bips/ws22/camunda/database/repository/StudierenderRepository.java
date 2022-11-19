package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Professor;
import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
import de.ostfalia.bips.ws22.camunda.database.domain.Studierender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.util.List;

public interface StudierenderRepository extends JpaRepository<Studierender, Integer> {
    @Modifying
    @Query(value = "INSERT INTO studierender (id_studierender, mailadresse, vorname, nachname)" +
            "VALUES  (:id_student,:email, :vorname, :nachname)", nativeQuery = true)

    @Transactional
    int studentEinspeichern(@Param("id_student") int id_studierender,
                           @Param("email") String mailadresse,
                           @Param("vorname") String vorname,
                           @Param("nachname") String nachname);


}
