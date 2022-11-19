package de.ostfalia.bips.ws22.camunda.database.domain;

import javax.persistence.*;

@Entity
@Table(name = "antrag")

public class Antrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_antrag", nullable = false)
    private Integer id;

    @ManyToOne(targetEntity = Studierender.class, optional = false)
    @JoinColumn(name = "id_studierender", referencedColumnName = "id_studierender", nullable = false)
    private Studierender studierender;

    @ManyToOne(targetEntity = Professor.class, optional = false)
    @JoinColumn(name = "id_professor", referencedColumnName = "id_professor", nullable = false)
    private Professor professor;

    @Column(name = "titel", nullable = false)
    private String titel;

    @Column(name = "typ", nullable = false)
    private Integer typ;

    @Column(name = "genehmigt_prof", nullable = false)
    private Integer genehmigt_prof;

    @Column(name = "genehmigt_pav", nullable = false)
    private Integer genehmigt_pav;

    @Column(name = "genehmigt_ssb", nullable = false)
    private Integer genehmigt_ssb;

    @Column(name = "begruendung_ablehnung", nullable = false)
    private String begruendung_ablehnung;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Studierender getStudierender() {
        return studierender;
    }

    public void setStudierender(Studierender studierender) {
        this.studierender = studierender;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public Integer getGenehmigt_prof() {
        return genehmigt_prof;
    }

    public void setGenehmigt_prof(Integer genehmigt_prof) {
        this.genehmigt_prof = genehmigt_prof;
    }

    public Integer getGenehmigt_pav() {
        return genehmigt_pav;
    }

    public void setGenehmigt_pav(Integer genehmigt_pav) {
        this.genehmigt_pav = genehmigt_pav;
    }

    public Integer getGenehmigt_ssb() {
        return genehmigt_ssb;
    }

    public void setGenehmigt_ssb(Integer genehmigt_ssb) {
        this.genehmigt_ssb = genehmigt_ssb;
    }

    public String getBegruendung_ablehnung() {
        return begruendung_ablehnung;
    }

    public void setBegruendung_ablehnung(String begruendung_ablehnung) {
        this.begruendung_ablehnung = begruendung_ablehnung;
    }

    @Override
    public String toString() {
        return "Antrag{" +
                "id=" + id +
                ", studierender=" + studierender +
                ", professor=" + professor +
                ", titel='" + titel + '\'' +
                ", typ=" + typ +
                ", genehmigt_prof=" + genehmigt_prof +
                ", genehmigt_pav=" + genehmigt_pav +
                ", genehmigt_ssb=" + genehmigt_ssb +
                ", begruendung_ablehnung='" + begruendung_ablehnung + '\'' +
                '}';
    }
}
