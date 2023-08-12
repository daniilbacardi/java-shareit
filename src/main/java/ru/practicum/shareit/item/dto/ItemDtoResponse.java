package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoResponse {
    @EqualsAndHashCode.Include
    long id;
    String name;
    String description;
    Boolean available;
    BookingDtoResponse lastBooking;
    BookingDtoResponse nextBooking;
    Long requestId;
    List<CommentDtoResponse> comments;
}
