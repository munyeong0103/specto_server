package spectacle.specto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.impl.Base64UrlCodec;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import spectacle.specto.dto.GoogleOAuthResponseDto;
import spectacle.specto.dto.UserDataDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OAuthService {
    private final String googleTokenUrl = "https://oauth2.googleapis.com/token";

    @Value("${oauth2.client.google.client-id}")
    private String clientId;

    @Value("${oauth2.client.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.google.redirect-uri}")
    private String redirectUrl;

    public String loadToLogin() {
        String loginUrl = "https://accounts.google.com/o/oauth2/v2/auth?" + "client_id=" + clientId + "&redirect_uri=" + redirectUrl
                + "&response_type=code&scope=profile%20email&access_type=offline";
        return loginUrl;
    }

    public ResponseEntity<GoogleOAuthResponseDto> getAccessToken(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessToken);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<GoogleOAuthResponseDto> responseEntity = restTemplate.postForEntity(googleTokenUrl, params, GoogleOAuthResponseDto.class);

        Optional<UserDataDto> decodeInfo = decodeToken(responseEntity.getBody().getId_token().split("\\.")[1]);

        return responseEntity;
    }

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

}

