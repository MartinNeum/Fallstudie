package de.ostfalia.bips.ws22.camunda;

import de.ostfalia.bips.ws22.camunda.database.domain.Abschlussarbeit;
import de.ostfalia.bips.ws22.camunda.database.domain.Antrag;
import de.ostfalia.bips.ws22.camunda.database.domain.Professor;
import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
import de.ostfalia.bips.ws22.camunda.database.service.*;
import de.ostfalia.bips.ws22.camunda.model.Option;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableZeebeClient
public class Worker {
    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    public static void main(String[] args) {
        SpringApplication.run(Worker.class, args);
    }

    private final StichpunktService stichpunktService;
    private final ProfessorService professorService;
    private final StudierenderService studierenderService;
    private final AntragService antragService;
    private final AbschlussarbeitService abschlussarbeitService;

    public Worker(StichpunktService stichpunktService,
                  ProfessorService professorService,
                  StudierenderService studierenderService,
                  AntragService antragService,
                  AbschlussarbeitService abschlussarbeitService) {
        this.stichpunktService = stichpunktService;
        this.professorService = professorService;
        this.studierenderService = studierenderService;
        this.antragService = antragService;
        this.abschlussarbeitService = abschlussarbeitService;
    }

    @ZeebeWorker(type = "lade-stichpunkte", autoComplete = true)
    public Map<String, Object> stichpunkt(final ActivatedJob job) {
        // Do the business logic
        LOGGER.info("Lade Stichpunkte");

        final List<Option<Integer>> stichpunkte = stichpunktService.getRepository().findAll().stream()
                .map(e -> new Option<>(e.getTitel(), e.getId()))
                .collect(Collectors.toList());

        // Probably add some process variables
        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("keywordsList", stichpunkte);
        return variables;
    }

    @ZeebeWorker(type = "lade-professor", autoComplete = true)
    public Map<String, Object> professor(final ActivatedJob job) {

        LOGGER.info("Lade Betreuer");

        /** Finde alle selektierten Stichpunkte */
        Object providedKeywordArray = job.getVariablesAsMap().get("keywordsSelected");

        String[] keywordString = providedKeywordArray.toString().substring(1, providedKeywordArray.toString().length() -1).split(", ");
        ArrayList<Integer> keywordIDs = new ArrayList<>();
        for(int i = 0; i < keywordString.length; i++) {
            keywordIDs.add(Integer.parseInt(keywordString[i]));
        }

        ArrayList<Optional<Stichpunkt>> selectedKeywords = new ArrayList<>();
        keywordIDs.forEach((id) -> {
            selectedKeywords.add(stichpunktService.getRepository().findById(id));
        });

        /** Finde Professoren zu den selektierten Stichpunkten */
        List<Option> professorsWithKeyword = new ArrayList<>();
        selectedKeywords.forEach((keyword) -> {
           List<Professor> profs = professorService.findAllByStichpunkt(keyword.get());
           profs.forEach(prof -> {
               professorsWithKeyword.add(new Option<>("[" + keyword.get().getTitel() + "] " + prof.getLabel(), prof.getId() + "," + keyword.get().getId()));
           });
        });

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("professorsWithKeywordsList", professorsWithKeyword);
        return variables;
    }

    @ZeebeWorker(type = "eintrag-anlegen", autoComplete = true)
    public void eintragAnlegen(final ActivatedJob job) {

        LOGGER.info("Neunen Antrag anlegen");

        /** Neuen Studenten anlegen */
        Integer studentID = Integer.parseInt(job.getVariablesAsMap().get("studentID").toString());
        String studentMail = job.getVariablesAsMap().get("studentMail").toString();
        String studentName = job.getVariablesAsMap().get("studentName").toString();
        String studentPrename = job.getVariablesAsMap().get("studentPrename").toString();

        try {
            studierenderService.createStudent(studentID, studentMail, studentPrename, studentName);
            LOGGER.info("--> Student wurde angelegt");
        } catch (Exception e) {
            LOGGER.info("--> Anlegen des Students schulg fehl: " + e.getMessage());
        }

        /** Antrag anlegen */
        String selectedProfessorIDAndKeywordID = job.getVariablesAsMap().get("selectedProfessorIDAndKeywordID").toString();
        String[] profAndKeywordString = selectedProfessorIDAndKeywordID.split(",");
        String title = job.getVariablesAsMap().get("applicationTitle").toString();
        String typString = job.getVariablesAsMap().get("applicationType").toString();

        Integer selectedProfID = Integer.parseInt(profAndKeywordString[0]);
        Integer typ = Integer.parseInt(typString);

        try {
            antragService.createAntrag(studentID, selectedProfID, title, typ);
            LOGGER.info("--> Antrag wurde angelegt");
        } catch (Exception e) {
            LOGGER.info("--> Anlage des Antrags schlug fehl: " + e.getMessage());
        }
    }

