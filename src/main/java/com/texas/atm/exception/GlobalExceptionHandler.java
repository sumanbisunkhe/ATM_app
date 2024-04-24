package com.texas.atm.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String>errorMap= new HashMap<>();
        List<FieldError> fieldErrors=ex.getFieldErrors();
        for(FieldError fieldError:fieldErrors)
        {
            String field=fieldError.getField();
            String message=fieldError.getDefaultMessage();
            errorMap.put(field,message);
        }
        return new ResponseEntity<>(
                errorMap,status
        );
    }



    @ExceptionHandler({CustonerNotFoundException.class})
    public ResponseEntity handlesStudentNotFoundException(CustonerNotFoundException e){
        Map<String,String> body=Map.of(
                "message",e.getMessage()
        );
        return new ResponseEntity<>(
                body, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handlesRunTimeException(RuntimeException exception){
        Map<String,String> body=Map.of(
                "message",exception.getMessage()
        );
        return new ResponseEntity<>(
                body, HttpStatus.NOT_FOUND
        );
    }


}