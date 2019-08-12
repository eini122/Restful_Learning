package App.test.app.test;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import App.test.app.test.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
//	UserEntity findUserByEmail(String email);
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
}
