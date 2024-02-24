package spectacle.specto.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spectacle.specto.domain.User;
import spectacle.specto.service.OAuthService;

import java.io.IOException;

// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

//    @Value("${secret-key}")
//    private String secretKey;

    private String secretKey = "this is specto secret key by munyeong";

    private final OAuthService oAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
        if(authorizationHeader == null) {
            System.out.println("header가 필요합니다");
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        System.out.println("token : " + token);
        System.out.println("key : " + secretKey);
        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("===============");
        System.out.println("===============");
        // Jwt Token에서 name 추출
        String email = JwtTokenUtil.getUserEmail(token, secretKey);
        System.out.println("email : " + email);
        // 추출한 loginId로 User 찾아오기
        User user = oAuthService.getUserData(email);
        System.out.println("findbyemail :" + user.getName());
        String name = user.getName();
        // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                name, token);
        System.out.println("token : " + authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println("token : " + authenticationToken);
        filterChain.doFilter(request, response);
    }
}
