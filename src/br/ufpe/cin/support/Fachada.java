package br.ufpe.cin.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufpe.cin.controller.UsuarioBC;
import br.ufpe.cin.exception.UsuarioExisteException;
import br.ufpe.cin.model.Usuario;

@Component
public class Fachada {

	@Autowired
	private UsuarioBC usuarioBC;

	// Métodos Usuário
	public void salvarUsuario(Usuario usuario) throws UsuarioExisteException {
		usuarioBC.salvarUsuario(usuario);
	}

	public void excluirUsuario(Usuario usuario) {
		usuarioBC.excluirUsuario(usuario);
	}

	public List<Usuario> listarUsuario() {
		return usuarioBC.listarUsuario();
	}

}
