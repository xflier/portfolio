package home.xflier.authn.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginOutDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long accessExpiresIn;
    private long refreshExpiresIn;
}

