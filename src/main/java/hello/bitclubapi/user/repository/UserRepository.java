package hello.bitclubapi.user.repository;

import hello.bitclubapi.fold.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {


}
