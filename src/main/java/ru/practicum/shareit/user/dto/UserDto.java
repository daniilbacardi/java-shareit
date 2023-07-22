package ru.practicum.shareit.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @PositiveOrZero(message = "ID не может быть меньше нуля")
    @EqualsAndHashCode.Include
    long id;
    @NotBlank(message = "Имя не должно быть пустым")
    String name;
    @NotBlank(message = "Почта не должна быть пустой")
    @Email(message = "Некорректная почта")
    String email;
}
