package com.bitacora.project.infrastucture.config;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bitacora.project.domain.exceptions.InvalidTradeDataException;
import com.bitacora.project.domain.exceptions.TradeNotFoundException;
import com.bitacora.project.infrastucture.adapters.dto.ErrorResponse;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>handleValidationExceptions(
        MethodArgumentNotValidException e , WebRequest request){
            Map<String,String> errors=new HashMap<>();
            e.getBindingResult().getAllErrors().forEach(error->{
                String fieldName=((FieldError) error).getField();
                String errorMessage=error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            ErrorResponse errorResponse= ErrorResponse.builder()
                                        .timeStamp(Instant.now())
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .error("Validation Error")
                                        .message(errors.toString())
                                        .path(request.getDescription(false))
                                        .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(TradeNotFoundException.class)
        public ResponseEntity<ErrorResponse>handleTradeNotFoundException(
            TradeNotFoundException e, WebRequest request){
                ErrorResponse errorResponse= ErrorResponse.builder()
                                        .timeStamp(Instant.now())
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .error("Not Found")
                                        .message(e.getMessage())
                                        .path(request.getDescription(false))
                                        .build();
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

            }
        @ExceptionHandler(InvalidTradeDataException.class)
        public ResponseEntity<ErrorResponse>handleInvalidTradeDataEexception(
            InvalidTradeDataException e, WebRequest request){
                ErrorResponse errorResponse= ErrorResponse.builder()
                                        .timeStamp(Instant.now())
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .error("Bad Request")
                                        .message(e.getMessage())
                                        .path(request.getDescription(false))
                                        .build();
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

            }
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception e,WebRequest request){
                ErrorResponse errorResponse= ErrorResponse.builder()
                                        .timeStamp(Instant.now())
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .error( "Unexpected error try again")
                                        .message(e.getMessage())
                                        .path(request.getDescription(false))
                                        .build();
            return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);

            }

    

}
