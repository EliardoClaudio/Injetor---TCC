package br.ufpe.cin.enums;

public enum Distribution {
	EXPONENTIAL(1), ERLANG(2), NORMAL(3), PARETO(4), GEOMETRIC(5), LOGNORMAL(6), POISSON(7), TRIANGULAR(8), WEIBULL(
			9), UNIFORM(10);

	@SuppressWarnings("unused")
	private int dist;

	private Distribution(int dist) {
		this.dist = dist;
	}
}
