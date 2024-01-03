package med.voll.api.infra.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TokenException extends RuntimeException {
    protected HttpStatus status;

    public TokenException(String message, Exception ex) {
        super(message, ex);
    }
}
