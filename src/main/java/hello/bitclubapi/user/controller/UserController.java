package hello.bitclubapi.user.controller;

import hello.bitclubapi.user.entity.User;
import hello.bitclubapi.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    /** 1) 전체 유저 조회 */
//    @GetMapping
//    public List<User> listAll() {
//        return userService.getAllUsers();
//    }

    /** 2) 특정 유저 조회 */
    @GetMapping("/{userId}")
    public User getOne(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    /** 3) 유저 생성 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return userService.createUser(username, password);
    }

    /** 4) 유저 수정 */
    @PutMapping("/{userId}")
    public User update(
            @PathVariable Long userId,
            @RequestParam String username,
            @RequestParam String password
    ) {
        return userService.updateUser(userId, username, password);
    }

    /** 5) 유저 삭제 */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}