package hello.bitclubapi.user.controller;

import hello.bitclubapi.security.JwtTokenProvider;
import hello.bitclubapi.user.dto.LoginRequest;
import hello.bitclubapi.user.entity.User;
import hello.bitclubapi.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserRepository userRepo,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /** 회원가입 */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody LoginRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            return "Username already exists";
        }
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // 해시 저장
        userRepo.save(u);
        return "Signup success";
    }

    /** 로그인 → JWT 발급 */
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            return jwtTokenProvider.createToken(req.getUsername());
        } catch (Exception e) {
            e.printStackTrace(); // ✅ 콘솔에 원인 표시
            return "Login failed: " + e.getMessage();
        }
    }

    /** 내 정보 */
    @GetMapping("/me")
    public String me(Authentication authentication) {
        return "Hello, " + authentication.getName();
    }
}
