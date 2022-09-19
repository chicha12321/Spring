package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Dao.Impl.StorageDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final StorageDaoImpl storageDaoImpl;

    @Autowired
    public UserServiceImpl(StorageDaoImpl storageDaoImpl) {
        this.storageDaoImpl = storageDaoImpl;
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        return storageDaoImpl.createUser(userDto);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return storageDaoImpl.updateUser(userDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        return storageDaoImpl.getUser(id);
    }

    @Override
    public void deleteUserById(Long id) {
        storageDaoImpl.deleteUser(id);
    }
}
