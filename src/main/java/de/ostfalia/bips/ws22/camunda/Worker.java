package de.ostfalia.bips.ws22.camunda;

import de.ostfalia.bips.ws22.camunda.database.domain.Professor;
import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
import de.ostfalia.bips.ws22.camunda.database.service.AntragService;
import de.ostfalia.bips.ws22.camunda.database.service.ProfessorService;
import de.ostfalia.bips.ws22.camunda.database.service.StichpunktService;
import de.ostfalia.bips.ws22.camunda.database.service.StudierenderService;
import de.ostfalia.bips.ws22.camunda.model.Option;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

    public Worker(StichpunktService stichpunktService,
                  ProfessorService professorService,
                  StudierenderService studierenderService,
                  AntragService antragService
    ) {
        this.stichpunktService = stichpunktService;
        this.professorService = professorService;
        this.studierenderService = studierenderService;
        this.antragService = antragService;
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
        Integer selectedKeywordID = Integer.parseInt(profAndKeywordString[1]);
        Integer typ = Integer.parseInt(typString);

        try {
            antragService.createAntrag(studentID, selectedProfID, title, typ);
            LOGGER.info("--> Antrag wurde angelegt");
        } catch (Exception e) {
            LOGGER.info("--> Anlage des Antrags schlug fehl: " + e.getMessage());
        }
    }
}
