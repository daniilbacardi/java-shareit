package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemServiceImpl itemService;

    @GetMapping
    public Collection<ItemDtoResponse> getAllItemOfUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("ItemController: getAllItemOfUser implementation. User ID {}.", userId);
        return itemService.getAllItemOfUser(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDtoResponse getItemById(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        log.info("ItemController: getItemById implementation. User ID {}, item ID {}.", userId, itemId);
        return itemService.getItemById(userId, itemId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItem(@RequestParam String text) {
        log.info("ItemController: searchItem implementation. Text: {}.", text);
        return itemService.searchItem(text);
    }

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @Valid @RequestBody ItemDto itemDto) {
        log.info("ItemController: createItem implementation. User ID {}.", userId);
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long itemId,
                              @RequestBody ItemDto itemDto) {
        log.info("ItemController: updateItem implementation. User ID {}, itemId {}.", userId, itemId);
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResponse createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @PathVariable long itemId,
                                            @Valid @RequestBody CommentDto commentDto) {
        log.info("ItemController: createComment implementation. User ID {}, itemId {}.", userId, itemId);
        return itemService.createComment(commentDto, userId, itemId);
    }
}
