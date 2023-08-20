package ru.practicun.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicun.shareit.booking.client.BookingClient;
import ru.practicun.shareit.booking.dto.BookingDtoRequest;
import ru.practicun.shareit.enums.BookingStates;
import ru.practicun.shareit.exeptions.WrongStartEndTimeException;
import ru.practicun.shareit.exeptions.WrongStateException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import static ru.practicun.shareit.common.Constants.USER_ID_HEADER;

@Validated
@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> createNewBooking(@RequestHeader(USER_ID_HEADER) Long userId,
                                                   @Valid @RequestBody BookingDtoRequest bookingDtoRequest) {
        if (bookingDtoRequest.getEnd().isBefore(bookingDtoRequest.getStart()) ||
                bookingDtoRequest.getEnd().isEqual(bookingDtoRequest.getStart())) {
            throw new WrongStartEndTimeException(
                    "Дата окончания не может быть раньше или равна дате начала бронирования." +
                            " Невозможно создать бронирование");
        }
        log.info("BookingController: createNewBooking выполнено. User ID {}.", userId);
        return bookingClient.bookItem(bookingDtoRequest, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> requestStatusDecision(@RequestHeader(USER_ID_HEADER) Long userId,
                                                        @PathVariable Long bookingId,
                                                        @RequestParam Boolean approved) {
        log.info("BookingController: requestStatusDecision выполнено. User ID {}, booking ID {}.",
                userId, bookingId);
        return bookingClient.requestStatusDecision(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingByUser(@RequestHeader(USER_ID_HEADER) Long userId,
                                                   @PathVariable Long bookingId) {
        log.info("BookingController: getBookingByUser выполнено. User ID {}, booking ID {}.", userId, bookingId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsByUser(@RequestHeader(USER_ID_HEADER) Long userId,
                                                       @RequestParam(name = "state", defaultValue = "ALL") String stateParam,
                                                       @RequestParam(defaultValue = "0") @Min(0) int from,
                                                       @RequestParam(defaultValue = "20") @Positive int size) {
        BookingStates state = BookingStates.from(stateParam)
                .orElseThrow(() -> new WrongStateException("Unknown state: " + stateParam));
        log.info("BookingController: getAllBookingsByUser выполнено. User ID {}, stateText {}.", userId, state);
        return bookingClient.getBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsByOwner(@RequestHeader(USER_ID_HEADER) Long ownerId,
                                                        @RequestParam(name = "state", defaultValue = "ALL") String stateParam,
                                                        @RequestParam(defaultValue = "0") @Min(0) int from,
                                                        @RequestParam(defaultValue = "20") @Positive int size) {
        BookingStates state = BookingStates.from(stateParam)
                .orElseThrow(() -> new WrongStateException("Unknown state: " + stateParam));
        log.info("BookingController: getAllBookingsByOwner выполнено. User ID {}, stateText {}.",
                ownerId, state);
        return bookingClient.getBookingsByOwner(ownerId, state, from, size);
    }
}
