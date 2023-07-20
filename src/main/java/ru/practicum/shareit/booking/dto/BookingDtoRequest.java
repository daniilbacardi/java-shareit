package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.validator.Create;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoRequest {
    Long id;
    @NotNull(groups = {Create.class})
    @FutureOrPresent(groups = {Create.class})
    LocalDateTime start;
    @NotNull(groups = {Create.class})
    @Future(groups = {Create.class})
    LocalDateTime end;
    Long itemId;
}
