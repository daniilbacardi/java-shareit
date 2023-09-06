package ru.practicun.shareit.exeptions;

public class WrongStateException extends RuntimeException {
    public WrongStateException(String message) {
        super(message);
    }
}
