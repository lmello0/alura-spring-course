package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Paciente(PostPacienteDTO data) {
        this.nome = data.nome();
        this.email = data.email();
        this.telefone = data.telefone();
        this.cpf = data.cpf();
        this.endereco = new Endereco(data.endereco());
        this.ativo = true;
    }

    public void atualizarInformacoes(UpdatePacienteDTO data) {
        if (data.nome() != null) {
            this.nome = data.nome();
        }

        if (data.telefone() != null) {
            this.telefone = data.telefone();
        }

        if (data.endereco() != null) {
            this.endereco.atualizarInformacoes(data.endereco());
        }
    }

    public void delete() {
        this.ativo = false;
    }
}
