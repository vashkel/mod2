package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such tag")
public class TagNotFoundException extends RuntimeException {

    private Long id;
    private String name;
    private String message;

    public TagNotFoundException(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public TagNotFoundException(String message){
        this.message = message;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }




}