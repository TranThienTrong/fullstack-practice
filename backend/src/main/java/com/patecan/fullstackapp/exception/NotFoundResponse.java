package com.patecan.fullstackapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundResponse extends RuntimeException{

    public NotFoundResponse(String message){
        super(message);
    }
}
