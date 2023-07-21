package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;
import java.util.Collection;

public interface ItemService {
    Collection<ItemDtoResponse> getAllItemOfUser(long userId);

    ItemDtoResponse getItemById(long userId, long itemId);

    Collection<ItemDto> searchItem(String text);

    ItemDto createItem(ItemDto itemDto, long userId);

    ItemDto updateItem(ItemDto itemDto, long userId, long itemId);

    CommentDtoResponse createComment(CommentDto commentDto, long userId, long itemId);
}
