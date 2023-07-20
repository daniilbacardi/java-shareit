package ru.practicum.shareit.user.model;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    Long id;
    @Column(name = "user_name", nullable = false)
    String name;
    @Column(name = "email", unique = true, length = 512, nullable = false)
    String email;
}
