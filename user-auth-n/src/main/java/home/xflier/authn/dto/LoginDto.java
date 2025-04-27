package home.xflier.authn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {

    @Schema(description = "username must be at least 4 chars")
    @NotBlank(message = "Username can't be blank!")
    @Size(min = 4, max = 255, message = "username length should be between 4 and 255!")
    private String username;

    @Schema(description = "password must be at least 4 chars")
    @NotBlank(message = "Password can't be blank!")
    @Size(min = 4, max = 255, message = "password length should be between 4 and 255!")
    private String passwd;

}
