package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.AuthData;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.TokenDTO;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthData data) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.login(), data.senha());

        Authentication auth = authManager.authenticate(authToken);

        String JWT = tokenService.genToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(JWT));
    }
}
