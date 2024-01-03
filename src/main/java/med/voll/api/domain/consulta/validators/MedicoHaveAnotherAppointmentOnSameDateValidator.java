package med.voll.api.domain.consulta.validators;

import med.voll.api.domain.consulta.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.PostConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoHaveAnotherAppointmentOnSameDateValidator implements AgendamentoConsultaValidator {

    @Autowired
    private ConsultaRepository repository;

    public void validate(PostConsultaDTO data) throws ValidationException {
        boolean medicoHaveAnotherAppointmentOnSameDate = repository.existsByMedicoIdAndDate(data.idMedico(), data.date());

        if (medicoHaveAnotherAppointmentOnSameDate) {
            throw new ValidationException("Médico já possui outra consulta agendadda nesse mesmo horário");
        }
    }
}
