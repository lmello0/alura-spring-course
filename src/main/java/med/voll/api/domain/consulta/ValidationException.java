package med.voll.api.domain.consulta;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
