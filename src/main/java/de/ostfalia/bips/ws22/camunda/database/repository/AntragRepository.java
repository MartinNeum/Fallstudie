package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Antrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AntragRepository extends JpaRepository<Antrag, Integer> {
    @Modifying
    @Query(value = "INSERT INTO Antrag (id_studierender, id_professor, titel, typ, genehmigt_prof) VALUES (:studentId, :professorId, :titel, :typ, 0)", nativeQuery = true)
    @Transactional
    int createAntrag (
            @Param("studentId") Integer idS,
            @Param("professorId") Integer idP,
            @Param("titel") String titel,
            @Param("typ") Integer typ
    );
}
