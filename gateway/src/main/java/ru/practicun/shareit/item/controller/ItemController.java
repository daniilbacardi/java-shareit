package ru.practicun.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicun.shareit.item.client.ItemClient;
import ru.practicun.shareit.item.dto.CommentDto;
import ru.practicun.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import static ru.practicun.shareit.common.Constants.USER_ID_HEADER;

@Validated
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getAllItemsOfUser(@RequestHeader(USER_ID_HEADER) long userId,
                                                    @RequestParam(defaultValue = "0") @Min(0) int from,
                                                    @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("ItemController: getAllItemsOfUser выполнено. User ID {}, From {}, Size {}", userId, from, size);
        return itemClient.getAllItemOfUser(userId, from, size);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader(USER_ID_HEADER) long userId, @PathVariable long itemId) {
        System.out.println(userId + itemId);
        log.info("ItemController: findItemById выполнено. User ID {}, item ID {}.", userId, itemId);
        return itemClient.getItemById(userId, itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchAnItem(@RequestParam String text,
                                               @RequestParam(defaultValue = "0") @Min(0) int from,
                                               @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("ItemController: searchAnItem выполнено. Text: {}.", text);
        return itemClient.searchItem(text, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> createNewItem(@RequestHeader(USER_ID_HEADER) long userId,
                                                @Valid @RequestBody ItemDto itemDto) {
        log.info("ItemController: createNewItem выполнено. User ID {}.", userId);
        return itemClient.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(USER_ID_HEADER) long userId,
                                             @PathVariable long itemId,
                                             @RequestBody ItemDto itemDto) {
        log.info("ItemController: updateItem выполнено. User ID {}, itemId {}.", userId, itemId);
        return itemClient.updateItem(itemDto, userId, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(USER_ID_HEADER) long userId,
                                                @PathVariable long itemId,
                                                @Valid @RequestBody CommentDto commentDto) {
        log.info("ItemController: createComment выполнено. User ID {}, itemId {}.", userId, itemId);
        return itemClient.createComment(commentDto, userId, itemId);
    }
}
