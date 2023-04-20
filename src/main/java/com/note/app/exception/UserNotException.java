package com.note.app.exception;

public class UserNotException extends RuntimeException {
    public UserNotException(String massage,Long id) {
        super(massage+id);
    }
}
