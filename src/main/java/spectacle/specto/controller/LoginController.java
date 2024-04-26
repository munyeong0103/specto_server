package spectacle.specto.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import spectacle.specto.service.GoogleOAuthService;
import spectacle.specto.service.KakaoOAuthService;
import spectacle.specto.service.UserService;


@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final GoogleOAuthService googleOAuthService;

    private final KakaoOAuthService kakaoOAuthService;

    private final UserService userService;

    @GetMapping("/google")
    public RedirectView loadGoogleUrl () {
        return new RedirectView(googleOAuthService.loadToGoogleLogin());
    }

    // 카카오로그인 url로 redirction 시키는 api
    @GetMapping("/kakao")
    public RedirectView loadKakaoUrl () {
        return new RedirectView(kakaoOAuthService.loadToKakaoLogin());
    }

    // 회원가입,로그인 진행하는 api
    @GetMapping("/oauth2/code/google")
    public ResponseEntity<?> googleLogin (@RequestParam("code") String accessCode) {
        return ResponseEntity.ok().body(googleOAuthService.getGoogleAccessToken(accessCode));
    }

    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<?> kakaoLogin (@RequestParam("code") String accessCode) {
        return ResponseEntity.ok().body(kakaoOAuthService.getKakaoAccessToken(accessCode));
    }


    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("refresh") String refresh) {
        String refreshToken = refresh.substring(7);
        return ResponseEntity.ok().body(userService.issueRefreshToken(refreshToken));
    }
}

