package com.example.reservation.exception_handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<BadRequestMessage> handle(ConstraintViolationException exception) {
//        String errorMessage = new ArrayList<>(exception.getConstraintViolations()).get(0).getMessage();
//        BadRequestMessage error = new BadRequestMessage(LocalDateTime.now(), errorMessage);
//        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
//    }




    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleCannotDelete(HandlerMethod handlerMethod) {
        String className = handlerMethod.getMethod().getName().replace("delete", "");
        BadRequestMessage badRequestMessage = new BadRequestMessage(LocalDateTime.now(), "Cannot delete " + className + ". Make sure "
                + className + " has not appointment scheduled.");
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }
}
