package com.epam.esm.exception;

import com.epam.esm.entity.ApiErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GiftCertificateNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> customerNotFound(GiftCertificateNotFoundException ex, WebRequest request) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withDetail("Not able to find gift certificate record")
                .withMessage("Not a valid gift certificate id.Please provide a gift certificate id .")
                .withError_code("404")
                .withStatus(HttpStatus.NOT_FOUND)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NotValidParamsRequest.class)
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(NotValidParamsRequest ex,
                                                                            WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST )
                .withDetail("not valid arguments")
                .withMessage(ex.getMessage())
                .withError_code("400")
                .withError_code(HttpStatus.NOT_ACCEPTABLE.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<ApiErrorResponse>(response, response.getStatus());
    }

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<ApiErrorResponse> handleCustomAPIException(ServiceException ex, WebRequest request) {

        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.SERVICE_UNAVAILABLE)
                .withDetail("something went wrong")
                .withMessage(ex.getLocalizedMessage())
                .withError_code("503")
                .withError_code(HttpStatus.SERVICE_UNAVAILABLE.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiErrorResponse> handleCustomAPIException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status)
                .withDetail("Something went wrong")
                .withMessage(ex.getLocalizedMessage())
                .withError_code("502")
                .withError_code(HttpStatus.BAD_GATEWAY.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpStatus status, WebRequest request) {
        List<String> errorMsg = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status)
                .withDetail("not valid arguments")
                .withMessage(errorMsg.toString())
                .withError_code("406")
                .withError_code(HttpStatus.NOT_ACCEPTABLE.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
}
