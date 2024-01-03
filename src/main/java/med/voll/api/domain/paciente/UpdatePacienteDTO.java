package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.EnderecoDTO;

public record UpdatePacienteDTO(
        String nome,
        String telefone,
        EnderecoDTO endereco
) {
}
