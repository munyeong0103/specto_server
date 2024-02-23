package spectacle.specto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import spectacle.specto.dto.GoogleOAuthResponseDto;
import spectacle.specto.service.OAuthService;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuthService oAuthService;

    @GetMapping("/login")
    public RedirectView loadUrl () {
        return new RedirectView(oAuthService.loadToLogin());
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<GoogleOAuthResponseDto> getAccessCode (@RequestParam("code") String accessCode) {
        return oAuthService.getAccessToken(accessCode);
    }

}

