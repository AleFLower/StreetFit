package test.java.com.streetfit.controller;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.model.TrainingStage;

import java.util.List;

public class TestGetAllStages {

    private AddStageController addStageController;

    @BeforeEach
    void setUp() {
        addStageController = new AddStageController();  // Setup del controller prima di ogni test
    }

    @Test
    void testGetAllStages() {
        // Aggiungi alcuni stage
        TrainingStage stage1 = new TrainingStage("Yoga for Beginners", "Basic yoga", "Yoga", null, "Room 101", 20);
        TrainingStage stage2 = new TrainingStage("Pilates for Experts", "Advanced pilates", "Pilates", null, "Room 102", 15);

        addStageController.addstage(stage1);
        addStageController.addstage(stage2);

        // Recupera gli stage aggiunti
        List<TrainingStage> stages = addStageController.getAllStages();

        // Verifica che la lista contenga gli stage
        assertNotNull(stages, "La lista degli stage non dovrebbe essere nulla");
        assertEquals(2, stages.size(), "La lista degli stage dovrebbe contenere 2 elementi");
        assertEquals("Yoga for Beginners", stages.get(0).getTitle(), "Il primo stage dovrebbe essere Yoga for Beginners");
        assertEquals("Pilates for Experts", stages.get(1).getTitle(), "Il secondo stage dovrebbe essere Pilates for Experts");
    }
}