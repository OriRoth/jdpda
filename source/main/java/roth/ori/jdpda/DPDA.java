package roth.ori.jdpda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DPDA<Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> {
	public final Class<Q> qClass;
	public final Class<Sigma> sigmaClass;
	public final Class<Gamma> gammaClass;
	public final Set<Edge<Q, Sigma, Gamma>> delta;
	public final Set<Q> acceptingStates;
	private final Q initialState;
	private final Gamma initialSymbol;

	public DPDA(Class<Q> qClass, Class<Sigma> sigmaClass, Class<Gamma> gammaClass, Set<Edge<Q, Sigma, Gamma>> delta,
			Set<Q> acceptingStates, Q initialState, Gamma initialSymbol) {
		this.qClass = qClass;
		this.sigmaClass = sigmaClass;
		this.gammaClass = gammaClass;
		this.delta = delta;
		this.acceptingStates = acceptingStates;
		this.initialState = initialState;
		this.initialSymbol = initialSymbol;
	}

	public Collection<Q> states() {
		return EnumSet.<Q>allOf(qClass);
	}

	public Collection<Sigma> letters() {
		return EnumSet.<Sigma>allOf(sigmaClass);
	}

	public Collection<Gamma> stackSymbols() {
		return EnumSet.<Gamma>allOf(gammaClass);
	}

	public Collection<Edge<Q, Sigma, Gamma>> delta() {
		return delta;
	}

	public boolean isAccepting(Q state) {
		return acceptingStates.contains(state);
	}

	public Q initialState() {
		return initialState;
	}

	public Gamma initialStackSymbol() {
		return initialSymbol;
	}

	public static class Builder<Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> {
		private final Class<Q> qClass;
		private final Class<Sigma> sigmaClass;
		private final Class<Gamma> gammaClass;
		private final Set<Edge<Q, Sigma, Gamma>> delta;
		private final Set<Q> acceptingStates;
		private Q initialState;
		private Gamma initialSymbol;

		public Builder(Class<Q> qClass, Class<Sigma> sigmaClass, Class<Gamma> gammaClass) {
			this.qClass = qClass;
			this.sigmaClass = sigmaClass;
			this.gammaClass = gammaClass;
			this.delta = new LinkedHashSet<>();
			this.acceptingStates = new LinkedHashSet<>();
		}

		public Builder<Q, Sigma, Gamma> delta(Q origin, Sigma letter, Gamma symbol, Q destination,
				@SuppressWarnings("unchecked") Gamma... string) {
			delta.add(new Edge<>(origin, letter, symbol, destination, Arrays.asList(string)));
			return this;
		}

		public Builder<Q, Sigma, Gamma> setAccepting(Q state) {
			acceptingStates.add(state);
			return this;
		}

		public Builder<Q, Sigma, Gamma> setInitialState(Q state) {
			initialState = state;
			return this;
		}

		public Builder<Q, Sigma, Gamma> setInitialStackSymbol(Gamma symbol) {
			initialSymbol = symbol;
			return this;
		}

		public DPDA<Q, Sigma, Gamma> build() {
			return new DPDA<>(qClass, sigmaClass, gammaClass, delta, acceptingStates, initialState, initialSymbol);
		}
	}

	public static class Edge<Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> {
		public final Q origin;
		public final Sigma letter;
		public final Gamma symbol;
		public final Q destination;
		public final List<Gamma> string;

		public Edge(Q origin, Sigma letter, Gamma symbol, Q destination, List<Gamma> string) {
			this.origin = origin;
			this.letter = letter;
			this.symbol = symbol;
			this.destination = destination;
			this.string = new ArrayList<>(string);
		}

		public boolean isEpsilonTransition() {
			return letter == null;
		}
	}
}
