package home.xflier.authn.aop;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import home.xflier.authn.dto.out.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerErrorHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(ControllerErrorHandler.class);

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleExceptions(Exception ex, HttpServletRequest req) {

        ResponseEntity<ErrorDto> response;

        LOGGER.error("Caught an exception -  " + req.getRequestURI() + " : " + ex.getClass());
        HttpStatus httpStatus;

        if (ex instanceof IllegalArgumentException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NoSuchElementException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        StackTraceElement[] stackTrace = ex.getStackTrace();
        StackTraceElement origin = stackTrace[0];
        LOGGER.error(ex.getClass() +" - " + ex.getMessage() + " - originated in:");
        LOGGER.error("Class: " + origin.getClassName());
        LOGGER.error("Method: " + origin.getMethodName());
        LOGGER.error("Line: " + origin.getLineNumber());

        ErrorDto errorDto = new ErrorDto("" + httpStatus.value(), httpStatus.name(), ex.getMessage());
        response = new ResponseEntity<>(errorDto, httpStatus);

        return response;

    }
}