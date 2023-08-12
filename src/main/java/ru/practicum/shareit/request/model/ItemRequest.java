package ru.practicum.shareit.request.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "requests")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false, length = 1000)
    String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requester_id")
    User requester;
    @Column(name = "created", nullable = false)
    LocalDateTime created;
}
