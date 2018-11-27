package br.ufpe.cin.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.ufpe.cin.model.Usuario;
import br.ufpe.cin.support.Fachada;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "usuarioListMB")
public class UsuarioListMB {

	@Inject
	private Fachada fachada;

	private List<Usuario> listaUsuario;

	@PostConstruct
	private void init() {
		setListaUsuario(listarUsuario());
	}

	private List<Usuario> listarUsuario() {
		return fachada.listarUsuario();
	}

	public void excluir(Usuario usuario) {
		fachada.excluirUsuario(usuario);
		setListaUsuario(listarUsuario());
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

}
