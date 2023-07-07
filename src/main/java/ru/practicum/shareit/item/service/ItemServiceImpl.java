package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AlreadyExistsException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRep;
    private final UserService userService;

    @Override
    public List<Item> findAllItems(Long userId) {
        log.debug("ItemService: выполнено findAllItems.");
        return itemRep.findAllItems(userId);
    }

    @Override
    public Item findItemById(Long userId, Long itemId) {
        if (!itemRep.ifItemExists(itemId)) {
            throw new NotFoundException(Item.class.toString(), itemId);
        }
        Item item = itemRep.findItemById(itemId).orElseThrow(
                () -> new NotFoundException(Item.class.toString(), itemId)
        );
        log.debug("ItemService: выполнено findItemById - {}.", item);
        return item;
    }

    @Override
    public List<Item> findItemsByText(String text) {
            List<Item> searchedItems = itemRep.findItemsByText(text.toLowerCase());
            log.debug("ItemService: выполнено findItemsByText - {}.", searchedItems);
            return searchedItems;
    }

    @Override
    public Item createItem(Long userId, Item item, Long requestId) {
        if (item.getId() != null && itemRep.ifItemExists(item.getId())) {
            throw new AlreadyExistsException(Item.class.toString(), item.getId());
        }
        item.setOwner(userService.findUserById(userId));
        item = itemRep.createNewItem(userId, item);
        log.debug("ItemService: выполнено createItem - {}.", item);
        return item;
    }

    @Override
    public Item updateItem(Long userId, Long itemId, Item item, Long requestId) {
        if (!itemRep.ifItemExists(itemId)) {
            throw new NotFoundException(Item.class.toString(), itemId);
        }
        item.setOwner(userService.findUserById(userId));
        item = itemRep.updateItem(userId, itemId, item);
        log.debug("ItemService: выполнено updateItem - {}.", item);
        return item;
    }

    @Override
    public void deleteItemById(Long userId, Long itemId) {
        if (!itemRep.ifItemExists(itemId)) {
            throw new NotFoundException(Item.class.toString(), itemId);
        }
        itemRep.deleteItemById(itemId);
        log.debug("ItemService: выполнено deleteItemById - ID {}.", itemId);
    }
}
