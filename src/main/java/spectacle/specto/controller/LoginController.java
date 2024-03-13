package spectacle.specto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import spectacle.specto.service.OAuthService;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuthService oAuthService;

    // 구글로그인 url로 redirction 시키는 api
    @GetMapping("/login")
    public RedirectView loadUrl () {
        return new RedirectView(oAuthService.loadToLogin());
    }

    // 회원가입,로그인 진행하는 api
    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<?> login (@RequestParam("code") String accessCode) {
        return ResponseEntity.ok().body(oAuthService.getAccessToken(accessCode));
    }

    // test api
    @GetMapping("/test")
    public ResponseEntity<?> testAPI () {
        String text = "test api";
        return ResponseEntity.ok().body(text);
    }

}

