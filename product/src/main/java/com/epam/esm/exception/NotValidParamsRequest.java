package com.epam.esm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "not valid params was entered")
public class NotValidParamsRequest extends RuntimeException {


}
