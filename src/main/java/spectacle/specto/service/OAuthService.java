package spectacle.specto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.impl.Base64UrlCodec;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import spectacle.specto.domain.User;
import spectacle.specto.dto.GoogleOAuthResponseDto;
import spectacle.specto.dto.JwtDto;
import spectacle.specto.dto.UserDataDto;
import spectacle.specto.dto.UserDto;
import spectacle.specto.jwt.JwtTokenUtil;
import spectacle.specto.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    private final String googleTokenUrl = "https://oauth2.googleapis.com/token";

    @Value("${oauth2.client.google.client-id}")
    private String clientId;

    @Value("${oauth2.client.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.google.redirect-uri}")
    private String redirectUrl;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final UserRepository userRepository;

    private String userName;
    private String email;

    // 구글 로그인 url로 redirction 시키는 메서드
    public String loadToLogin() {
        String loginUrl = "https://accounts.google.com/o/oauth2/v2/auth?" + "client_id=" + clientId + "&redirect_uri=" + redirectUrl
                + "&response_type=code&scope=profile%20email&access_type=offline";
        return loginUrl;
    }

    // 구글로그인을 통해 사용자의 정보를 가져오는 메서드
    public JwtDto getAccessToken(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessToken);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<GoogleOAuthResponseDto> responseEntity = restTemplate.postForEntity(googleTokenUrl, params, GoogleOAuthResponseDto.class);

        Optional<UserDataDto> decodeInfo = decodeToken(responseEntity.getBody().getId_token().split("\\.")[1]);

        return loginToService(decodeInfo.get().getName(), decodeInfo.get().getEmail());
    }

    // 구글로그인을 통해 얻은 토큰을 decode 하여 사용자의 정보를 저장할 수 있는 형태로 가공하는 메서드
    public Optional<UserDataDto> decodeToken(String jwtToken) {
        byte[] decode = new Base64UrlCodec().decode(jwtToken);
        String decode_data = new String(decode, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            UserDataDto userDataDto = objectMapper.readValue(decode_data, UserDataDto.class);

            return Optional.ofNullable(userDataDto);
        }
        catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 서비스의 자체 회원가입,로그인을 진행하는 메서드
    public JwtDto loginToService(String name, String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) { // 회원가입
            UserDto userDto = new UserDto();
            User signUpUser = userDto.toEntity(name, email);
            userRepository.save(signUpUser);
            return JwtTokenUtil.createToken(email, secretKey);
        }
        else { // 로그인
            return JwtTokenUtil.createToken(email, secretKey);
        }
    }

}

