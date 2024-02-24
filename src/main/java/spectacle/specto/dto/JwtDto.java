package spectacle.specto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {

    private String grantType;

    private String accessToken;

    private String refreshToken;
}
