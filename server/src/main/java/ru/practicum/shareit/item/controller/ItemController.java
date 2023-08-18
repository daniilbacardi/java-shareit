package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

@Validated
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDtoResponse> getAllItemsOfUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                         @RequestParam(defaultValue = "0") int from,
                                                         @RequestParam(defaultValue = "20") int size) {
        log.info("ItemController: getAllItemsOfUser выполнено. User ID {}, From {}, Size {}", userId, from, size);
        return itemService.getAllItemsOfUser(userId, from, size);
    }

    @GetMapping("/{itemId}")
    public ItemDtoResponse findItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                        @PathVariable long itemId) {
        log.info("ItemController: findItemById выполнено. User ID {}, item ID {}.", userId, itemId);
        return itemService.getItemById(userId, itemId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchAnItem(@RequestParam String text,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "20") int size) {
        log.info("ItemController: searchAnItem выполнено. Text: {}.", text);
        return itemService.searchAnItem(text, from, size);
    }

    @PostMapping
    public ItemDto createNewItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @RequestBody ItemDto itemDto) {
        log.info("ItemController: createNewItem выполнено. User ID {}.", userId);
        return itemService.createNewItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long itemId,
                              @RequestBody ItemDto itemDto) {
        log.info("ItemController: updateItem выполнено. User ID {}, itemId {}.", userId, itemId);
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResponse createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @PathVariable long itemId,
                                            @RequestBody CommentDto commentDto) {
        log.info("ItemController: createComment выполнено. User ID {}, itemId {}.", userId, itemId);
        return itemService.createComment(commentDto, userId, itemId);
    }
}
