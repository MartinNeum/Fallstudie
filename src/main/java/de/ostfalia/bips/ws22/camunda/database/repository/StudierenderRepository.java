package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Studierender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudierenderRepository extends JpaRepository<Studierender, Integer> {
    @Modifying
    @Query(value = "INSERT INTO studierender (id_studierender, mailadresse, vorname, nachname) VALUES (:id, :mail, :prename, :name)", nativeQuery = true)
    @Transactional
    int createStudent (
            @Param("id") Integer id,
            @Param("mail") String mail,
            @Param("prename") String prename,
            @Param("name") String name
    );
}
