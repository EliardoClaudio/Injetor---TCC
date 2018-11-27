package br.ufpe.cin.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpe.cin.model.Usuario;

public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {

	public Usuario findBynome(String nome);

}