    @ZeebeWorker(type = "antrag-genehmigen-prof", autoComplete = true)
    public void antragGenehmigenProf(final ActivatedJob job) {

        LOGGER.info("Setze Genehmigungsstatus vom Professor");

        /** Antrag genehmigen oder ablehnen */
        Integer studentID = Integer.parseInt(job.getVariablesAsMap().get("studentID").toString());
        String approvedString = job.getVariablesAsMap().get("applicationProfessorApproved").toString();
        Boolean approved = Boolean.parseBoolean(approvedString);

        try {
            antragService.setAntragGenehmigungStatusProf(approved, studentID);
            LOGGER.info("--> Genehmigungsstatus wurde gesetzt: " + approved);
        } catch (Exception e) {
            LOGGER.info("--> Setzen des Genehmigungsstatus schlug fehl: " + e.getMessage());
        }

        if(!approved) {
            String reasonDeny = job.getVariablesAsMap().get("reasonDeny").toString();
            antragService.updateAntragAblehnung(reasonDeny, studentID);
        }

    }

    @ZeebeWorker(type = "antrag-genehmigen-pav", autoComplete = true)
    public void antragGenehmigenPAV(final ActivatedJob job) {

        LOGGER.info("Setze Genehmigungsstatus vom Prüfungsausschuss");

        /** Antrag genehmigen oder ablehnen */
        Integer studentID = Integer.parseInt(job.getVariablesAsMap().get("studentID").toString());
        String approvedString = job.getVariablesAsMap().get("applicationPavApproved").toString();
        Boolean approved = Boolean.parseBoolean(approvedString);

        try {
            antragService.setAntragGenehmigungStatusPAV(approved, studentID);
            LOGGER.info("--> Genehmigungsstatus wurde gesetzt: " + approved);
        } catch (Exception e) {
            LOGGER.info("--> Setzen des Genehmigungsstatus schlug fehl: " + e.getMessage());
        }

        if(!approved) {
            String reasonDeny = job.getVariablesAsMap().get("reasonDeny").toString();
            antragService.updateAntragAblehnung(reasonDeny, studentID);
        }

    }

    @ZeebeWorker(type = "antrag-genehmigen-ssb", autoComplete = true)
    public void antragGenehmigenSSB(final ActivatedJob job) {

        LOGGER.info("Setze Genehmigungsstatus vom Studenten-Servicebüro");

        /** Antrag genehmigen oder ablehnen */
        Integer studentID = Integer.parseInt(job.getVariablesAsMap().get("studentID").toString());
        String approvedString = job.getVariablesAsMap().get("applicationSsbApproved").toString();
        Boolean approved = Boolean.parseBoolean(approvedString);

        try {
            antragService.setAntragGenehmigungStatusSSB(approved, studentID);
            LOGGER.info("--> Genehmigungsstatus wurde gesetzt: " + approved);
        } catch (Exception e) {
            LOGGER.info("--> Setzen des Genehmigungsstatus schlug fehl: " + e.getMessage());
        }

        if(!approved) {
            String reasonDeny = job.getVariablesAsMap().get("reasonDeny").toString();
            antragService.updateAntragAblehnung(reasonDeny, studentID);
        }

    }

