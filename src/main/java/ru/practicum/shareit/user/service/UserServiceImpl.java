package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.AlreadyExistsException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRep;

    @Transactional(readOnly = true)
    @Override
    public Collection<UserDto> findAllUsers() {
        return userRep.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto findUserById(long userId) {
        User user = userRep.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = " + userId + " не существует"));
        return UserMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public UserDto createNewUser(UserDto userDto) {
        User user = userRep.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        User updateUser = userRep.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = " + userId + " не существует"));
        Optional.ofNullable(userDto.getName()).ifPresent(updateUser::setName);
        Optional.ofNullable(userDto.getEmail()).ifPresent(updateUser::setEmail);
        User emailUser = userRep.findUserByEmail(updateUser.getEmail());
        if (!Objects.equals(updateUser.getEmail(), emailUser.getEmail())) {
            throw new AlreadyExistsException("Пользователь с такой почтой уже существует");
        }
        userRep.save(updateUser);
        return UserMapper.toUserDto(updateUser);
    }

    @Transactional
    @Override
    public void deleteUserById(long userId) {
        userRep.deleteById(userId);
    }
}
