package br.ufpe.cin.enums;

public enum VMState {
	WORKING(1), WAIT_TIMER_WORKING(2), FAILED(3), WAIT_TIMER_FAILED(4);

	private int valor;

	private VMState(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return this.valor;
	}
}
