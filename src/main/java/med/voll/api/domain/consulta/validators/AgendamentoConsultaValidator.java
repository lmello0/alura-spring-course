package med.voll.api.domain.consulta.validators;

import med.voll.api.domain.consulta.PostConsultaDTO;
import med.voll.api.domain.consulta.ValidationException;

public interface AgendamentoConsultaValidator {
    void validate(PostConsultaDTO data) throws ValidationException;
}
