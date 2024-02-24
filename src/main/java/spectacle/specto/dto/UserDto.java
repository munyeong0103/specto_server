package spectacle.specto.dto;

import lombok.*;
import spectacle.specto.domain.User;

@Getter
@Setter
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

}
