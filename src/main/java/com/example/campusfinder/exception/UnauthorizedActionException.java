package com.example.campusfinder.exception;

// eg. editing someone else's review
public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
