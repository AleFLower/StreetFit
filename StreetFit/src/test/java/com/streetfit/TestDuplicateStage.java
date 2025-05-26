package test.java.com.streetfit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.model.TrainingStage;

public class TestDuplicateStage {

    private AddStageController addStageController;

    @BeforeEach
    void setUp() {
        addStageController = new AddStageController();  // Setup del controller prima di ogni test
    }

    @Test
    void testAddStageWithDuplicateTitle() {
        // Aggiungi uno stage iniziale
        TrainingStage stage1 = new TrainingStage("Yoga for Beginners", "Basic yoga", "Yoga", null, "Room 101", 20);
        addStageController.addstage(stage1);

        // Tenta di aggiungere uno stage con lo stesso titolo
        TrainingStage stage2 = new TrainingStage("Yoga for Beginners", "Advanced yoga", "Yoga", null, "Room 102", 25);

        // Verifica che venga lanciata un'eccezione per il titolo duplicato
        assertThrows(IllegalStateException.class, () -> addStageController.addstage(stage2),
                 "Non dovrebbe essere possibile aggiungere uno stage con un titolo duplicato");
    }
}