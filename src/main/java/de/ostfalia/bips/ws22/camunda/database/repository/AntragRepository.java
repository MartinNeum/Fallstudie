package de.ostfalia.bips.ws22.camunda.database.repository;

import de.ostfalia.bips.ws22.camunda.database.domain.Antrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

    @Modifying
    @Query(value = "UPDATE Antrag SET begruendung_ablehnung = :grund WHERE id_studierender = :studentID", nativeQuery = true)
    @Transactional
    void updateAntragAblehnung (
            @Param("grund") String grund,
            @Param("studentID") Integer studentID
    );

    @Modifying
    @Query(value = "SELECT * FROM Antrag WHERE id_studierender = :studentId AND id_professor = :professorId", nativeQuery = true)
    @Transactional
    List<Antrag> getAntragByStudentAndProfessorId (
            @Param("studentId") Integer idS,
            @Param("professorId") Integer idP
    );

    @Modifying
    @Query(value = "UPDATE Antrag  SET genehmigt_prof = :genehmigt WHERE id_studierender = :studentID", nativeQuery = true)
    @Transactional
    int updateAntragGenehmigungProf (
        @Param("genehmigt") Integer genehmigt,
        @Param("studentID") Integer id
    );

    @Modifying
    @Query(value = "UPDATE Antrag  SET genehmigt_pav = :genehmigt WHERE id_studierender = :studentID", nativeQuery = true)
    @Transactional
    int updateAntragGenehmigungPAV (
            @Param("genehmigt") Integer genehmigt,
            @Param("studentID") Integer id
    );

    @Modifying
    @Query(value = "UPDATE Antrag  SET genehmigt_ssb = :genehmigt WHERE id_studierender = :studentID", nativeQuery = true)
    @Transactional
    int updateAntragGenehmigungSSB (
            @Param("genehmigt") Integer genehmigt,
            @Param("studentID") Integer id
    );

}
