package com.recomendalivrodetails.advices;

import com.recomendalivrodetails.advices.exceptions.ObjectNotFoundException;
import com.recomendalivrodetails.advices.exceptions.ObjetoExistenteException;
import com.recomendalivrodetails.advices.exceptions.StandardException;
import jakarta.servlet.ServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<StandardException> objectNotFoundException(ObjectNotFoundException e, ServletRequest request) {
        StandardException error = new StandardException(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.NOT_FOUND.value()
                , e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ObjetoExistenteException.class)
    ResponseEntity<StandardException> objetoJaExistenteException(ObjetoExistenteException e, ServletRequest request) {
        StandardException error = new StandardException(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.CONFLICT.value()
                , e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
