package med.voll.api.domain.consulta.validators;

import med.voll.api.domain.consulta.ValidationException;
import med.voll.api.domain.consulta.PostConsultaDTO;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveMedicoValidator implements AgendamentoConsultaValidator {

    @Autowired
    private MedicoRepository repository;

    public void validate(PostConsultaDTO data) throws ValidationException {
        if (data.idMedico() == null) {
            return;
        }

        boolean isMedicoAtivo = repository.findAtivoById(data.idMedico());

        if (!isMedicoAtivo) {
            throw new ValidationException("Consulta não pode ser agendada com médico inativo");
        }
    }
}
