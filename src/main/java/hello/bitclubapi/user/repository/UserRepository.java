package hello.bitclubapi.user.repository;

import hello.bitclubapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * username으로 User 조회
     * -로그인, 중복 검사 등에 활용
     *
     * */
    Optional<User> findByUsername(String username);

    /**
     * username 중복 체크
     */
    boolean existsByUsername(String username);

    
}
