package hello.bitclubapi.fold.entity.repository;

import hello.bitclubapi.fold.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
