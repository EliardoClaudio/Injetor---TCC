package br.ufpe.cin.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpe.cin.model.User;

public interface IUserDAO extends JpaRepository<User, Long> {

	public User findByLoginAndPassword(String username, String password);

}
