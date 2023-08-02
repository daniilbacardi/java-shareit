package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.practicum.shareit.booking.model.BookingStates.*;
import static ru.practicum.shareit.booking.model.BookingStatus.APPROVED;
import static ru.practicum.shareit.enums.Sorts.START;

class BookingServiceImplTest {
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final BookingService bookingService = new BookingServiceImpl(bookingRepository, itemRepository,
            userRepository);
    private static User booker;
    private static User owner;
    private static Item item;
    private static Booking booking;
    private static BookingDto bookingDto;

    @BeforeAll
    static void setUp() {
        booker = new User(2, "name", "user1@mail.ru");
        owner = new User(1, "owner", "user2@mail.ru");
        item = new Item(1, "item", "best", true, owner, null);
        booking = new Booking(1, LocalDateTime.now(), LocalDateTime.now().plusDays(1), item, booker,
                BookingStatus.WAITING);
        bookingDto = BookingMapper.toBookingDto(booking);

    }

    @Test
    void createNewBookingTest() {
        BookingDtoRequest bookingDtoS = new BookingDtoRequest(bookingDto.getStart(), bookingDto.getEnd(), item.getId());
        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(booking);
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
        BookingDto result = bookingService.createNewBooking(bookingDtoS, booker.getId());
        assertNotNull(result);
        assertEquals(bookingDto, result);
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(userRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).findById(anyLong());
    }

    @Test
    void requestStatusDecisionTest() {
        Booking bookingApr = new Booking(1, LocalDateTime.now(), LocalDateTime.now().plusDays(1), item, booker,
                BookingStatus.WAITING);
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(bookingApr));
        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(bookingApr);
        BookingDto result = bookingService.requestStatusDecision(1L, 1L, true);
        assertNotNull(result);
        assertEquals(APPROVED, result.getStatus());
        verify(bookingRepository, times(1)).findById(anyLong());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void getBookingByUserTest() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(booking));
        BookingDto result = bookingService.getBookingByUser(1L, 1L);
        assertNotNull(result);
        assertEquals(bookingDto, result);
        verify(bookingRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllBookingsByUserTest() {
        when(bookingRepository.findAllByBookerId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        Collection<BookingDto> result = bookingService.getAllBookingsByUser(1L, ALL, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByUser(1L, CURRENT, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByBookerIdAndEndBefore(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByUser(1L, PAST, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByBookerIdAndStartAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByUser(1L, FUTURE, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByBookerIdAndStatusEquals(anyLong(), any(BookingStatus.class),
                any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByUser(1L, WAITING, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);
        result = bookingService.getAllBookingsByUser(1L, REJECTED, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);
        verify(bookingRepository, times(1)).findAllByBookerId(anyLong(), any(Pageable.class));
        verify(bookingRepository, times(1)).findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(),
                any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));
        verify(bookingRepository, times(1)).findAllByBookerIdAndEndBefore(anyLong(),
                any(LocalDateTime.class), any(Pageable.class));
        verify(bookingRepository, times(1)).findAllByBookerIdAndStartAfter(anyLong(),
                any(LocalDateTime.class), any(Pageable.class));
        verify(bookingRepository, times(2)).findAllByBookerIdAndStatusEquals(anyLong(),
                any(BookingStatus.class), any(Pageable.class));
    }

    @Test
    void getAllBookingsByOwnerTest() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(owner));
        when(bookingRepository.findAllByItemOwner(any(User.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        Collection<BookingDto> result = bookingService.getAllBookingsByOwner(1L, ALL, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByItemOwnerAndStartBeforeAndEndAfter(any(User.class), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByOwner(1L, CURRENT, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByItemOwnerAndEndBefore(any(User.class), any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByOwner(1L, PAST, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByItemOwnerAndStartAfter(any(User.class), any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByOwner(1L, FUTURE, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);

        when(bookingRepository.findAllByItemOwnerAndStatusEquals(any(User.class), any(BookingStatus.class),
                any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(booking)));
        result = bookingService.getAllBookingsByOwner(1L, WAITING, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);
        result = bookingService.getAllBookingsByOwner(1L, REJECTED, 0, 20);
        assertNotNull(result);
        assertEquals(List.of(bookingDto), result);
        verify(userRepository, times(6)).findById(anyLong());
        verify(bookingRepository, times(1)).findAllByItemOwner(any(User.class),
                any(Pageable.class));
        verify(bookingRepository, times(1))
                .findAllByItemOwnerAndStartBeforeAndEndAfter(any(User.class), any(LocalDateTime.class),
                        any(LocalDateTime.class), any(Pageable.class));
        verify(bookingRepository, times(1)).findAllByItemOwnerAndEndBefore(any(User.class),
                any(LocalDateTime.class), any(Pageable.class));
        verify(bookingRepository, times(1)).findAllByItemOwnerAndStartAfter(any(User.class),
                any(LocalDateTime.class), any(Pageable.class));
        verify(bookingRepository, times(2)).findAllByItemOwnerAndStatusEquals(any(User.class),
                any(BookingStatus.class), any(Pageable.class));
    }

    @Test
    void getBookingDtoResponseTest() {
        when(bookingRepository.findAllByItem(any(Item.class), any(Sort.class)))
                .thenReturn(List.of(booking));
        Sort sort = Sort.by(START.getSort()).descending();
        Collection<BookingDtoResponse> result = bookingRepository.findAllByItem(item, sort).stream()
                .map(BookingMapper::toBookingDtoResponse)
                .collect(Collectors.toList());
        assertNotNull(result);
        assertEquals(List.of(BookingMapper.toBookingDtoResponse(booking)), result);
        verify(bookingRepository, times(1)).findAllByItem(any(Item.class), any(Sort.class));
    }
}
