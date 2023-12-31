package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    default void isExist(Long userId) {
        if (!existsById(userId)) {
            throw new NotFoundException("Пользователя с id = " + userId + " не существует");
        }
    }
}
