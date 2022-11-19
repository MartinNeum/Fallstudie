package de.ostfalia.bips.ws22.camunda.database.domain;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "professor_hat_stichpunkt")
public class ProfessorHatStichpunkt {
    @EmbeddedId
    private Id id;
    @Column(name = "gewicht", nullable = false)
    private int gewicht;

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public ProfessorHatStichpunkt(){
    }

    public ProfessorHatStichpunkt(Professor professor, Stichpunkt stichpunkt) {
        this.id = new Id(professor, stichpunkt);
    }

    public ProfessorHatStichpunkt.Id getId() {
        return id;
    }

    public void setId(ProfessorHatStichpunkt.Id id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorHatStichpunkt that = (ProfessorHatStichpunkt) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProfessorHatStichpunkt{" +
                "id=" + id +
                '}';
    }

    @Embeddable
    public static class Id implements Serializable {
        @ManyToOne(targetEntity = Professor.class, optional = false)
        @JoinColumn(name = "id_professor", referencedColumnName = "id_professor", nullable = false)
        private Professor professor;

        @ManyToOne(targetEntity = Stichpunkt.class, optional = false)
        @JoinColumn(name = "id_stichpunkt", referencedColumnName = "id_stichpunkt", nullable = false)
        private Stichpunkt stichpunkt;

        public Id() {
        }

        public Id(Professor professor, Stichpunkt stichpunkt) {
            this.professor = professor;
            this.stichpunkt = stichpunkt;
        }

        public Professor getProfessor() {
            return professor;
        }

        public void setProfessor(Professor professor) {
            this.professor = professor;
        }

        public Stichpunkt getStichpunkt() {
            return stichpunkt;
        }

        public void setStichpunkt(Stichpunkt stichpunkt) {
            this.stichpunkt = stichpunkt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return Objects.equals(professor, id.professor) && Objects.equals(stichpunkt, id.stichpunkt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(professor, stichpunkt);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "professor=" + professor +
                    ", stichpunkt=" + stichpunkt +
                    '}';
        }
    }
}
