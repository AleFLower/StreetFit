package test.java.com.streetfit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.streetfit.controller.AddStageController;
import com.streetfit.model.TrainingStage;

import java.util.List;

public class TestAddStageIncreases {

    private AddStageController addStageController;

    @BeforeEach
    void setUp() {
        addStageController = new AddStageController();  // Setup del controller prima di ogni test
    }

    @Test
    void testAddStageIncreasesTotalStages() {
        // Aggiungi uno stage iniziale
        TrainingStage stage1 = new TrainingStage("Yoga for Beginners", "Basic yoga", "Yoga", null, "Room 101", 20);
        addStageController.addstage(stage1);

        // Verifica il numero di stage dopo aver aggiunto il primo stage
        List<TrainingStage> stagesAfterFirst = addStageController.getAllStages();
        int initialSize = stagesAfterFirst.size();

        // Aggiungi un altro stage
        TrainingStage stage2 = new TrainingStage("Pilates for Experts", "Advanced pilates", "Pilates", null, "Room 102", 15);
        addStageController.addstage(stage2);

        // Verifica che il numero di stage sia aumentato di 1
        List<TrainingStage> stagesAfterSecond = addStageController.getAllStages();
        assertEquals(initialSize + 1, stagesAfterSecond.size(), "Il numero di stage non è stato incrementato correttamente");
    }
}

