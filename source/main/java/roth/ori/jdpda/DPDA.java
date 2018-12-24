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
	public final Class<Q> QClass;
	/**
	 * Alphabet enum class. ε is represented by {@code null}.
	 */
	public final Class<Σ> ΣClass;
	/**
	 * Stack symbols enum class.
	 */
	public final Class<Γ> ΓClass;
	/**
	 * Transition function.
	 */
	public final Set<δ<Q, Σ, Γ>> δ;
	/**
	 * Accepting states.
	 */
	public final Set<Q> F;
	/**
	 * Initial state.
	 */
	private final Q q0;
	/**
	 * Initial stack symbol.
	 */
	private final Γ Z;

	public DPDA(Class<Q> QClass, Class<Σ> ΣClass, Class<Γ> ΓClass, Set<δ<Q, Σ, Γ>> δ, Set<Q> F, Q q0, Γ Z) {
		this.QClass = QClass;
		this.ΣClass = ΣClass;
		this.ΓClass = ΓClass;
		this.δ = δ;
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
		return δ;
	}

	/**
	 * @param q current state
	 * @param σ current input letter
	 * @param γ current stack symbol
	 * @return matching transition
	 */
	public δ<Q, Σ, Γ> δ(Q q, Σ σ, Γ γ) {
		for (δ<Q, Σ, Γ> Δ : δ)
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
	 * @return the automaton's initial state.
	 */
	public Q q0() {
		return q0;
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
		Q currentq = q;
		Σ currentσ = σ;
		Stack<Γ> S = new Stack<>();
		S.push(γ);
		for (;;) {
			if (S.isEmpty())
				return new δ<>(q, σ, γ, currentq, S);
			Γ currentγ = S.peek();
			δ<Q, Σ, Γ> Δ = δ(currentq, currentσ, currentγ);
			if (Δ == null) {
				if (currentσ != null) {
					return null;
				}
				Collections.reverse(S);
				return new δ<>(q, σ, γ, currentq, S);
			}
			currentσ = null;
			currentq = Δ.q_;
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
		private final Set<δ<Q, Σ, Γ>> δ;
		private final Set<Q> F;
		private Q q0;
		private Γ Z;

		public Builder(Class<Q> QClass, Class<Σ> ΣClass, Class<Γ> ΓClass) {
			this.QClass = QClass;
			this.ΣClass = ΣClass;
			this.ΓClass = ΓClass;
			this.δ = new LinkedHashSet<>();
			this.F = new LinkedHashSet<>();
		}

		public Builder<Q, Σ, Γ> delta(Q q, Σ σ, Γ γ, Q q_, @SuppressWarnings("unchecked") Γ... α) {
			δ.add(new δ<>(q, σ, γ, q_, Arrays.asList(α)));
			return this;
		}

		public Builder<Q, Σ, Γ> setAccepting(@SuppressWarnings("unchecked") Q... qs) {
			Collections.addAll(F, qs);
			return this;
		}

		public Builder<Q, Σ, Γ> setInitialState(Q q) {
			q0 = q;
			return this;
		}

		public Builder<Q, Σ, Γ> setInitialStackSymbol(Γ γ) {
			Z = γ;
			return this;
		}

		public DPDA<Q, Σ, Γ> build() {
			assert q0 != null;
			assert Z != null;
			return new DPDA<>(QClass, ΣClass, ΓClass, δ, F, q0, Z);
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
		public final Q q_;
		/**
		 * Stack symbols to be pushed.
		 */
		public final List<Γ> α;
		/**
		 * An edge representing termination of computation.
		 */
		@SuppressWarnings("rawtypes")
		public static final δ STUCK = new δ<>(null, null, null, null, null);

		public δ(Q q, Σ σ, Γ γ, Q q_, List<Γ> α) {
			this.q = q;
			this.σ = σ;
			this.γ = γ;
			this.q_ = q_;
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
			result = result * 31 + q.hashCode();
			result = result * 31 + (σ == null ? 1 : σ.hashCode());
			result = result * 31 + γ.hashCode();
			result = result * 31 + q_.hashCode();
			result = result * 31 + α.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof δ))
				return false;
			δ<?, ?, ?> other = (δ<?, ?, ?>) obj;
			if (this == STUCK)
				return obj == STUCK;
			return q.equals(other.q) && (σ == null && other.σ == null || σ.equals(other.σ)) && γ.equals(other.γ)
					&& q_.equals(other.q_) && α.equals(other.α);
		}

		@Override
		public String toString() {
			return "<" + q + "," + (σ == null ? "ε" : σ) + "," + γ + "," + q_ + "," + α + ">";
		}
	}
}
