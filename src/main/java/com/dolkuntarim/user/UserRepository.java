package com.dolkuntarim.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByUserEmail(@Param("email") String email);
}
