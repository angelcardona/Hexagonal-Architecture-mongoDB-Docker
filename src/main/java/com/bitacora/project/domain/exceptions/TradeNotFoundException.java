package com.bitacora.project.domain.exceptions;

public class TradeNotFoundException extends RuntimeException{
    public TradeNotFoundException(String id){
        super("Trade with ID" + id + " Not Found");
    }
    

}
