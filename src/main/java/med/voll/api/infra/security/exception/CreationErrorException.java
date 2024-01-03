package med.voll.api.infra.security.exception;

import org.springframework.http.HttpStatus;

public class CreationErrorException extends TokenException {
    public CreationErrorException(Exception ex) {
        super("Erro ao gerar token JWT", ex);
        this.status = HttpStatus.FORBIDDEN;
    }
}
