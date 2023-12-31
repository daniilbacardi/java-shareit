package ru.practicum.shareit.uer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exceptions.AlreadyExistsException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserServiceImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserServiceImpl(userRepository);
    private static User user;
    private static UserDto userDto;

    @BeforeAll
    static void setUp() {
        user = new User(1, "name", "user1@mail.com");
        userDto = UserMapper.toUserDto(user);
    }

    @Test
    void getAllUsersTest() {
        User secondUser = new User(2, "name2", "user2@mail.com");
        when(userRepository.findAll())
                .thenReturn(List.of(user, secondUser));
        Collection<User> result = userService.getAllUsers().stream()
                .map(UserMapper::toUser)
                .collect(Collectors.toList());
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(result, List.of(user, secondUser));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        UserDto result = userService.getUserById(1);
        assertNotNull(result);
        assertEquals(result, UserMapper.toUserDto(user));
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void createNewUserTest() {
        when(userRepository.save(any(User.class)))
                .thenReturn(UserMapper.toUser(userDto));
        UserDto result = userService.createNewUser(userDto);
        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserTest() {
        UserDto updateUser = new UserDto(1, "newName", "user1@mail.com");
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(UserMapper.toUser(userDto));
        when(userRepository.save(any(User.class)))
                .thenReturn(UserMapper.toUser(updateUser));
        UserDto result = userService.updateUser(updateUser, updateUser.getId());
        assertNotNull(result);
        assertEquals(updateUser, result);

        result.setEmail("new@mail.ru");
        when(userRepository.save(any(User.class)))
                .thenThrow(new AlreadyExistsException("Пользователь с такой почтой уже существует"));
        final AlreadyExistsException ex = Assertions.assertThrows(
                AlreadyExistsException.class,
                () -> userService.updateUser(result, result.getId()));
        Assertions.assertEquals("Пользователь с такой почтой уже существует", ex.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(2)).findById(anyLong());
        verify(userRepository, times(2)).findUserByEmail(anyString());
    }

    @Test
    void deleteUserByIdTest() {
        userService.deleteUserById(1);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
