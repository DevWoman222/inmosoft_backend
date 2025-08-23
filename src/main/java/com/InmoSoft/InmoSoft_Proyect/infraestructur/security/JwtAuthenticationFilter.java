package com.InmoSoft.InmoSoft_Proyect.infraestructur.security;

import com.InmoSoft.InmoSoft_Proyect.service.AuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


// Antes de que el controlador reciba la petición, este filtro revisa si el usuario está autenticado
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtGenerator jwtGenerator;

    private AuthenticatedService authenticatedService;

    @Autowired
    public JwtAuthenticationFilter(JwtGenerator jwtGenerator, AuthenticatedService authenticatedService) {
        this.jwtGenerator = jwtGenerator;
        this.authenticatedService = authenticatedService;
    }

    // Aquí es donde el filtro actúa en cada request
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        if(StringUtils.hasText(token) && jwtGenerator.validateToken(token)){

            String username = jwtGenerator.getUsernameFromJWT(token);

            UserDetails userDetails = authenticatedService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}

       /* El JwtAuthenticationFilter es como un portero de discoteca:

        1 Mira si traes tu brazalete (Bearer token).

        2 Verifica que el brazalete sea auténtico (validateToken).

        3 Busca tu nombre en la lista de invitados (loadUserByUsername).

        4 Si estás en la lista, te deja pasar y avisa al sistema de seguridad quién eres (SecurityContextHolder).*/