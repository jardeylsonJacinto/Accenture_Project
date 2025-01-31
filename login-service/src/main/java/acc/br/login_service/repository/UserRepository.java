package acc.br.login_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acc.br.login_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByLogin(String login);

}
