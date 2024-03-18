package spectacle.specto.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import spectacle.specto.dto.AuthenticationDto;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            return new AuthenticationDto((String) authentication.getPrincipal(), authentication.getCredentials());

        } catch (MalformedJwtException | MissingClaimException ex) {
            // JWT 인증 에러
            throw new RuntimeException();

        } catch (ExpiredJwtException ex) {
            // JWT 만료 에러
            throw new RuntimeException();
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationDto.class.isAssignableFrom(authentication);
    }
}
