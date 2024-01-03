package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.EnderecoDTO;

public record UpdateMedicoDTO(
        String nome,
        String telefone,
        EnderecoDTO endereco
) {
}
