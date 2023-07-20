package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoResponse {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    Item item;
    Booker booker;
    Status status;

    @Data
    public static class Item {
        final long id;
        final String name;
    }

    @Data
    public static class Booker {
        final long id;
        final String name;
    }
}
