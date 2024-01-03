package med.voll.api.infra.security.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends TokenException {
    public InvalidTokenException(Exception ex) {
        super("Token inválido", ex);
        this.status = HttpStatus.FORBIDDEN;
    }
}
