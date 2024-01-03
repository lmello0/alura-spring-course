package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.ValidationException;
import med.voll.api.domain.consulta.validators.AgendamentoConsultaValidator;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<AgendamentoConsultaValidator> validators;

    public ConsultaReturnDTO agendar(PostConsultaDTO data) {
        if (!pacienteRepository.existsById(data.idPaciente())) {
            throw new ValidationException("ID do paciente não existe");
        }

        if (data.idMedico() != null && !medicoRepository.existsById(data.idMedico())) {
            throw new ValidationException("ID do médico não existe");
        }

        validators.forEach(validator -> {
            validator.validate(data);
        });

        Paciente paciente = pacienteRepository.getReferenceById(data.idPaciente());
        Medico medico = chooseMedico(data);

        if (medico == null) {
            throw new ValidationException("Não existe médico disponível nessa data");
        }

        Consulta consulta = new Consulta(
                null,
                medico,
                paciente,
                data.date()
        );

        consultaRepository.save(consulta);

        return new ConsultaReturnDTO(consulta);
    }

    private Medico chooseMedico(PostConsultaDTO data) {
        if (data.idMedico() != null) {
            return medicoRepository.getReferenceById(data.idMedico());
        }

        if (data.especialidade() == null) {
            throw new ValidationException("Especialidade é obrigatória quiando médico não for escolhido");
        }

        return medicoRepository.getRandomMedicoOnDate(data.especialidade(), data.date());
    }

}