    @ZeebeWorker(type = "abschlussarbeit-anlegen", autoComplete = true)
    public void abschlussarbeitAnlegen(final ActivatedJob job) {

        LOGGER.info("Neue Abschlussarbeit anlegen");

        /** Antrag finden */
        Integer studentID = Integer.parseInt(job.getVariablesAsMap().get("studentID").toString());
        String selectedProfessorIDAndKeywordID = job.getVariablesAsMap().get("selectedProfessorIDAndKeywordID").toString();
        String[] profAndKeywordString = selectedProfessorIDAndKeywordID.split(",");
        Integer professorID = Integer.parseInt(profAndKeywordString[0]);

        List<Antrag> applications = null;
        try {
            applications = antragService.getAntragByStudentAndProfessorId(studentID, professorID);
            LOGGER.info("--> Antrag gefunden");
        } catch (Exception e) {
            LOGGER.info("--> Holen des Antrags schlug fehl: " + e.getMessage());
        }

        Integer applicationID = applications.get(0).getId();

        /** Abschlussarbeit anlegen */
        Integer thesisID = Integer.parseInt(job.getVariablesAsMap().get("thesisID").toString());
        Integer applicationType = Integer.parseInt(job.getVariablesAsMap().get("applicationType").toString());
        LocalDate thesisStartDate = LocalDate.parse(job.getVariablesAsMap().get("thesisStartDate").toString());
        LocalDate thesisEndDate;

        if (applicationType == 1) {
            thesisEndDate = thesisStartDate.plusWeeks(11);
        } else if (applicationType == 2) {
            thesisEndDate = thesisStartDate.plusMonths(6);
        } else {
            thesisEndDate = null;
        }

        try {
            abschlussarbeitService.createAbschlussarbeit(thesisID, thesisEndDate.toString(), thesisStartDate.toString(), applicationID);
            LOGGER.info("--> Abschlussarbeit wurde angelegt");
        } catch (Exception e) {
            LOGGER.info("Anlage der Abschlussarbeit schlug fehl: " + e.getMessage());
        }

    }

    @ZeebeWorker(type = "ablehnung-senden", autoComplete = true)
    public void ablehnungSenden(final ActivatedJob job) {

        /** Antrag finden */
        Integer studentID = Integer.parseInt(job.getVariablesAsMap().get("studentID").toString());
        String selectedProfessorIDAndKeywordID = job.getVariablesAsMap().get("selectedProfessorIDAndKeywordID").toString();
        String[] profAndKeywordString = selectedProfessorIDAndKeywordID.split(",");
        Integer professorID = Integer.parseInt(profAndKeywordString[0]);

        List<Antrag> applications = null;
        try {
            applications = antragService.getAntragByStudentAndProfessorId(studentID, professorID);
            LOGGER.info("--> Antrag gefunden");
        } catch (Exception e) {
            LOGGER.info("--> Holen des Antrags schlug fehl: " + e.getMessage());
        }

        LOGGER.info("Versende Absage (Grund: " + applications.get(0).getBegruendung_ablehnung() + ")");

    }

    @ZeebeWorker(type = "annahme-senden", autoComplete = true)
    public void anahmeSenden(final ActivatedJob job) {

        /** Antrag finden */
        Integer studentID = Integer.parseInt(job.getVariablesAsMap().get("studentID").toString());
        String selectedProfessorIDAndKeywordID = job.getVariablesAsMap().get("selectedProfessorIDAndKeywordID").toString();
        String[] profAndKeywordString = selectedProfessorIDAndKeywordID.split(",");
        Integer professorID = Integer.parseInt(profAndKeywordString[0]);

        List<Antrag> applications = null;
        try {
            applications = antragService.getAntragByStudentAndProfessorId(studentID, professorID);
            LOGGER.info("--> Antrag gefunden");
        } catch (Exception e) {
            LOGGER.info("--> Holen des Antrags schlug fehl: " + e.getMessage());
        }

        Integer applicationID = applications.get(0).getId();

        /** Abschlussarbeit finden */
        List<Abschlussarbeit> thesis = null;
        try {
            thesis = abschlussarbeitService.getAbschlussarbeitByAntragID(applicationID);
            LOGGER.info("--> Abschlussarbeit gefunden");
        } catch (Exception e) {
            LOGGER.info("--> Holen der Abschlussarbeit schlug fehl: " + e.getMessage());
        }

        LOGGER.info("Versende Annahme (Beginn: " + thesis.get(0).getBeginn_datum() + ", Ende: " + thesis.get(0).getEnde_datum() + ")");

    }
}
