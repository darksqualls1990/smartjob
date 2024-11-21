import org.example.smartjob.infrastructure.exception.ValidationException;
import org.example.smartjob.infrastructure.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @InjectMocks
    private StringUtils stringUtils;

    @BeforeEach
    public void setUp() throws Exception {
    }
    @Test
    void testIsValidEmail_validEmail() {
        // Test para validar un correo electrónico correcto
        String email = "test@dominio.cl";
        boolean isValid = stringUtils.isValidEmail(email);

        assertTrue(isValid, "El correo debe ser válido");
    }

    @Test
    void testIsValidEmail_invalidEmail() {
        // Test para validar un correo electrónico incorrecto
        String email = "test@dominio";
        boolean isValid = stringUtils.isValidEmail(email);

        assertFalse(isValid, "El correo debe ser inválido");
    }
}
