package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.UserCreateRequest;
import com.group.libraryapp.dto.user.UserResponse;
import com.group.libraryapp.dto.user.UserUpdateRequest;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceV1 {

    private final UserJdbcRepository userRepository;

    public UserServiceV1(UserJdbcRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request){
        userRepository.saveUser(request.getName(), request.getAge());
    }

    public List<UserResponse> getUser(){
        return userRepository.getUser();
    }

    public void updateUser(UserUpdateRequest request){
       // 데이터 조회
        if (userRepository.isUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }

        // 데이터 수정
        userRepository.updateUserName(request.getName(), request.getId());
    }

    public void deleteUser(String name){
        // 데이터 조회
        if (userRepository.isUserNotExist(name)) {
            throw new IllegalArgumentException();
        }
        userRepository.deleteUser(name);
    }
}
