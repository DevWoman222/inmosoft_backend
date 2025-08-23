package com.InmoSoft.InmoSoft_Proyect.infraestructur.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {

    // Ingresar informacion para crear un objeto de tipo logger --mensajes de registro--
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    // Traer el valor de una variable de entorno a una clase
    @Value("${security.jwt.secret-key}") //application.properties donde se encuentra esta variable de entorno
    private String secretKey; // su valor


    // Traer los datos de autenticación de un User
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());


        // Uso de currentdate.getTime() para obtener la fecha y hora actual y sumarle el tiempo de expiración
        Date currentDate = new Date(); //ccreacion
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);//tiempo de expiración


        //Token que se va a crear deacuerdo a la información del usuario
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("Usuario", roles)  // Definir claim parte final de nuestro token
                .issuedAt(currentDate)  // Tiempo real en el que se creo
                .expiration(expireDate) // Tiempo de expiración
                .signWith(getKey()) // Llave secreta la llammamos desde nuestro atributo "secret-key" cifrado
                .compact();
    }

    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

    public String getUsernameFromJWT(String token) {
        return getClaims(token, Claims::getSubject);
    }

    // Enviar cada uno de los mensajes que pueden verificarse al crear un token----
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token mal formado " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Token vacío " + e.getMessage());
        } catch (SignatureException e) {
            logger.error("Error en al forma " + e.getMessage());
        }
        return false;
    }

    //Para poder generar nuevos token
    public String refreshToken(Authentication authentication) {
        try {
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            List<String> roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            Date currentDate = new Date();
            Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

            // Generando un nuevo token
            return Jwts.builder()
                    .subject(userPrincipal.getUsername())
                    .claim("roles", roles)
                    .issuedAt(currentDate)
                    .expiration(expireDate)
                    .signWith(getKey())
                    .compact();

        } catch (Exception e) {
            throw new RuntimeException("Error internal server");
        }
    }
}