package com.bitacora.project.domain.exceptions;

public class InvalidTradeDataException extends RuntimeException{
    public InvalidTradeDataException(String message){
        super(message);
    }

}
