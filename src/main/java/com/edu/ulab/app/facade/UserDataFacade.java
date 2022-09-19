package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.mapper.update.BookUpdateMapper;
import com.edu.ulab.app.mapper.update.UserUpdateMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import com.edu.ulab.app.web.response.update.UserBookRequestUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    private final BookUpdateMapper bookUpdateMapper;

    private final UserUpdateMapper userUpdateMapper;


    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper,
                          BookUpdateMapper bookUpdateMapper,
                          UserUpdateMapper userUpdateMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.bookUpdateMapper = bookUpdateMapper;
        this.userUpdateMapper = userUpdateMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequestUpdate userBookRequest) {
        UserDto userDto =  userService.updateUser(userUpdateMapper.userRequestUpdateToUserDto(userBookRequest.getUserRequest()));
        log.info("Update user: {}", userDto);
        List<Long> listBooksId = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookUpdateMapper::bookRequestUpdateToBookDto)
                .peek(n -> n.setUserId(userDto.getId()))
                .map(bookService::updateBook)
                .peek(n -> log.info(n.toString()))
                .map(BookDto::getId)
                .toList();
        log.info("Collected updates book ids: {}", listBooksId);
        return UserBookResponse.builder().userId(userDto.getId()).booksIdList(listBooksId).build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        UserDto userDto = userService.getUserById(userId);
        log.info("User: {}", userDto);
        List<Long> listBooksId = bookService.getBooksByUserId(userId).stream().map(BookDto::getId).toList();
        log.info("Collected book ids: {}", listBooksId);
        return UserBookResponse.builder().userId(userDto.getId()).booksIdList(listBooksId).build();
    }

    public void deleteUserWithBooks(Long userId) {
        bookService.getBooksByUserId(userId).stream().map(BookDto::getId).forEach(bookService::deleteBookById);
        userService.deleteUserById(userId);
    }
}
