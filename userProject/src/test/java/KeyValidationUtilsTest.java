import org.example.smartjob.infrastructure.exception.ValidationException;
import org.example.smartjob.infrastructure.util.KeyValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class KeyValidationUtilsTest {

    private KeyValidationUtils keyValidationUtils;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        keyValidationUtils = new KeyValidationUtils();
        Field keyRegexField = KeyValidationUtils.class.getDeclaredField("keyRegex");
        keyRegexField.setAccessible(true);
        keyRegexField.set(keyValidationUtils, "^(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20}$");
    }

    @Test
    void testValidateKey_validKey() {
        // Test para validar que la clave sea válida (con mayúsculas, caracteres especiales y longitud adecuada)
        String validKey = "Valid1@Key";

        assertDoesNotThrow(() -> keyValidationUtils.validateKey(validKey));
    }

    @Test
    void testValidateKey_invalidKey_missingUppercase() {
        // Test para validar que la clave no pase si no tiene mayúsculas
        String invalidKey = "invalid1@key";

        ValidationException exception = assertThrows(ValidationException.class, () -> keyValidationUtils.validateKey(invalidKey));
        assertEquals("La clave debe contener al menos una mayúscula, un carácter especial y tener entre 8 y 20 caracteres.", exception.getMessage());
    }

    @Test
    void testValidateKey_invalidKey_missingSpecialCharacter() {
        // Test para validar que la clave no pase si no tiene un carácter especial
        String invalidKey = "InvalidKey123";

        ValidationException exception = assertThrows(ValidationException.class, () -> keyValidationUtils.validateKey(invalidKey));
        assertEquals("La clave debe contener al menos una mayúscula, un carácter especial y tener entre 8 y 20 caracteres.", exception.getMessage());
    }

    @Test
    void testValidateKey_invalidKey_tooShort() {
        // Test para validar que la clave no pase si tiene menos de 8 caracteres
        String invalidKey = "Inv@1";

        ValidationException exception = assertThrows(ValidationException.class, () -> keyValidationUtils.validateKey(invalidKey));
        assertEquals("La clave debe contener al menos una mayúscula, un carácter especial y tener entre 8 y 20 caracteres.", exception.getMessage());
    }
}
