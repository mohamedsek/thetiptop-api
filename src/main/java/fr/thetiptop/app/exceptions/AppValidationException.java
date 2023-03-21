package fr.thetiptop.app.exceptions;

public class AppValidationException extends RuntimeException {

    private String fieldName;

    public AppValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
