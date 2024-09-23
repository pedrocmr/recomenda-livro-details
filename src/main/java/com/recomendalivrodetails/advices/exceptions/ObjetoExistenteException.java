package com.recomendalivrodetails.advices.exceptions;

import java.io.Serial;

public class ObjetoExistenteException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ObjetoExistenteException(String message) {
        super(message);
    }

    public ObjetoExistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
