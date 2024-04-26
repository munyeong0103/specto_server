package spectacle.specto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spectacle.specto.domain.User;
import spectacle.specto.dto.loginDto.JwtDto;
import spectacle.specto.dto.UserDto;
import spectacle.specto.jwt.JwtTokenUtil;
import spectacle.specto.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final UserRepository userRepository;

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

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getCredentials(); //email

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));
        return user;
    }

    public JwtDto issueRefreshToken(String refreshToken) {
        String email = JwtTokenUtil.getUserEmail(refreshToken, secretKey);
        return JwtTokenUtil.createToken(email, secretKey);
    }

}
