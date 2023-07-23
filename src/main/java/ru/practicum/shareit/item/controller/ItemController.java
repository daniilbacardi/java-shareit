package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
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
    public Collection<ItemDtoResponse> findAllItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("ItemController: findAllItems выполнено. User ID {}.", userId);
        return itemService.findAllItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDtoResponse findItemById(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        log.info("ItemController: findItemById выполнено. User ID {}, item ID {}.", userId, itemId);
        return itemService.findItemById(userId, itemId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> findItemByParams(@RequestParam String text) {
        log.info("ItemController: findItemByParams выполнено. Text: {}.", text);
        return itemService.findItemsByText(text);
    }

    @PostMapping
    public ItemDto createNewItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @Valid @RequestBody ItemDto itemDto) {
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
                                            @Valid @RequestBody CommentDto commentDto) {
        log.info("ItemController: createComment выполнено. User ID {}, itemId {}.", userId, itemId);
        return itemService.createNewComment(commentDto, userId, itemId);
    }
}
