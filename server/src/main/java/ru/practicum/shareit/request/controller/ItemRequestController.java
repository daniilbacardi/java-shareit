package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ShortRequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final RequestService requestService;

    @PostMapping
    public ItemRequestDto createNewRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @RequestBody ShortRequestDto shortRequestDto) {
        log.info("ItemRequestController: createNewRequest выполнено. User ID {}.", userId);
        return requestService.createNewRequest(userId, shortRequestDto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getListOfUserRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("ItemRequestController: getListOfUserRequests выполнено. User ID {}.", userId);
        return requestService.getListOfUserRequests(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getListOfRequests(@RequestHeader("X-Sharer-User-Id") long userId,
                                                        @RequestParam(defaultValue = "0") int from,
                                                        @RequestParam(defaultValue = "20") int size) {
        log.info("ItemRequestController: getListOfRequests выполнено. User ID {}, From {}, Size {}",
                userId, from, size);
        return requestService.getListOfRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                     @PathVariable long requestId) {
        log.info("ItemRequestController: getRequest выполнено. User ID {}, request Id {}.", userId, requestId);
        return requestService.getRequest(userId, requestId);
    }
}
