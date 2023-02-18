package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.UserCreateRequest;
import com.group.libraryapp.dto.user.UserResponse;
import com.group.libraryapp.dto.user.UserUpdateRequest;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserServiceV2 userService;

    public UserController(UserServiceV2 userService) {
        this.userService = userService;
    }

//    @Autowired
//    public void setUserService(UserService userService){
//        this.userService=userService;
//    }


    // 유저등록처리 API
    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request){
        userService.saveUser(request);
    }

    @GetMapping("/user") // GET /user
    public List<UserResponse> getUsers(){
        return userService.getUser();
    }

    @PutMapping("/user") // PUT
    public void updateUser(@RequestBody UserUpdateRequest request){
        userService.updateUser(request);
    }

    @DeleteMapping("/user") // DELETE
    public void deleteUser(@RequestParam String name){
        userService.deleteUser(name);
    }

}
