package med.voll.api.domain.consulta.validators;

import med.voll.api.domain.consulta.ValidationException;
import med.voll.api.domain.consulta.PostConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ClinicOpeningHoursValidator implements AgendamentoConsultaValidator {
    public void validate(PostConsultaDTO data) throws ValidationException {
        LocalDateTime dateConsulta = data.date();

        boolean isDomingo = dateConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isBeforeOpen = dateConsulta.getHour() < 7;
        boolean isAfterClose = dateConsulta.getHour() > 18;

//        if (isDomingo || isBeforeOpen || isAfterClose) {
//            throw new ValidationException("Consulta fora do horário de atendimento");
//        }

        if (isBeforeOpen || isAfterClose) {
            throw new ValidationException("Consulta fora do horário de atendimento");
        }
    }
}
