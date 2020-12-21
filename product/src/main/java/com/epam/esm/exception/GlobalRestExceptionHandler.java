package com.epam.esm.exception;

import com.epam.esm.exception.error.Error;
import com.epam.esm.exception.model.ApiErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
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

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withDetail(messageSource.getMessage(Error.ERROR02.getDescription(), null, Locale.ENGLISH))
                .withError_code(Error.ERROR02.name())
                .withStatus(HttpStatus.BAD_REQUEST)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            GiftCertificateNotFoundException.class,
            OrderNotFoundException.class,
            TagNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ApiErrorResponse> customerNotFound(RuntimeException ex, WebRequest request, Locale locale) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withMessage(messageSource.getMessage(ex.getLocalizedMessage(), null, locale))
                .withDetail(messageSource.getMessage(Error.ERROR01.getDescription(), null, locale))
                .withError_code(Error.ERROR01.name())
                .withStatus(HttpStatus.NOT_FOUND)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNamePasswordNotValid(RuntimeException e, WebRequest request,
                                                                           Locale locale) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withMessage(messageSource.getMessage(e.getLocalizedMessage(), null, locale))
                .withDetail(messageSource.getMessage(Error.ERROR05.getDescription(), null, locale))
                .withError_code(Error.ERROR05.name())
                .withStatus(HttpStatus.FORBIDDEN)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeny(RuntimeException e, WebRequest request,
                                                             Locale locale) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withMessage(e.getLocalizedMessage())
                .withDetail(messageSource.getMessage(Error.ERROR05.getDescription(), null, locale))
                .withError_code(Error.ERROR05.name())
                .withStatus(HttpStatus.FORBIDDEN)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenValid(RuntimeException e, WebRequest request,
                                                             Locale locale) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withMessage(messageSource.getMessage(e.getLocalizedMessage(), null, locale))
                .withDetail(messageSource.getMessage(Error.ERROR06.getDescription(), null, locale))
                .withError_code(Error.ERROR06.name())
                .withStatus(HttpStatus.UNAUTHORIZED)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<ApiErrorResponse> entityExistsException(EntityExistsException ex, WebRequest request,
                                                                  Locale locale) {
        ApiErrorResponse apiResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withMessage(messageSource.getMessage(ex.getLocalizedMessage(), null, locale))
                .withDetail(messageSource.getMessage(Error.ERROR07.getDescription(), null, locale))
                .withError_code(Error.ERROR07.name())
                .withStatus(HttpStatus.CONFLICT)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler({NotValidParamsRequest.class, PaginationException.class})
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request, Locale locale) {
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withMessage(messageSource.getMessage(ex.getLocalizedMessage(), null, locale))
                .withError_code(Error.ERROR03.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomAPIException(
            Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, Locale locale) {
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.BAD_GATEWAY)
                .withDetail("Something went wrong")
                .withMessage(messageSource.getMessage(ex.getLocalizedMessage(), null, locale))
                .withError_code("500")
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error_code", Error.ERROR04.name());
        body.put("details of error", messageSource.getMessage(Error.ERROR04.getDescription(), null, Locale.ENGLISH));
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex,
                                                               HttpServletResponse response) throws IOException {
        ApiErrorResponse responseError = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withDetail(messageSource.getMessage(Error.ERROR03.getDescription(), null, Locale.ENGLISH))
                .withMessage(ex.getLocalizedMessage())
                .withError_code(Error.ERROR03.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(responseError, responseError.getStatus());
    }

}
