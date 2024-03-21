package spectacle.specto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.impl.Base64UrlCodec;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import spectacle.specto.dto.loginDto.OAuthResponseDto;
import spectacle.specto.dto.loginDto.JwtDto;
import spectacle.specto.dto.loginDto.KakaoUserDataDto;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final String kakaoTokenUrl =  "https://kauth.kakao.com/oauth/token";

    @Value("${oauth2.client.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.client.kakao.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.kakao.redirect-uri}")
    private String redirectUrl;

    private final UserService userService;

    public String loadToKakaoLogin() {

        String loginUrl = "https://kauth.kakao.com/oauth/authorize?" + "client_id=" + clientId + "&redirect_uri=" + redirectUrl
                + "&response_type=code";
        return loginUrl;
    }

    public JwtDto getKakaoAccessToken(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("code", accessToken);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUrl);
        params.add("grant_type", "authorization_code");

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        // POST 요청 보내기
        ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.postForEntity(kakaoTokenUrl, requestEntity, OAuthResponseDto.class);

        Optional<KakaoUserDataDto> decodeInfo = decodeToken(responseEntity.getBody().getId_token().split("\\.")[1]);

        return userService.loginToService(decodeInfo.get().getNickname(), decodeInfo.get().getEmail());
    }

    // 카카오로그인을 통해 얻은 토큰을 decode 하여 사용자의 정보를 저장할 수 있는 형태로 가공하는 메서드
    public Optional<KakaoUserDataDto> decodeToken(String jwtToken) {
        byte[] decode = new Base64UrlCodec().decode(jwtToken);
        String decode_data = new String(decode, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            KakaoUserDataDto userDataDto = objectMapper.readValue(decode_data, KakaoUserDataDto.class);

            return Optional.ofNullable(userDataDto);
        }
        catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
