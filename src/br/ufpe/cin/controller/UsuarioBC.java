package br.ufpe.cin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.ufpe.cin.exception.UsuarioExisteException;
import br.ufpe.cin.model.Usuario;
import br.ufpe.cin.model.repository.IUsuarioDAO;

@Controller
public class UsuarioBC {

	@Autowired
	private IUsuarioDAO dao;

	public void salvarUsuario(Usuario usuario) throws UsuarioExisteException {
		Usuario a = dao.findBynome(usuario.getNome());
		if (a != null && !a.equals(usuario)) {
			throw new UsuarioExisteException();
		} else {
			dao.save(usuario);
		}
	}

	public void excluirUsuario(Usuario usuario) {
		dao.delete(usuario);
	}

	public List<Usuario> listarUsuario() {
		return dao.findAll();
	}

}
