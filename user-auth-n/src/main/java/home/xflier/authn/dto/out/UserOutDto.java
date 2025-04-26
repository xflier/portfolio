package home.xflier.authn.dto.out;

import lombok.Data;

@Data
public class UserOutDto {
    private long id;
    private String username;
    private String email;
}
