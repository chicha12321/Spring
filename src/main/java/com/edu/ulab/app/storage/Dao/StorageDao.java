package com.edu.ulab.app.storage.Dao;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;

import java.util.List;

public interface StorageDao {

    UserDto getUser(Long userId);

    UserDto updateUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long userId);

    List<BookDto> getUserBooks(Long userId);

    BookDto getBook(Long bookId);

    BookDto updateBook(BookDto bookDto);

    BookDto createBook(BookDto bookDto);

    void deleteBook(Long bookId);
}
