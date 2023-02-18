package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.UserCreateRequest;
import com.group.libraryapp.dto.user.UserResponse;
import com.group.libraryapp.dto.user.UserUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 아래 함수가 시작될때 start transaction;을 해준다
    // 함수가 예외 없이 끝나면 commit
    // 혹시라도 문제가 있다면 rollback
    @Transactional
    public void saveUser(UserCreateRequest request){
       userRepository.save(new User(request.getName(), request.getAge()));
       //throw new IllegalArgumentException();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUser(){
        return  userRepository.findAll().stream()
           .map(UserResponse::new)
           .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request){
        // select * from user where id=?;
        // Optional<User>
        // orElseThrow : User가 없으면 예외생성
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalAccessError::new);

        user.updateName(request.getName());
        //userRepository.save(user); //변경감지 (Dirty Check)
    }

    @Transactional
    public void deleteUser(String name){
        // select * from user where name=?;
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalAccessError::new);

        userRepository.delete(user);
    }
}
