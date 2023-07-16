package ru.practicum.shareit.request.repository;

import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;
import java.util.Optional;

public interface ItemRequestRepository {

    List<ItemRequest> findAllItemRequests();

    Optional<ItemRequest> findItemRequestById(Long itemRequestId);

    ItemRequest createNewItemRequest(ItemRequest itemRequest);

    ItemRequest updateItemRequest(ItemRequest itemRequest);

    void deleteItemRequestById(Long itemRequestId);

    boolean shouldItemRequestExists(Long itemRequestId);
}
