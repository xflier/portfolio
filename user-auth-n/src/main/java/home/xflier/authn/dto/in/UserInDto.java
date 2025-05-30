package home.xflier.authn.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * UserInDto is a Data Transfer Object (DTO) that represents the input data for a user.
 * It contains fields for user ID, username, password, and email.
 * 
 * @author xflier
 * @version 1.0
 * @date 2023/10/27
 */
@Data
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
