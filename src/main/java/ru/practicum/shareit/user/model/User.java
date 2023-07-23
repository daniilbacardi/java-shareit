package ru.practicum.shareit.user.model;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false, length = 512)
    String name;
    @Column(nullable = false, unique = true, length = 255)
    String email;
}
