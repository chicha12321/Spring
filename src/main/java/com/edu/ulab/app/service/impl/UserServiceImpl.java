package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personToUserDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        userRepository.findById(userDto.getId()).orElseThrow(()->new NotFoundException(String.format("User with id: %d not found", userDto.getId())));
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person updatePerson = userRepository.save(user);
        log.info("Update user: {}", updatePerson);
        return userMapper.personToUserDto(updatePerson);
    }

    @Override
    @Transactional
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);
        Person person = userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User with id: %d not found", id)));
        log.info("Get user: {}", person);
        return userMapper.personToUserDto(person);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        log.info("Delete user by id: {}", id);
        userRepository.deleteById(id);
        log.info("Confirm delete user by id: {}", id);
    }
}
