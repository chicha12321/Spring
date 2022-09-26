package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.RowMapper.UserRowMapper;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)", new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Saved user: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (getUserById(userDto.getId()) == null) {
            throw new NotFoundException(String.format("User with id: %d not found", userDto.getId()));
        }
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("UPDATE PERSON SET full_name = ?, title=?, age=? WHERE id=?", new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    ps.setLong(4, userDto.getId());
                    return ps;
                });
        log.info("Update user: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);
        UserDto userDto = jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM person WHERE id=?", new String[]{"id"});
            preparedStatement.setLong(1, id);
            return preparedStatement;
        }, new UserRowMapper()).stream().findFirst().orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found", id)));
        log.info("Get user: {}", userDto);
        return userDto;
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user by id: {}", id);
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("DELETE FROM person WHERE id = ?", new String[]{"id"});
                    ps.setLong(1, id);
                    return ps;
                });
        log.info("Confirm delete user by id: {}", id);
    }
}
