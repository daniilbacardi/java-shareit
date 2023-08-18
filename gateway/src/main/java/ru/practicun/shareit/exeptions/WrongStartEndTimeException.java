package ru.practicun.shareit.exeptions;

public class WrongStartEndTimeException extends RuntimeException {
    public WrongStartEndTimeException(String message) {
        super(message);
    }
}