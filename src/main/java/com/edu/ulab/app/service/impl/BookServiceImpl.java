package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookToBookDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Book updateBook = bookRepository.save(book);
        log.info("Update book: {}", updateBook);
        return bookMapper.bookToBookDto(updateBook);
    }

    @Override
    @Transactional
    public BookDto getBookById(Long id) {
        log.info("Get book by id: {}", id);
        Book getBook = bookRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Book with id: %d not found", id)));
        log.info("Get book: {}", getBook);
        return bookMapper.bookToBookDto(getBook);
    }

    @Override
    @Transactional
    public List<BookDto> getBookByUserId(Long id) {
        log.info("Get books by user id: {}", id);
        return bookRepository.findAllByUserId(id).stream().map(bookMapper::bookToBookDto).toList();
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        log.info("Delete book by id: {}", id);
        bookRepository.deleteById(id);
        log.info("Confirm delete book by id: {}", id);
    }
}
