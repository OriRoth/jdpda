package roth.ori.jdpda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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

	public Collection<Sigma> alphabet() {
		return EnumSet.<Sigma>allOf(sigmaClass);
	}

	public Collection<Sigma> alphabetEpsilon() {
		Set<Sigma> result = new LinkedHashSet<>(alphabet());
		result.add(null);
		return result;
	}

	public Collection<Gamma> stackSymbols() {
		return EnumSet.<Gamma>allOf(gammaClass);
	}

	public Collection<Edge<Q, Sigma, Gamma>> delta() {
		return delta;
	}

	public Edge<Q, Sigma, Gamma> delta(Q origin, Sigma letter, Gamma symbol) {
		for (Edge<Q, Sigma, Gamma> edge : delta)
			if (edge.match(origin, letter, symbol))
				return edge;
		return null;
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

	public Edge<Q, Sigma, Gamma> getConsolidatedTransition(Q origin, Sigma letter, Gamma symbol) {
		Q currentState = origin;
		Sigma currentLetter = letter;
		Stack<Gamma> stack = new Stack<>();
		stack.push(symbol);
		for (;;) {
			if (stack.isEmpty())
				return new Edge<>(origin, letter, symbol, currentState, stack);
			Gamma currentSymbol = stack.peek();
			Edge<Q, Sigma, Gamma> transition = delta(currentState, currentLetter, currentSymbol);
			if (transition == null) {
				if (currentLetter != null) {
					return null;
				}
				Collections.reverse(stack);
				return new Edge<>(origin, letter, symbol, currentState, stack);
			}
			currentLetter = null;
			currentState = transition.destination;
			stack.pop();
			for (Gamma stackSymbol : transition.string)
				stack.push(stackSymbol);
		}
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

		public Builder<Q, Sigma, Gamma> setAccepting(@SuppressWarnings("unchecked") Q... states) {
			Collections.addAll(acceptingStates, states);
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
			assert initialState != null;
			assert initialSymbol != null;
			return new DPDA<>(qClass, sigmaClass, gammaClass, delta, acceptingStates, initialState, initialSymbol);
		}
	}

	public static class Edge<Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> {
		public final Q origin;
		public final Sigma letter;
		public final Gamma symbol;
		public final Q destination;
		public final List<Gamma> string;
		@SuppressWarnings("rawtypes")
		public static final Edge STUCK = new Edge<>(null, null, null, null, null);

		public Edge(Q origin, Sigma letter, Gamma symbol, Q destination, List<Gamma> string) {
			this.origin = origin;
			this.letter = letter;
			this.symbol = symbol;
			this.destination = destination;
			this.string = string == null ? null : new ArrayList<>(string);
		}

		public boolean isEpsilonTransition() {
			return letter == null;
		}

		public boolean match(Q state, Sigma letter, Gamma symbol) {
			return this != STUCK && origin.equals(state)
					&& (this.letter == null ? letter == null : this.letter.equals(letter))
					&& this.symbol.equals(symbol);
		}

		@Override
		public int hashCode() {
			int result = 1;
			if (this == STUCK)
				return result;
			result = result * 31 + origin.hashCode();
			result = result * 31 + (letter == null ? 1 : letter.hashCode());
			result = result * 31 + symbol.hashCode();
			result = result * 31 + destination.hashCode();
			result = result * 31 + string.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Edge))
				return false;
			Edge<?, ?, ?> other = (Edge<?, ?, ?>) obj;
			if (this == STUCK)
				return obj == STUCK;
			return origin.equals(other.origin)
					&& (letter == null && other.letter == null || letter.equals(other.letter))
					&& symbol.equals(other.symbol) && destination.equals(other.destination)
					&& string.equals(other.string);
		}

		@Override
		public String toString() {
			return "(" + origin + "," + (letter == null ? "E" : letter) + "," + symbol + "," + destination + ","
					+ string + ")";
		}
	}
}
