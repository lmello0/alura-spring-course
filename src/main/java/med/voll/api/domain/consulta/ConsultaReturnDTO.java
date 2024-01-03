package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record ConsultaReturnDTO(
        Long id,
        Long idMedico,
        Long idPaciente,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime date
) {
        public ConsultaReturnDTO(Consulta consulta) {
                this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getDate());
        }
}
