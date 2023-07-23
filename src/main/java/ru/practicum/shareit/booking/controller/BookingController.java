package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.model.BookingStates;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @Valid @RequestBody BookingDtoRequest bookingDtoShort) {
        log.info("BookingController: createBooking выполнено. User ID {}.", userId);
        return bookingService.createBooking(bookingDtoShort, userId);
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
                                                       @RequestParam(defaultValue = "ALL") BookingStates state) {
        log.info("BookingController: getAllBookingsByUser выполнено. User ID {}, stateText {}.", userId, state);
        return bookingService.getAllBookingsByUser(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                        @RequestParam(defaultValue = "ALL") BookingStates state) {
        log.info("BookingController: getAllBookingsByOwner выполнено. User ID {}, stateText {}.",
                ownerId, state);
        return bookingService.getAllBookingsByOwner(ownerId, state);
    }
}
