package spectacle.specto.dto;

import lombok.*;
import spectacle.specto.domain.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    private String email;

    public User toEntity(String name, String email) {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
