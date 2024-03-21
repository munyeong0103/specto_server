package spectacle.specto.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spectacle.specto.dto.loginDto.AuthenticationDto;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter{

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final AuthenticationManager authenticationManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization 값이 비어있으면 다음 필터 진행
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 다음 필터 진행
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        // 전송받은 Jwt Token이 만료되었으면 다음 필터 진행
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt Token에서 email 추출
        String email = JwtTokenUtil.getUserEmail(token, secretKey);

        // JwtAuthenticationToken 생성
        AuthenticationDto authenticationDto = new AuthenticationDto(token, email);
        // 생성한 토큰 기반 인증 진행
        Authentication authentication = this.authenticationManager.authenticate(authenticationDto);


        // 사용자 인증 성공 후, SecurityContextHolder에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
