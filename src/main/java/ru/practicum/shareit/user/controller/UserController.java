package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> findAllUsers() {
        log.info("UserController: findAllUsers выполнено.");
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable long userId) {
        log.info("UserController: findUserById выполнено. User ID {}.", userId);
        return userService.findUserById(userId);
    }

    @PostMapping
    public UserDto createNewUser(@Valid @RequestBody UserDto userDto) {
        log.info("UserController: createNewUser выполнено.");
        return userService.createNewUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable long userId, @RequestBody UserDto userDto) {
        log.info("UserController: updateUser выполнено. User ID {}.", userId);
        return userService.updateUser(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable long userId) {
        log.info("UserController: deleteUserById выполнено. User ID {}.", userId);
        userService.deleteUserById(userId);
    }
}
