package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    @EqualsAndHashCode.Include
    long id;
    String name;
    String description;
    Boolean available;
    Long requestId;
}
