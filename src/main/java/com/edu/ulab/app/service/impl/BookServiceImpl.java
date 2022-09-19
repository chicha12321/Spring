package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Dao.Impl.StorageDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final StorageDaoImpl storageDaoImpl;

    public BookServiceImpl(StorageDaoImpl storageDaoImpl) {
        this.storageDaoImpl = storageDaoImpl;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        return storageDaoImpl.createBook(bookDto);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return storageDaoImpl.updateBook(bookDto);
    }

    @Override
    public List<BookDto> getBooksByUserId(Long id) {
        return storageDaoImpl.getUserBooks(id);
    }

    @Override
    public BookDto getBookById(Long id) {
        return storageDaoImpl.getBook(id);
    }

    @Override
    public void deleteBookById(Long id) {
        storageDaoImpl.deleteBook(id);
    }
}
