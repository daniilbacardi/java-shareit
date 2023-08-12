package ru.practicum.shareit.item.model;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @Column(nullable = false, length = 512)
    String name;
    @Column(nullable = false, length = 1000)
    String description;
    @Column(name = "is_available", nullable = false)
    Boolean available;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false)
    User owner;
    @ManyToOne
    @JoinColumn(name = "request_id")
    ItemRequest itemRequest;
}
