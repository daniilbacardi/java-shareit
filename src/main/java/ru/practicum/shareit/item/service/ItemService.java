package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import java.util.Collection;

public interface ItemService {
    Collection<ItemDtoResponse> findAllItems(long userId);

    ItemDtoResponse findItemById(long userId, long itemId);

    Collection<ItemDto> findItemsByText(String text);

    ItemDto createNewItem(ItemDto itemDto, long userId);

    ItemDto updateItem(ItemDto itemDto, long userId, long itemId);

    CommentDtoResponse createNewComment(CommentDto commentDto, long userId, long itemId);
}
