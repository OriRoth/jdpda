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
 * @param <Σ> alphabet enum
 * @param <Γ> stack symbols enum
 */
public class DPDA<Q extends Enum<Q>, Σ extends Enum<Σ>, Γ extends Enum<Γ>> {
	/**
	 * States enum class.
	 */
	final Class<Q> QClass;
	/**
	 * Alphabet enum class. ε is represented by {@code null}.
	 */
	final Class<Σ> ΣClass;
	/**
	 * Stack symbols enum class.
	 */
	final Class<Γ> ΓClass;
	/**
	 * Transition function.
	 */
	final Set<δ<Q, Σ, Γ>> δs;
	/**
	 * Accepting states.
	 */
	final Set<Q> F;
	/**
	 * Initial state.
	 */
	final Q q0;
	/**
	 * Initial stack symbol.
	 */
	private final Γ Z;

	public DPDA(Class<Q> QClass, Class<Σ> ΣClass, Class<Γ> ΓClass, Set<δ<Q, Σ, Γ>> δs, Set<Q> F, Q q0, Γ Z) {
		this.QClass = QClass;
		this.ΣClass = ΣClass;
		this.ΓClass = ΓClass;
		this.δs = δs;
		this.F = F;
		this.q0 = q0;
		this.Z = Z;
	}

	/**
	 * @return all automaton states.
	 */
	public Collection<Q> Q() {
		return EnumSet.<Q>allOf(QClass);
	}

	/**
	 * @return automaton alphabet.
	 */
	public Collection<Σ> Σ() {
		return EnumSet.<Σ>allOf(ΣClass);
	}

	/**
	 * @return all stack symbols.
	 */
	public Collection<Γ> Γ() {
		return EnumSet.<Γ>allOf(ΓClass);
	}

	/**
	 * @return the automaton's transition function.
	 */
	public Collection<δ<Q, Σ, Γ>> δ() {
		return δs;
	}

	/**
	 * @param q current state
	 * @param σ current input letter
	 * @param γ current stack symbol
	 * @return matching transition
	 */
	public δ<Q, Σ, Γ> δ(Q q, Σ σ, Γ γ) {
		for (δ<Q, Σ, Γ> Δ : δs)
			if (Δ.match(q, σ, γ))
				return Δ;
		return null;
	}

	/**
	 * @param q a state
	 * @return whether this is an accepting state
	 */
	public boolean isAccepting(Q q) {
		return F.contains(q);
	}

	/**
	 * @return the automaton's initial stack symbol.
	 */
	public Γ Z() {
		return Z;
	}

	/**
	 * Returns matching consolidated transition, i.e., the result of the multiple
	 * transitions initiated by the received configuration.
	 * 
	 * @param q current state
	 * @param σ current input letter
	 * @param γ current stack symbol
	 * @return matching consolidated transition
	 */
	public δ<Q, Σ, Γ> consolidate(Q q, Σ σ, Γ γ) {

		Σ currentσ = σ;
		Stack<Γ> S = new Stack<>();
		S.push(γ);
		for (Q q$ = q;;) {
			if (S.isEmpty())
				return new δ<>(q, σ, γ, q$, S);
			δ<Q, Σ, Γ> Δ = δ(q$, currentσ, S.peek());
			if (Δ == null) {
				if (currentσ != null)
					return null;
				Collections.reverse(S);
				return new δ<>(q, σ, γ, q$, S);
			}
			currentσ = null;
			q$ = Δ.q$;
			S.pop();
			for (Γ stackSymbol : Δ.α)
				S.push(stackSymbol);
		}
	}

	/**
	 * {@link DPDA} builder. Does not check the correctness of the automaton, i.e.,
	 * it assumes it is deterministic and cannot loop infinitely.
	 */
	public static class Builder<Q extends Enum<Q>, Σ extends Enum<Σ>, Γ extends Enum<Γ>> {
		private final Class<Q> QClass;
		private final Class<Σ> ΣClass;
		private final Class<Γ> ΓClass;
		private final Set<δ<Q, Σ, Γ>> δ = new LinkedHashSet<>();
		private final Set<Q> F = new LinkedHashSet<>();
		private Q q0;
		private Γ γ0;

		public Builder(Class<Q> QClass, Class<Σ> ΣClass, Class<Γ> ΓClass) {
			this.QClass = QClass;
			this.ΣClass = ΣClass;
			this.ΓClass = ΓClass;
		}

		public Builder<Q, Σ, Γ> δ(Q q, Σ σ, Γ γ, Q q_, @SuppressWarnings("unchecked") Γ... α) {
			δ.add(new δ<>(q, σ, γ, q_, Arrays.asList(α)));
			return this;
		}

		public Builder<Q, Σ, Γ> F(@SuppressWarnings("unchecked") Q... qs) {
			Collections.addAll(F, qs);
			return this;
		}

		public Builder<Q, Σ, Γ> q0(Q q0) {
			this.q0 = q0;
			return this;
		}

		public Builder<Q, Σ, Γ> γ0(Γ γ0) {
			this.γ0 = γ0;
			return this;
		}

		public DPDA<Q, Σ, Γ> go() {
			assert q0 != null;
			assert γ0 != null;
			return new DPDA<>(QClass, ΣClass, ΓClass, δ, F, q0, γ0);
		}
	}

	/**
	 * An automaton edge. A set of edges is a transition function.
	 */
	public static class δ<Q extends Enum<Q>, Σ extends Enum<Σ>, Γ extends Enum<Γ>> {
		/**
		 * Current state.
		 */
		final Q q;
		/**
		 * Current input letter.
		 */
		final Σ σ;
		/**
		 * Current stack symbol.
		 */
		final Γ γ;
		/**
		 * Next state.
		 */
		final Q q$;
		/**
		 * Stack symbols to be pushed.
		 */
		final List<Γ> α;
		/**
		 * An edge representing termination of computation.
		 */
		@SuppressWarnings("rawtypes")
		public static final δ STUCK = new δ<>(null, null, null, null, null);

		public δ(Q q, Σ σ, Γ γ, Q q$, List<Γ> α) {
			this.q = q;
			this.σ = σ;
			this.γ = γ;
			this.q$ = q$;
			this.α = α == null ? null : new ArrayList<>(α);
		}

		/**
		 * @return whether this edge describes an ε transition.
		 */
		public boolean isεTransition() {
			return σ == null;
		}

		/**
		 * @param currentq current state
		 * @param currentσ current input letter
		 * @param currentγ current stack symbol
		 * @return whether this edge describes the next transition
		 */
		public boolean match(Q currentq, Σ currentσ, Γ currentγ) {
			return this != STUCK && q.equals(currentq) && (this.σ == null ? currentσ == null : this.σ.equals(currentσ))
					&& this.γ.equals(currentγ);
		}

		@Override
		public int hashCode() {
			int result = 1;
			if (this == STUCK)
				return result;
			result = 31 * (γ.hashCode() + 31 * ((σ == null ? 1 : σ.hashCode()) + 31 * (31 * result + q.hashCode())))
					+ q$.hashCode();
			return result = 31 * result + α.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof δ))
				return false;
			δ<?, ?, ?> other = (δ<?, ?, ?>) o;
			return this == STUCK ? o == STUCK
					: q.equals(other.q) && (σ == null && other.σ == null || σ.equals(other.σ)) && γ.equals(other.γ)
							&& q$.equals(other.q$) && α.equals(other.α);
		}

		@Override
		public String toString() {
			return "<" + q + "," + (σ != null ? σ : "ε") + "," + γ + "," + q$ + "," + α + ">";
		}
	}
}
