package ru.practicun.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicun.shareit.request.client.ItemRequestClient;
import ru.practicun.shareit.request.dto.ShortRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Validated
@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createNewRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @Valid @RequestBody ShortRequestDto shortRequestDto) {
        log.info("ItemRequestController: createNewRequest выполнено. User ID {}.", userId);
        return itemRequestClient.createRequest(userId, shortRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getListOfUserRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("ItemRequestController: getListOfUserRequests выполнено. User ID {}.", userId);
        return itemRequestClient.getListOfUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getListOfRequests(@RequestHeader("X-Sharer-User-Id") long userId,
                                                    @RequestParam(defaultValue = "0") @Min(0) int from,
                                                    @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("ItemRequestController: getListOfRequests выполнено. User ID {}, From {}, Size {}",
                userId, from, size);
        return itemRequestClient.getListOfRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long requestId) {
        log.info("ItemRequestController: getRequest выполнено. User ID {}, request Id {}.", userId, requestId);
        return itemRequestClient.getRequest(userId, requestId);
    }
}
