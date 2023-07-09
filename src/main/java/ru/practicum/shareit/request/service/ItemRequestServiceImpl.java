package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AlreadyExistsException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRep;

    @Override
    public List<ItemRequest> findAllItemRequests() {
        log.debug("RequestService: выполнено findAllItemRequests.");
        return itemRequestRep.findAllItemRequests();
    }

    @Override
    public ItemRequest findItemRequestById(Long itemRequestId) {
        ItemRequest itemRequest = itemRequestRep.findItemRequestById(itemRequestId).orElseThrow(
                () -> new NotFoundException(ItemRequest.class.toString(), itemRequestId)
        );
        log.debug("RequestService: выполнено findItemRequestById - {}.", itemRequest);
        return itemRequest;
    }

    @Override
    public ItemRequest createNewItemRequest(ItemRequest itemRequest) {
        if (itemRequest.getId() != null && itemRequestRep.shouldItemRequestExists(itemRequest.getId())) {
            throw new AlreadyExistsException(ItemRequest.class.toString(), itemRequest.getId());
        }
        itemRequest = itemRequestRep.createNewItemRequest(itemRequest);
        log.debug("RequestService: выполнено createItemRequest - {}.", itemRequest);
        return itemRequest;
    }

    @Override
    public ItemRequest updateItemRequest(ItemRequest itemRequest) {
        if (!itemRequestRep.shouldItemRequestExists(itemRequest.getId())) {
            throw new NotFoundException(ItemRequest.class.toString(), itemRequest.getId());
        }
        log.debug("RequestService: выполнено updateItemRequest - {}.", itemRequest);
        return itemRequestRep.updateItemRequest(itemRequest);
    }

    @Override
    public void deleteItemRequestById(Long itemRequestId) {
        if (!itemRequestRep.shouldItemRequestExists(itemRequestId)) {
            throw new NotFoundException(ItemRequest.class.toString(), itemRequestId);
        }
        itemRequestRep.deleteItemRequestById(itemRequestId);
        log.debug("RequestService: выполнено deleteItemRequestById - ID {}.", itemRequestId);
    }
}
