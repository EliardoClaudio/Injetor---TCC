package br.ufpe.cin.view;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "testeMB")
public class TesteMB {

	@PostConstruct
	private void init() {

	}

	public void imprimir() {
		System.out.println("aloteste");
	}

}
