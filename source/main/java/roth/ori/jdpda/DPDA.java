package roth.ori.jdpda;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
	public final Class<Q> Q;
	/**
	 * Alphabet enum class. ε is represented by {@code null}.
	 */
	public final Class<Σ> Σ;
	/**
	 * Stack symbols enum class.
	 */
	public final Class<Γ> Γ;
	/**
	 * Transition function.
	 */
	public final Set<δ<Q, Σ, Γ>> δs;
	/**
	 * Accepting states.
	 */
	public final Set<Q> F;
	/**
	 * Initial state.
	 */
	public final Q q0;
	/**
	 * Initial stack symbol.
	 */
	public final Γ Z;

	public DPDA(Class<Q> QClass, Class<Σ> ΣClass, Class<Γ> ΓClass, Set<δ<Q, Σ, Γ>> δs, Set<Q> F, Q q0, Γ Z) {
		this.Q = QClass;
		this.Σ = ΣClass;
		this.Γ = ΓClass;
		this.δs = δs;
		this.F = F;
		this.q0 = q0;
		this.Z = Z;
	}

	/**
	 * @return all automaton states.
	 */
	public Collection<Q> Q() {
		return EnumSet.<Q>allOf(Q);
	}

	/**
	 * @return automaton alphabet.
	 */
	public Collection<Σ> Σ() {
		return EnumSet.<Σ>allOf(Σ);
	}

	/**
	 * @return all stack symbols.
	 */
	public Collection<Γ> Γ() {
		return EnumSet.<Γ>allOf(Γ);
	}

	/**
	 * @param q current state
	 * @param σ current input letter
	 * @param γ current stack symbol
	 * @return matching transition
	 */
	public δ<Q, Σ, Γ> δ(Q q, Σ σ, Γ γ) {
		for (δ<Q, Σ, Γ> δ : δs)
			if (δ.match(q, σ, γ))
				return δ;
		return null;
	}

	/**
	 * @param q current state
	 * @param γ current stack symbol
	 * @return matching epsilon transition
	 */
	public δ<Q, Σ, Γ> δ(Q q, Γ γ) {
		return δ(q, null, γ);
	}

	/**
	 * @param q a state
	 * @return whether this is an accepting state
	 */
	public boolean isAccepting(Q q) {
		return F.contains(q);
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
		δ<Q, Σ, Γ> δ;
		Q q$ = q;
		Word<Γ> s = new Word<>(γ);
		if (σ != null) {
			// Consuming transition.
			δ = δ(q, σ, s.pop());
			if (δ == null)
				return null;
			q$ = δ.q$;
			s.push(δ.α);
		}
		// ε transitions.
		for (;;) {
			if (s.isEmpty())
				return new δ<>(q, σ, γ, q$, s);
			δ = δ(q$, s.top());
			if (δ == null)
				return new δ<>(q, σ, γ, q$, s);
			s.pop();
			s.push(δ.α);
			q$ = δ.q$;
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
		private final Set<δ<Q, Σ, Γ>> δs;
		private final Set<Q> F;
		private Q q0;
		private Γ γ0;

		public Builder(Class<Q> QClass, Class<Σ> ΣClass, Class<Γ> ΓClass) {
			this.QClass = QClass;
			this.ΣClass = ΣClass;
			this.ΓClass = ΓClass;
			this.δs = new LinkedHashSet<>();
			this.F = new LinkedHashSet<>();
		}

		public Builder<Q, Σ, Γ> δ(Q q, Σ σ, Γ γ, Q q$, @SuppressWarnings("unchecked") Γ... α) {
			δs.add(new δ<>(q, σ, γ, q$, new Word<>(α)));
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
			return new DPDA<>(QClass, ΣClass, ΓClass, δs, F, q0, γ0);
		}
	}

	/**
	 * An automaton edge. A set of edges is a transition function.
	 */
	public static class δ<Q extends Enum<Q>, Σ extends Enum<Σ>, Γ extends Enum<Γ>> {
		/**
		 * Current state.
		 */
		public final Q q;
		/**
		 * Current input letter.
		 */
		public final Σ σ;
		/**
		 * Current stack symbol.
		 */
		public final Γ γ;
		/**
		 * Next state.
		 */
		public final Q q$;
		/**
		 * Stack symbols to be pushed.
		 */
		public final Word<Γ> α;
		/**
		 * An edge representing termination of computation.
		 */
		@SuppressWarnings("rawtypes")
		public static final δ STUCK = new δ<>(null, null, null, null, null);

		public δ(Q q, Σ σ, Γ γ, Q q$, Word<Γ> α) {
			this.q = q;
			this.σ = σ;
			this.γ = γ;
			this.q$ = q$;
			this.α = α == null ? null : new Word<>(α);
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
			int result = 31 * 1 + q.hashCode();
			result = 31 * result + (σ == null ? 1 : σ.hashCode());
			result = 31 * result + γ.hashCode();
			result = 31 * result + q$.hashCode();
			result = 31 * result + α.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof δ))
				return false;
			δ<?, ?, ?> other = (δ<?, ?, ?>) o;
			if (this == STUCK)
				return o == STUCK;
			return q.equals(other.q) && (σ == null && other.σ == null || σ.equals(other.σ)) && γ.equals(other.γ)
					&& q$.equals(other.q$) && α.equals(other.α);
		}

		@Override
		public String toString() {
			return "<" + q + "," + (σ != null ? σ : "ε") + "," + γ + "," + q$ + "," + α + ">";
		}
	}
}
