package br.com.forumhub.demo.config.security;

import br.com.forumhub.demo.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    private static final String SECRET_KEY = "MINHA_CHAVE_SECRETA_1234567890";
    private static final long EXPIRATION_TIME = 86400000;

    public String gerarToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date expiracao = new Date(hoje.getTime() + EXPIRATION_TIME);

        Key chave = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setIssuer("API Fórum")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(expiracao)
                .signWith(chave, SignatureAlgorithm.HS256)
                .compact();
    }
}

