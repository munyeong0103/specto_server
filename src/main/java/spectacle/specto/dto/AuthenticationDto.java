package spectacle.specto.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

// 권한 설정 진행하는 dto
public class AuthenticationDto extends AbstractAuthenticationToken {

    private Object email;

    private String token;

    public AuthenticationDto(String token, Object email) {
        super(Arrays.asList(new SimpleGrantedAuthority((String)email)));
        this.setAuthenticated(true);
        this.email = email;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return email;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}

