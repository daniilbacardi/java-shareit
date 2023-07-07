package ru.practicum.shareit.exceptions;

public class OperationAccessException extends RuntimeException {
    public OperationAccessException(String message) {
        super(message);
    }

    public OperationAccessException(Long id) {
        super(String.format("Операция недоступна для пользователя с ID = ", id));
    }
}
