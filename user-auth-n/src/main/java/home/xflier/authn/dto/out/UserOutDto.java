package home.xflier.authn.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {
    private long id;
    private String username;
    private String email;
}
