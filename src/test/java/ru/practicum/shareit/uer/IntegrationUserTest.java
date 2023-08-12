package ru.practicum.shareit.uer;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static ru.practicum.shareit.TestData.owner;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class IntegrationUserTest {
    private final UserService userService;

    @Test
    public void getUserByIdTest() {
        UserDto savedUser = userService.createNewUser(owner);
        UserDto gottenUser = userService.getUserById(savedUser.getId());
        assertThat(gottenUser.getId(), notNullValue());
        assertThat(gottenUser.getName(), equalTo(owner.getName()));
        assertThat(gottenUser.getEmail(), equalTo(owner.getEmail()));
        userService.deleteUserById(savedUser.getId());
    }
}
