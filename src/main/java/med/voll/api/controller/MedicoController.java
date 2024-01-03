package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
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
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PostMedicoDTO data, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(data);
        repository.save(medico);

        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new MedicoReturnDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<GetMedicoDTO>> listar(@PageableDefault(sort = { "nome" }) Pageable pagination) {
        Page<GetMedicoDTO> page = repository
                .findAllByAtivoTrue(pagination)
                .map(GetMedicoDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new MedicoReturnDTO(medico));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@RequestBody @Valid UpdateMedicoDTO data, @PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);

        medico.atualizarInformacoes(data);

        return ResponseEntity.ok(new MedicoReturnDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);

        medico.delete();

        return ResponseEntity.noContent().build();
    }
}
