package home.xflier.authn.dto.out;

/**
 * ErrorDto is a Data Transfer Object (DTO) that represents an error response.
 * It contains fields for error code, error message, and additional details.
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
public class ErrorDto {
    private String errorCode;
    private String errorMessage;
    private String detail;
    
    public ErrorDto(String errorCode, String errorMessage, String detail) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.detail = detail;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDetail() {
        return detail;
    }
}
