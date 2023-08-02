package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.TestData;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingDtoResponseJsonTest {
    @Autowired
    private JacksonTester<BookingDtoResponse> json;

    @Test
    void bookingDtoResponseTest() throws IOException {
        BookingDtoResponse bookingDtoResponse = TestData.bookingDtoResponse;
        JsonContent<BookingDtoResponse> result = json.write(bookingDtoResponse);
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(0);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2024-12-12T10:00:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2024-12-20T10:00:00");
        assertThat(result).extractingJsonPathNumberValue("$.bookerId").isEqualTo(1);
    }
}
