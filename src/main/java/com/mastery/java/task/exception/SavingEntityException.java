package com.mastery.java.task.exception;

public class SavingEntityException extends RuntimeException {

    public SavingEntityException() {
        super();
    }

    public SavingEntityException(String message) {
        super(message);
    }

    public SavingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
