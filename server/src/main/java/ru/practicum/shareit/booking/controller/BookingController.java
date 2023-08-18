package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.model.BookingStates;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createNewBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @RequestBody BookingDtoRequest bookingDtoRequest) {
        log.info("BookingController: createNewBooking выполнено. User ID {}.", userId);
        return bookingService.createNewBooking(bookingDtoRequest, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto requestStatusDecision(@RequestHeader("X-Sharer-User-Id") Long userId,
                                            @PathVariable Long bookingId,
                                            @RequestParam Boolean approved) {
        log.info("BookingController: requestStatusDecision выполнено. User ID {}, booking ID {}.",
                userId, bookingId);
        return bookingService.requestStatusDecision(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @PathVariable Long bookingId) {
        log.info("BookingController: getBookingByUser выполнено. User ID {}, booking ID {}.", userId, bookingId);
        return bookingService.getBookingByUser(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingDto> getAllBookingsByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                       @RequestParam(defaultValue = "ALL") BookingStates state,
                                                       @RequestParam(defaultValue = "0") int from,
                                                       @RequestParam(defaultValue = "20") int size) {
        log.info("BookingController: getAllBookingsByUser выполнено. User ID {}, stateText {}.", userId, state);
        return bookingService.getAllBookingsByUser(userId, state, from, size);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                        @RequestParam(defaultValue = "ALL") BookingStates state,
                                                        @RequestParam(defaultValue = "0") int from,
                                                        @RequestParam(defaultValue = "20") int size) {
        log.info("BookingController: getAllBookingsByOwner выполнено. User ID {}, stateText {}.",
                ownerId, state);
        return bookingService.getAllBookingsByOwner(ownerId, state, from, size);
    }
}
