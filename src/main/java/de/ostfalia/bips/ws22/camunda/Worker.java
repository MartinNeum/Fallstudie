package de.ostfalia.bips.ws22.camunda;

import de.ostfalia.bips.ws22.camunda.database.domain.Stichpunkt;
import de.ostfalia.bips.ws22.camunda.database.service.ProfessorService;
import de.ostfalia.bips.ws22.camunda.database.service.StichpunktService;
import de.ostfalia.bips.ws22.camunda.model.Option;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Worker(StichpunktService stichpunktService,
                  ProfessorService professorService) {
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

        // Get the
        final Object stichpunkt = job.getVariablesAsMap().get("stichpunkt");
        final Optional<Stichpunkt> found;
        if (stichpunkt instanceof Integer) {
            found = stichpunktService.getRepository().findById(((Integer) stichpunkt));
        } else if (stichpunkt != null && stichpunkt.toString().matches("\\d+")) {
            found = stichpunktService.getRepository().findById(Integer.parseInt(stichpunkt.toString()));
        } else {
            found = Optional.empty();
        }
        final List<Option<String>> professor = found
                .map(professorService::findAllByStichpunkt)
                .orElse(professorService.getRepository().findAll()).stream()
                .map(e -> new Option<>(e.getVorname(), e.getNachname()))
                .collect(Collectors.toList());

        // Probably add some process variables
        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("professor", professor);
        return variables;
    }
}
