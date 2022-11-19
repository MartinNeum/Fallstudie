package de.ostfalia.bips.ws22.camunda;

import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
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
import java.util.stream.Collectors;

@SpringBootApplication
@EnableZeebeClient
public class Worker {
    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    public static void main(String[] args) {
        SpringApplication.run(Worker.class, args);
    }

    private final StudierenderService studierenderService;
    private final StichpunktService stichpunktService;
    private final ProfessorService professorService;

    public Worker(StudierenderService studierenderService, StichpunktService stichpunktService,
                  ProfessorService professorService) {
        this.studierenderService = studierenderService;
        this.stichpunktService = stichpunktService;
        this.professorService = professorService;

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
        variables.put("stichpunkte", stichpunkte);
        return variables;
    }

    @ZeebeWorker(type = "lade-professor", autoComplete = true)
    public Map<String, Object> professor(final ActivatedJob job) {
        // Do the business logic
        LOGGER.info("Lade Professor");

        List<Option<Integer>> sammlung = new ArrayList<>();

        // Get the
        final Object stichpunkt = job.getVariablesAsMap().get("stichpunkt");
        Optional<Stichpunkt> found;

        String[] strArr = stichpunkt.toString().substring(1, stichpunkt.toString().length() - 1).split(", ");
        LOGGER.info(Arrays.toString(strArr));
        LOGGER.info(stichpunkt.getClass().toString());

        for (int i = 0; i < strArr.length; i++) {
            if ((Integer) Integer.parseInt(strArr[i]) instanceof Integer) {
                found = stichpunktService.getRepository().findById(((Integer) Integer.parseInt(strArr[i])));
            } else {
                LOGGER.info("3");
                found = Optional.empty();
            }

            final List<Option<Integer>> professor = found
                    .map(professorService::findAllByStichpunkt)
                    .orElse(professorService.getRepository().findAll()).stream()
                    .map(e -> new Option<>(e.getNachname(), e.getId()))
                    .collect(Collectors.toList());
            sammlung.addAll(professor);
        }
        for (int i = 0; i < sammlung.size(); i++) {
            for (int j = i + 1; j < sammlung.size(); j++) {
                if (sammlung.get(i).getLabel().equals(sammlung.get(j).getLabel())) {
                    sammlung.remove(j);
                }
            }
        }
        // Probably add some process variables
        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("professor", sammlung);
        for (int i = 1; i < variables.size(); i++) {
            LOGGER.info("++ " + variables.get(i).toString());
        }
        return variables;
    }
    @ZeebeWorker(type = "antrag-speichern", autoComplete = true)
    public void AntragSpeichern(final ActivatedJob job){
        LOGGER.info("AntragSpeichern");
        Integer id_student = Integer.parseInt(job.getVariablesAsMap().get("id_student").toString());
        String vorname = job.getVariablesAsMap().get("vorname").toString();
        String nachname = job.getVariablesAsMap().get("nachname").toString();
        String email = job.getVariablesAsMap().get("email").toString();
        String titel = job.getVariablesAsMap().get("titel").toString();
        Integer typ = Integer.parseInt(job.getVariablesAsMap().get("typ").toString());
        studierenderService.studentEinspeichern(id_student, email, vorname, nachname);
        LOGGER.info("Methode ausgeführt");
    }
}
