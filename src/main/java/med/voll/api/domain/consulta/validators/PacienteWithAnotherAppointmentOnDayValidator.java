package med.voll.api.domain.consulta.validators;

import med.voll.api.domain.consulta.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.PostConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PacienteWithAnotherAppointmentOnDayValidator implements AgendamentoConsultaValidator {

    @Autowired
    private ConsultaRepository repository;

    public void validate(PostConsultaDTO data) throws ValidationException {
        LocalDateTime firstHour = data.date().withHour(7);
        LocalDateTime lastHour = data.date().withHour(18);

        boolean pacienteWithAnotherAppointmentOnDay = repository.existsByPacienteIdAndDateBetween(data.idPaciente(), firstHour, lastHour);

        if (pacienteWithAnotherAppointmentOnDay) {
            throw new ValidationException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
