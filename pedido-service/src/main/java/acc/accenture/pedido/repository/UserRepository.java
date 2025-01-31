package acc.accenture.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acc.accenture.pedido.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByLogin(String login);

}