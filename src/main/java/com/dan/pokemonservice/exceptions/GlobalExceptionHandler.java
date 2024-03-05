package com.dan.pokemonservice.exceptions;

import com.dan.pokemonservice.models.dtos.error.ErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        String result = String.valueOf(e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .findFirst());
        ErrorDTO errorDTO = new ErrorDTO(result, BAD_REQUEST);

        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String result = e.getMessage();
        ErrorDTO errorDTO = new ErrorDTO(result, BAD_REQUEST);

        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> result = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> result.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
        return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
    }
}
