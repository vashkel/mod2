package com.epam.esm.controller.exception;

import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.NotValidParamsRequest;
import com.epam.esm.exception.PaginationException;
import com.epam.esm.exception.model.ApiErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({GiftCertificateNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> customerNotFound(GiftCertificateNotFoundException ex, WebRequest request, Locale locale) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withDetail("Not able to find gift certificate record")
                .withMessage(messageSource.getMessage(ex.getLocalizedMessage(), null, locale))
                .withError_code("404")
                .withStatus(HttpStatus.NOT_FOUND)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<ApiErrorResponse> entityExistsException(EntityExistsException ex, WebRequest request) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withDetail("you are creating already existed entity ")
                .withMessage(ex.getLocalizedMessage())
                .withError_code("403")
                .withStatus(HttpStatus.CONFLICT)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<ApiErrorResponse>(apiResponse, apiResponse.getStatus());
    }


    @ExceptionHandler({NotValidParamsRequest.class, PaginationException.class})
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(RuntimeException ex,
                                                                            WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withDetail("not valid arguments")
                .withMessage(ex.getLocalizedMessage())
                .withError_code("400")
                .withError_code(HttpStatus.NOT_ACCEPTABLE.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiErrorResponse> handleCustomAPIException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.BAD_GATEWAY)
                .withDetail("Something went wrong")
                .withMessage(ex.getLocalizedMessage())
                .withError_code("502")
                .withError_code(HttpStatus.BAD_GATEWAY.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex, HttpServletResponse response) throws IOException {
        ApiErrorResponse responseError = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withDetail(ex.getMessage())
                .withMessage(ex.getMessage())
                .withError_code("400")
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(responseError, responseError.getStatus());
    }


}
