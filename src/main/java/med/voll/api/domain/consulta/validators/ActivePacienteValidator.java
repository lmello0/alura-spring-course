package med.voll.api.domain.consulta.validators;

import med.voll.api.domain.consulta.ValidationException;
import med.voll.api.domain.consulta.PostConsultaDTO;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePacienteValidator implements AgendamentoConsultaValidator {

    @Autowired
    private PacienteRepository repository;

    public  void validate(PostConsultaDTO data) throws ValidationException {
        boolean isPacienteAtivo = repository.findAtivoById(data.idPaciente());

        if (!isPacienteAtivo) {
            throw new ValidationException("Consulta não pode ser agendada para um paciente inativo");
        }
    }
}
