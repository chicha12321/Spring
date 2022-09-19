package com.edu.ulab.app.storage.Dao.Impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.dto.BookDtoMapper;
import com.edu.ulab.app.mapper.dto.UserDtoMapper;
import com.edu.ulab.app.storage.Dao.StorageDao;
import com.edu.ulab.app.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StorageDaoImpl implements StorageDao {

    private final Storage storage;

    private final UserDtoMapper userDtoMapper;

    private final BookDtoMapper bookDtoMapper;

    @Autowired
    public StorageDaoImpl(Storage storage, UserDtoMapper userDtoMapper, BookDtoMapper bookDtoMapper) {
        this.storage = storage;
        this.userDtoMapper = userDtoMapper;
        this.bookDtoMapper = bookDtoMapper;
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = storage.getUser(userId);
        if(user == null){
            throw  new NotFoundException(String.format("User with id:%d not found", userId));
        }
        return userDtoMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userDtoMapper.userDtoToUser(userDto);
        return userDtoMapper.userToUserDto(storage.updateUser(user));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userDtoMapper.userDtoToUser(userDto);
        return userDtoMapper.userToUserDto(storage.saveUser(user));
    }

    @Override
    public void deleteUser(Long userId) {
        storage.deleteUser(userId);
    }

    @Override
    public List<BookDto> getUserBooks(Long userId) {
        User user = storage.getUser(userId);
        if (user == null){
            throw new NotFoundException(String.format("User with id:%d not found", userId));
        }
        return user.getBookList().stream().filter(Objects::nonNull).map(storage::getBookById).map(bookDtoMapper::bookToBookDto).toList();
    }

    @Override
    public BookDto getBook(Long bookId) {
        Book book = storage.getBookById(bookId);
        if (book == null) {
            throw new NotFoundException(String.format("Book with id:%d not found", bookId));
        }
        return bookDtoMapper.bookToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if (bookDto == null){
            throw new NotFoundException("Book not found");
        }
        Book book = bookDtoMapper.bookDtoToBook(bookDto);
        if (book.getId() == null){
            return bookDtoMapper.bookToBookDto(storage.saveBook(book));
        }
        return bookDtoMapper.bookToBookDto(storage.updateBook(book));
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookDtoMapper.bookDtoToBook(bookDto);
        return bookDtoMapper.bookToBookDto(storage.saveBook(book));
    }

    @Override
    public void deleteBook(Long bookId) {
        storage.deleteBook(bookId);
    }
}
