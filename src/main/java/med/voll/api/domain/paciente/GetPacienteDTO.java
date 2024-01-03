package med.voll.api.domain.paciente;

public record GetPacienteDTO(
        Long id,
        String nome,
        String email,
        String cpf
) {
    public GetPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
