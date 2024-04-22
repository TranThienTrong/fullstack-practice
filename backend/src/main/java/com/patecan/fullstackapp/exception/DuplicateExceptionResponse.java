package com.patecan.fullstackapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateExceptionResponse extends RuntimeException{

    public DuplicateExceptionResponse(String message){
        super(message);
    }
}
