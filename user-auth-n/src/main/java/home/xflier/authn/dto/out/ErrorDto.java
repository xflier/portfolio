package home.xflier.authn.dto.out;

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
