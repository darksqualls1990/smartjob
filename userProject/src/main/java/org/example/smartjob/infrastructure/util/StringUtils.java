package org.example.smartjob.infrastructure.util;

import java.util.regex.Pattern;

public class StringUtils {

    // Expresión regular para validar el formato de correo
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
