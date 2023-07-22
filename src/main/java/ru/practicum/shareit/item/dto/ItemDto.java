package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    @PositiveOrZero
    @EqualsAndHashCode.Include
    long id;
    @NotBlank(message = "Отсутствует название вещи")
    String name;
    @NotBlank(message = "Отсутствует описание вещи")
    String description;
    @NotNull
    @AssertTrue
    Boolean available;
    Long request;
}
