package br.ufpe.cin.view;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.ufpe.cin.exception.UsuarioExisteException;
import br.ufpe.cin.model.Usuario;
import br.ufpe.cin.support.Fachada;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "usuarioEditMB")
public class UsuarioEditMB {

	@Autowired
	private Fachada fachada;

	private Usuario usuario;

	@PostConstruct
	private void init() {
		usuario = new Usuario();
	}

	public void preAlterar(Usuario usuario) {
		setUsuario(usuario);
	}

	public String salvar() {
		try {
			fachada.salvarUsuario(usuario);
			return "success";
		} catch (UsuarioExisteException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		return null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
