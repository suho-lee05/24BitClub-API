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

import java.util.Map;

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
    public Map<String, Object> login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            // DB에서 사용자 조회
            User u = userRepo.findByUsername(req.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + req.getUsername()));

            // 토큰 생성 (변수명 token 유지)
            String token = jwtTokenProvider.createToken(u.getUsername());

            // 응답 한번에 구성 (필드명: userId, username, password, token)
            Map<String, Object> res = new java.util.HashMap<>();
            res.put("userId", u.getId());
            res.put("username", u.getUsername());
            res.put("password", u.getPassword()); // 해시 비밀번호 내려감 (원문 아님)
            res.put("token", token);
            return res;
        } catch (Exception e) {
            e.printStackTrace(); // ✅ 콘솔에 원인 표시
            throw new IllegalArgumentException("Login failed: " + e.getMessage());
        }
    }

    /** 내 정보 */
    @GetMapping("/me")
    public String me(Authentication authentication) {
        return "Hello, " + authentication.getName();
    }
}
