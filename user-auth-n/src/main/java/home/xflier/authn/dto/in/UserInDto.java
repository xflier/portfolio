package home.xflier.authn.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserInDto {
    private long id;

    @Schema(description = "username must be at least 4 chars")
    @NotBlank(message = "Username can't be blank!")
    @Size(min = 4, max = 255, message = "username length should be between 4 and 255!")
    private String username;

    @Schema(description = "password must be at least 4 chars")
    @NotBlank(message = "Password can't be blank!")
    @Size(min = 4, max = 255, message = "password length should be between 4 and 255!")
    private String passwd;

    @Schema(description = "valid email")
    @NotBlank(message = "Email can't be blank!")
    @Email(message = "Invalid Email address !")
    private String email;
}
