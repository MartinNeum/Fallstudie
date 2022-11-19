package de.ostfalia.bips.ws22.camunda.database.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "supervisor_has_keyword")
public class SupervisorHasKeyword {
    @EmbeddedId
    private Id id;

    public SupervisorHasKeyword() {
    }

    public SupervisorHasKeyword(Supervisor supervisor, Keyword keyword) {
        this.id = new Id(supervisor, keyword);
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupervisorHasKeyword that = (SupervisorHasKeyword) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "SupervisorHasKeyword{id=" + (id == null ? "<null>" : id) + '}';
    }

    @Embeddable
    public static class Id implements Serializable {
        @ManyToOne(targetEntity = Supervisor.class, optional = false)
        @JoinColumn(name = "id_supervisor", referencedColumnName = "id_supervisor", nullable = false)
        private Supervisor supervisor;

        @ManyToOne(targetEntity = Keyword.class, optional = false)
        @JoinColumn(name = "id_keyword", referencedColumnName = "id_keyword", nullable = false)
        private Keyword keyword;

        public Id() {
        }

        public Id(Supervisor supervisor, Keyword keyword) {
            this.supervisor = supervisor;
            this.keyword = keyword;
        }

        public Supervisor getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(Supervisor supervisor) {
            this.supervisor = supervisor;
        }

        public Keyword getKeyword() {
            return keyword;
        }

        public void setKeyword(Keyword keyword) {
            this.keyword = keyword;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return Objects.equals(getSupervisor(), id.getSupervisor()) && Objects.equals(getKeyword(), id.getKeyword());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getSupervisor(), getKeyword());
        }

        @Override
        public String toString() {
            return String.format("%s has %s", supervisor,  keyword);
        }
    }
}
