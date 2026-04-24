package giuliacrepaldi.Event_Management.security;

import giuliacrepaldi.Event_Management.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenTools tokenTools;

    public TokenFilter(TokenTools tokenTools) {
        this.tokenTools = tokenTools;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. Verifichiamo se la richiesta contiene l'header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Unauthorized. Enter the correct token");

        //2.Estraggo il token dall'header
        String accessToken = authHeader.replace("Bearer ", "");

        //3. Verifico il Token
        tokenTools.verifyToken(accessToken);

        //4. fine catena filtri
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
