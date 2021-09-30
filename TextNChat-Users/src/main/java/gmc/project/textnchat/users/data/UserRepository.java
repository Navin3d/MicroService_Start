package gmc.project.textnchat.users.data;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	UserEntity findByUserId(String userId);
	UserEntity findByUserName(String userName);
	UserEntity findByEmail(String email);

}
