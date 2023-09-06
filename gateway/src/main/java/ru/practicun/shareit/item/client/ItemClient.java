package ru.practicun.shareit.item.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicun.shareit.common.BaseClient;
import ru.practicun.shareit.item.dto.CommentDto;
import ru.practicun.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/items"))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getAllItemOfUser(long userId, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getItemById(long userId, long itemId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> searchItem(String text, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "text", text,
                "from", from,
                "size", size
        );
        return get("/search?text={text}&from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> createItem(ItemDto itemDto, long userId) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> updateItem(ItemDto itemDto, long userId, long itemId) {
        return patch("/" + itemId, userId, itemDto);
    }

    public ResponseEntity<Object> createComment(CommentDto commentDto, long userId, long itemId) {
        return post("/" + itemId + "/comment", userId, commentDto);
    }
}
