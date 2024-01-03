package med.voll.api.domain.consulta.validators;

import med.voll.api.domain.consulta.ValidationException;
import med.voll.api.domain.consulta.PostConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceTimeValidator implements AgendamentoConsultaValidator {

    public void validate(PostConsultaDTO data) throws ValidationException {
        LocalDateTime dateConsulta = data.date();
        LocalDateTime now = LocalDateTime.now();
        long diffInMinutes = Duration.between(now, dateConsulta).toMinutes();

        if (diffInMinutes < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
