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

/**
 * Deterministic pushdown automaton (DPDA) supporting acceptance by final state.
 * 
 * @author Ori Roth
 *
 * @param <Q> states enum
 * @param <Sigma> alphabet enum
 * @param <Gamma> stack symbols enum
 */
public class DPDA<Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> {
	/**
	 * States enum class.
	 */
	public final Class<Q> qClass;
	/**
	 * Alphabet enum class. Epsilon is represented by {@code null}.
	 */
	public final Class<Sigma> sigmaClass;
	/**
	 * Stack symbols enum class.
	 */
	public final Class<Gamma> gammaClass;
	/**
	 * Transition function.
	 */
	public final Set<Edge<Q, Sigma, Gamma>> delta;
	/**
	 * Accepting states.
	 */
	public final Set<Q> acceptingStates;
	/**
	 * Initial state.
	 */
	private final Q initialState;
	/**
	 * Initial stack symbol.
	 */
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

	/**
	 * @return all automaton states.
	 */
	public Collection<Q> states() {
		return EnumSet.<Q>allOf(qClass);
	}

	/**
	 * @return automaton alphabet.
	 */
	public Collection<Sigma> alphabet() {
		return EnumSet.<Sigma>allOf(sigmaClass);
	}

	/**
	 * @return automaton alphabet, including epsilon.
	 */
	public Collection<Sigma> alphabetEpsilon() {
		Set<Sigma> result = new LinkedHashSet<>(alphabet());
		result.add(null);
		return result;
	}

	/**
	 * @return all stack symbols.
	 */
	public Collection<Gamma> stackSymbols() {
		return EnumSet.<Gamma>allOf(gammaClass);
	}

	/**
	 * @return the automaton's transition function.
	 */
	public Collection<Edge<Q, Sigma, Gamma>> delta() {
		return delta;
	}

	/**
	 * @param origin current state
	 * @param letter current input letter
	 * @param symbol current stack symbol
	 * @return matching transition
	 */
	public Edge<Q, Sigma, Gamma> delta(Q origin, Sigma letter, Gamma symbol) {
		for (Edge<Q, Sigma, Gamma> edge : delta)
			if (edge.match(origin, letter, symbol))
				return edge;
		return null;
	}

	/**
	 * @param state a state
	 * @return whether this is an accepting state
	 */
	public boolean isAccepting(Q state) {
		return acceptingStates.contains(state);
	}

	/**
	 * @return the automaton's initial state.
	 */
	public Q initialState() {
		return initialState;
	}

	/**
	 * @return the automaton's initial stack symbol.
	 */
	public Gamma initialStackSymbol() {
		return initialSymbol;
	}

	/**
	 * Returns matching consolidated transition, i.e., the result of the multiple
	 * transitions initiated by the received configuration.
	 * 
	 * @param origin current state
	 * @param letter current input letter
	 * @param symbol current stack symbol
	 * @return matching consolidated transition
	 */
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

	/**
	 * {@link DPDA} builder. Does not check the correctness of the automaton, i.e.,
	 * it assumes it is deterministic and cannot loop infinitely.
	 */
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

	/**
	 * An automaton edge. A set of edges is a transition function.
	 */
	public static class Edge<Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> {
		/**
		 * Current state.
		 */
		public final Q origin;
		/**
		 * Current input letter.
		 */
		public final Sigma letter;
		/**
		 * Current stack symbol.
		 */
		public final Gamma symbol;
		/**
		 * Next state.
		 */
		public final Q destination;
		/**
		 * Stack symbols to be pushed.
		 */
		public final List<Gamma> string;
		/**
		 * An edge representing termination of computation.
		 */
		@SuppressWarnings("rawtypes")
		public static final Edge STUCK = new Edge<>(null, null, null, null, null);

		public Edge(Q origin, Sigma letter, Gamma symbol, Q destination, List<Gamma> string) {
			this.origin = origin;
			this.letter = letter;
			this.symbol = symbol;
			this.destination = destination;
			this.string = string == null ? null : new ArrayList<>(string);
		}

		/**
		 * @return whether this edge describes an epsilon transition.
		 */
		public boolean isEpsilonTransition() {
			return letter == null;
		}

		/**
		 * @param state current state
		 * @param letter current input letter
		 * @param symbol current stack symbol
		 * @return whether this edge describes the next transition
		 */
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
			return "<" + origin + "," + (letter == null ? "E" : letter) + "," + symbol + "," + destination + ","
					+ string + ">";
		}
	}
}
