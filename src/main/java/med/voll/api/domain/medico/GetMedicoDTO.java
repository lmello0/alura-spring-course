package med.voll.api.domain.medico;

public record GetMedicoDTO(
    Long id,
    String nome,
    String email,
    String crm,
    Especialidade especialidade
) {
    public GetMedicoDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
