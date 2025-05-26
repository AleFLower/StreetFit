package test.java.com.streetfit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import main.java.com.streetfit.beans.StageBean;



public class StageBeanTest {

    // Test per il metodo isValid() in caso di titolo valido
    @Test
    public void testValidTitle() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), "Place", 20);
        assertTrue(stage.isValid(), "Title should be valid");
    }

    // Test per il metodo isValid() in caso di titolo non valido (null)
    @Test
    public void testInvalidTitleNull() {
        StageBean stage = new StageBean(null, "Itinerary", "Yoga", new Date(), "Place", 20);
        assertFalse(stage.isValid(), "Title should not be null");
    }

    // Test per il metodo isValid() in caso di titolo vuoto
    @Test
    public void testInvalidTitleEmpty() {
        StageBean stage = new StageBean("", "Itinerary", "Yoga", new Date(), "Place", 20);
        assertFalse(stage.isValid(), "Title should not be empty");
    }

    // Test per il metodo isValid() in caso di itinerario valido
    @Test
    public void testValidItinerary() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), "Place", 20);
        assertTrue(stage.isValid(), "Itinerary should be valid");
    }

    // Test per il metodo isValid() in caso di itinerario vuoto
    @Test
    public void testInvalidItineraryEmpty() {
        StageBean stage = new StageBean("Stage 1", "", "Yoga", new Date(), "Place", 20);
        assertFalse(stage.isValid(), "Itinerary should not be empty");
    }

    // Test per il metodo isValid() in caso di categoria valida
    @Test
    public void testValidCategory() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), "Place", 20);
        assertTrue(stage.isValid(), "Category should be valid");
    }

    // Test per il metodo isValid() in caso di categoria non valida
    @Test
    public void testInvalidCategory() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "InvalidCategory", new Date(), "Place", 20);
        assertFalse(stage.isValid(), "Category should be valid (Functional, Yoga, HIIT, Stretching, Pilates, Crossfit)");
    }

    // Test per il metodo isValid() in caso di data futura
    @Test
    public void testValidDate() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(System.currentTimeMillis() + 100000), "Place", 20);
        assertTrue(stage.isValid(), "Date should be valid (in the future)");
    }

    // Test per il metodo isValid() in caso di data passata
    @Test
    public void testInvalidDate() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(System.currentTimeMillis() - 100000), "Place", 20);
        assertFalse(stage.isValid(), "Date should not be in the past");
    }

    // Test per il metodo isValid() in caso di posto valido
    @Test
    public void testValidPlace() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), "Place", 20);
        assertTrue(stage.isValid(), "Place should be valid");
    }

    // Test per il metodo isValid() in caso di posto non valido (null)
    @Test
    public void testInvalidPlaceNull() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), null, 20);
        assertFalse(stage.isValid(), "Place should not be null");
    }

    // Test per il metodo isValid() in caso di max partecipanti valido
    @Test
    public void testValidMaxParticipants() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), "Place", 20);
        assertTrue(stage.isValid(), "Max participants should be valid");
    }

    // Test per il metodo isValid() in caso di max partecipanti non valido (<= 0)
    @Test
    public void testInvalidMaxParticipants() {
        StageBean stage = new StageBean("Stage 1", "Itinerary", "Yoga", new Date(), "Place", 0);
        assertFalse(stage.isValid(), "Max participants should be greater than 0");
    }

}
