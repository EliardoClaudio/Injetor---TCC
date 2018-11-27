package br.ufpe.cin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadeBase<Long> {

	private static final long serialVersionUID = 1L;

	@Column(name = "nome", length = 30, nullable = false)
	private String nome;

	@Column(name = "password", length = 10, nullable = false)
	private String senha;

	@Column(name = "email", length = 30, nullable = false)
	private String email;

	public Usuario() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
