package test.java.com.streetfit;

import org.junit.jupiter.api.Test;  // JUnit 5

import main.java.com.streetfit.beans.CredentialsBean;
import main.java.com.streetfit.model.Role;

import static org.junit.jupiter.api.Assertions.*;  // Usa JUnit 5

class TestCredentialsBean {

    /*
        Test che verifica se viene sollevata un'eccezione
        nel caso di username non valido (null)
     */
    @Test
    void testConstructorWithInvalidUsername() {
        int validUsername;

        try {
            new CredentialsBean(null, "securePassword", Role.PARTICIPANT);
            validUsername = 1;
        } catch (IllegalArgumentException e) {
            validUsername = 0;
        }

        assertEquals(0, validUsername);  // Usa assertEquals di JUnit 5
    }

    /*
        Test che verifica se viene sollevata un'eccezione
        nel caso di password non valida (null)
     */
    @Test
    void testConstructorWithInvalidPassword() {
        int validPassword;

        try {
            new CredentialsBean("validUser", null, Role.PARTICIPANT);
            validPassword = 1;
        } catch (IllegalArgumentException e) {
            validPassword = 0;
        }

        assertEquals(0, validPassword);  // Usa assertEquals di JUnit 5
    }

    /*
        Test che verifica il comportamento corretto in caso di parametri validi
     */
    @Test
    void testConstructorWithValidInputs() {
        int validInputs;

        try {
            CredentialsBean cb = new CredentialsBean("trainer", "trainer", Role.TRAINER);
            validInputs = cb.getUsername().equals("trainer")
                       && cb.getPassword().equals("trainer")
                       && cb.getRole() == Role.TRAINER ? 1 : 0;
        } catch (Exception e) {
            validInputs = 0;
        }

        assertEquals(1, validInputs);  // Usa assertEquals di JUnit 5
    }
}

