package br.ufpe.cin.enums;

public enum StateMachineEnum {
    RUNNING(1), TIMER_INJECT_FAILURE(2), FAILED(3), TIMER_REPAIR(4);

    private int value;

    private StateMachineEnum(int valor) {
	this.value = valor;
    }

    public int getValue() {
	return this.value;
    }
}
