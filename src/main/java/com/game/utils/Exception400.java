package com.game.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Exception400 extends RuntimeException{

    public Exception400(){}

    public Exception400(String message){
        super(message);
    }
}
