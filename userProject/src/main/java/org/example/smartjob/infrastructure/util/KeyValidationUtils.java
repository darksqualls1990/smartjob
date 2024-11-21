package org.example.smartjob.infrastructure.util;

import org.example.smartjob.infrastructure.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KeyValidationUtils {

    @Value("${key.regex}")
    private String keyRegex;

    public void validateKey(String key) {
        Pattern pattern = Pattern.compile(keyRegex);
        Matcher matcher = pattern.matcher(key);

        if (!matcher.matches()) {
            throw new ValidationException("La clave debe contener al menos una mayúscula, un carácter especial y tener entre 8 y 20 caracteres.");
        }
    }
}
