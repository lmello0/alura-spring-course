package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PostPacienteDTO data, UriComponentsBuilder uriBuilder) {
        Paciente paciente = new Paciente(data);

        repository.save(paciente);

        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new PacienteReturnDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<GetPacienteDTO>> listar(@PageableDefault(sort = { "nome" })Pageable pagination) {
        Page<GetPacienteDTO> page = repository
                .findAllByAtivoTrue(pagination)
                .map(GetPacienteDTO::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@RequestBody @Valid UpdatePacienteDTO data, @PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);

        paciente.atualizarInformacoes(data);

        return ResponseEntity.ok(new PacienteReturnDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);

        paciente.delete();

        return ResponseEntity.noContent().build();
    }
}
