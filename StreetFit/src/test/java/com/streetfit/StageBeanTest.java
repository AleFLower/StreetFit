package test.java.com.streetfit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import main.java.com.streetfit.beans.StageBean;



class StageBeanTest {

    // Test per il metodo isValid() in caso di categoria non valida
    @Test
     void testInvalidCategory() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "InvalidCategory", new Date(), "Place", 20);
        assertFalse(stage.isValid(), "Category should be valid (Functional, Yoga, HIIT, Stretching, Pilates, Crossfit)");
    }

    // Test per il metodo isValid() in caso di max partecipanti non valido (<= 0)
    @Test
     void testInvalidMaxParticipants() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), "Place", 0);
        assertFalse(stage.isValid(), "Max participants should be greater than 0");
    }

}
