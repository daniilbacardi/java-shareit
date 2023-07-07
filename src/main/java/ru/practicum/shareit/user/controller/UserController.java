package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.validator.Create;
import ru.practicum.shareit.user.validator.Update;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAllUsers() {
        log.debug("UserController: выполнено findAllUsers.");
        return UserMapper.toUserDtoList(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Long userId) {
        log.debug("UserController: выполнено findUserById - {}.", userId);
        return UserMapper.toUserDto(userService.findUserById(userId));
    }

    @PostMapping
    public UserDto createNewUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.debug("UserController: выполнено createUser - {}.", userDto);
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userService.createNewUser(user));
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @Validated(Update.class) @RequestBody UserDto userDto) {
        log.debug("UserController: выполнено updateUser - {}.", userDto);
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userService.updateUser(userId, user));
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        log.debug("UserController: выполнено deleteUserById - {}.", userId);
        userService.deleteUserById(userId);
    }
}