package com.bitacora.project.infrastucture.adapters.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;


@Data
@Builder

public class ErrorResponse {
    
    private Instant timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;
    

}
