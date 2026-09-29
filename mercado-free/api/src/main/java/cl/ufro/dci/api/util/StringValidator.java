package cl.ufro.dci.api.util;

public class StringValidator {


    /**
     * Checks if the string starts with a number.
     * @param string
     * @return
     */

    public static Boolean startsWithNumber(String string) {
        return Character.isDigit(string.charAt(0));
    }

    /**
     * Checks if there isn't any illegal character on the string
     * (only letters, numbers and spaces).
     * @param string
     * @return
     */

    public static Boolean hasOnlyLettersNumbersSpaces(String string) {
        return string.matches("[a-zA-Z0-9 ]+");
    }

    /**
     * Validates string length.
     * @param string
     * @param minLength
     * @param maxLength
     * @return Boolean
     */

    public static Boolean validateStringLength(String string, int minLength, int maxLength) {
        return string.length() >= minLength && string.length() <= maxLength;
    }

    /**
     * Checks if String isn't empty.
     * @param string
     * @return Boolean
     */

    public static Boolean isNotEmpty(String string) {
        return !string.isEmpty();
    }

    /**
     * Validates search field.
     * - Must not be empty.
     * - Must not start with a number.
     * - Must not contain illegal characters.
     * - Max length has to be 64.
     * @param searchField
     * @return Boolean
     */

    public static Boolean validateSearchField(String searchField) {
        return isNotEmpty(searchField) &&
                !startsWithNumber(searchField) &&
                hasOnlyLettersNumbersSpaces(searchField) &&
                validateStringLength(searchField, 0, 64);
    }


}
