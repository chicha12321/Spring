package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.RowMapper.BookRowMapper;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)", new String[]{"id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getUserId());
                    return ps;
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Saved book: {}", bookDto);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if (getBookById(bookDto.getId()) == null) {
            return createBook(bookDto);
        }
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("UPDATE BOOK SET title = ?, author=?, page_count=?, user_id=? WHERE id=?", new String[]{"id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getUserId());
                    ps.setLong(5, bookDto.getId());
                    return ps;
                });
        log.info("Update book: {}", bookDto);
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Get book by id: {}", id);
        BookDto bookDto = jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM book WHERE id=?", new String[]{"id"});
            preparedStatement.setLong(1, id);
            return preparedStatement;
        }, new BookRowMapper()).stream().findFirst().orElse(null);
        log.info("Get book: {}", bookDto);
        return bookDto;
    }

    @Override
    public List<BookDto> getBookByUserId(Long id) {
        log.info("Get books by user id: {}", id);
        return jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM book WHERE user_id=?", new String[]{"id"});
            preparedStatement.setLong(1, id);
            return preparedStatement;
        }, new BookRowMapper());
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Delete book by id: {}", id);
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("DELETE FROM BOOK WHERE id = ?", new String[]{"id"});
                    ps.setLong(1, id);
                    return ps;
                });
        log.info("Confirm delete book by id: {}", id);
    }
}
