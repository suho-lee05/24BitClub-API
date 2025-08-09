package hello.bitclubapi.user.service;

import hello.bitclubapi.user.entity.User;
import hello.bitclubapi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /** 전체 유저 조회 */
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    /** 단일 유저 조회 */
    public User getUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    /** 유저 생성 */
    @Transactional
    public User createUser(String username, String rawPassword) {
        if (userRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken: " + username);
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword)); // 🔐 해시 저장
        return userRepo.save(u);
    }

    /** 유저 정보 수정 */
    @Transactional
    public User updateUser(Long userId, String newUsername, String newPassword) {
        User u = getUser(userId);
        if (!u.getUsername().equals(newUsername) && userRepo.existsByUsername(newUsername)) {
            throw new IllegalArgumentException("Username already taken: " + newUsername);
        }
        u.setUsername(newUsername);
        u.setPassword(newPassword);
        return u;
    }

    /** 유저 삭제 */
    @Transactional
    public void deleteUser(Long userId) {
        User u = getUser(userId);
        userRepo.delete(u);
    }




}
