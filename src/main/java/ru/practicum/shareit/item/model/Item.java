package ru.practicum.shareit.item.model;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "items", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    Long id;
    @Column(name = "item_name", nullable = false)
    String name;
    @Column(name = "description", length = 512, nullable = false)
    String description;
    @Column(name = "available", nullable = false)
    Boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;
}
