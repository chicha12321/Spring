package com.edu.ulab.app.RowMapper;

import com.edu.ulab.app.dto.BookDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<BookDto> {

    @Override
    public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookDto bookDto = new BookDto();
        bookDto.setId(rs.getLong("id"));
        bookDto.setAuthor(rs.getString("author"));
        bookDto.setUserId(rs.getLong("user_id"));
        bookDto.setTitle(rs.getString("title"));
        bookDto.setPageCount(rs.getInt("page_count"));
        return bookDto;
    }
}
