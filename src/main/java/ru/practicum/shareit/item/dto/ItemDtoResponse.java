package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoResponse {
    Long id;
    String name;
    String description;
    Boolean available;
    ItemOwner owner;
    ItemBooking lastBooking;
    ItemBooking nextBooking;
    List<CommentDto> comments;

    @Data
    public static class ItemOwner {
        final long id;
        final String name;
    }

    @Data
    public static class ItemBooking {
        final long id;
        final long bookerId;
    }
}
