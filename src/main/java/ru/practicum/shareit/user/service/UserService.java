package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> findAllUsers();

    UserDto findUserById(long userId);

    UserDto createNewUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, long userId);

    void deleteUserById(long userId);
}
