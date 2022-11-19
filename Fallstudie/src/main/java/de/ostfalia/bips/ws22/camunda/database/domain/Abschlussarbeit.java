package de.ostfalia.bips.ws22.camunda.database.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "abschlussarbeit")

public class Abschlussarbeit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_keyword", nullable = false)
    private Integer id;

    @Column(name = "ende_datum", nullable = false)
    private Date ende_datum;

    @Column(name = "beginn_datum", nullable = false)
    private Date beginn_datum;

    @ManyToOne(targetEntity = Antrag.class, optional = false)
    @JoinColumn(name = "id_antrag", referencedColumnName = "id_antrag", nullable = false)
    private Antrag antrag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEnde_datum() {
        return ende_datum;
    }

    public void setEnde_datum(Date ende_datum) {
        this.ende_datum = ende_datum;
    }

    public Date getBeginn_datum() {
        return beginn_datum;
    }

    public void setBeginn_datum(Date beginn_datum) {
        this.beginn_datum = beginn_datum;
    }

    public Antrag getAntrag() {
        return antrag;
    }

    public void setAntrag(Antrag antrag) {
        this.antrag = antrag;
    }

    @Override
    public String toString() {
        return "Abschlussarbeit{" +
                "id=" + id +
                ", ende_datum=" + ende_datum +
                ", beginn_datum=" + beginn_datum +
                ", antrag=" + antrag +
                '}';
    }
}
