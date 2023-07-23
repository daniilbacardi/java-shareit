package ru.practicum.shareit.item.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @NotBlank
    private String text;
}
