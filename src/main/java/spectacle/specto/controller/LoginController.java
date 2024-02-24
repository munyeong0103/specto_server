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

    @GetMapping("/login")
    public RedirectView loadUrl () {
        return new RedirectView(oAuthService.loadToLogin());
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<?> login (@RequestParam("code") String accessCode) {
        return ResponseEntity.ok().body(oAuthService.getAccessToken(accessCode));
    }


    @GetMapping("/test")
    public ResponseEntity<?> testAPI () {
        String text = "need header";
        System.out.println(text);
        return ResponseEntity.ok().body(text);
    }
}

