package ru.practicun.shareit.booking.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoRequest {
    @NotNull
    @Future(message = "Дата начала аренды должна быть в будущем")
    LocalDateTime start;
    @NotNull
    @Future(message = "Дата окончания аренды должна быть в будущем")
    LocalDateTime end;
    @PositiveOrZero(message = "itemId не может быть меньше ноля")
    @NotNull
    Long itemId;
}
