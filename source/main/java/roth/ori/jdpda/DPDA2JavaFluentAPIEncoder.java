package roth.ori.jdpda;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import roth.ori.jdpda.DPDA.δ;

/**
 * Encodes deterministic pushdown automaton ({@link DPDA}) as a Java class; A
 * method calls chain of the form {@code START().a().b()...z().ACCEPT()}
 * type-checks against this class if, and only if, the original automaton
 * accepts the input word {@code ab...z}; A chain of a rejected input word
 * either do not type-check, or terminate with {@code STUCK()} call; Otherwise
 * the chain may terminate its computation by calling {@code TERMINATED()}.
 * 
 * @author Ori Roth
 *
 * @param <Q> states enum
 * @param <Σ> alphabet enum
 * @param <Γ> stack symbols enum
 */
public class DPDA2JavaFluentAPIEncoder<Q extends Enum<Q>, Σ extends Enum<Σ>, Γ extends Enum<Γ>> {
	private static final String ACCEPT = "Accept";
	private static final String TERMINATED = "Terminated";
	private static final String STUCK = "Stuck";
	/**
	 * Class name.
	 */
	public final String name;
	/**
	 * {@link DPDA} origin.
	 */
	public final DPDA<Q, Σ, Γ> M;
	/**
	 * Class encoding.
	 */
	public final String encoding;
	/**
	 * Encoded types.
	 */
	private final Map<TypeIdentifier<Q, Γ>, String> typesEncodings;

	public DPDA2JavaFluentAPIEncoder(String name, DPDA<Q, Σ, Γ> M) {
		this.name = name;
		this.M = M;
		this.typesEncodings = new LinkedHashMap<>();
		this.encoding = getJavaFluentAPI();
	}

	/**
	 * @return class encoding
	 */
	private String getJavaFluentAPI() {
		return String.format("public class %s{%s%s%s}", name, terminationTypes(), startMethod(),
				String.join("", typesEncodings.values()));
	}

	private String terminationTypes() {
		String string = "public interface " + STUCK + "{void STUCK();}";
		String string2 = "public interface " + ACCEPT + "{void ACCEPT();}";
		String string3 = "public interface " + TERMINATED
						+ "{void TERMINATED();}";
		return string + string3 + string2;
	}

	private String startMethod() {
		return String.format("public static %s<%s> START(){return null;}", //
				requestTypeName(M.q0, new Word<>(M.Z)),
				M.Q().stream().map(q -> M.isAccepting(q) ? ACCEPT : TERMINATED).collect(Collectors.joining(","))//
				);
	}

	/**
	 * Computes the type representing the state of the automaton after consuming an
	 * input letter.
	 * 
	 * @param q current state
	 * @param σ current input letter
	 * @param β current stack symbols to be pushed
	 * @return next state type
	 */
	public String getType(Q q, Σ σ, Word<Γ> β) {
		if (β.isEmpty()) {
			assert σ == null;
			return q.name();
		}
		δ<Q, Σ, Γ> consolidatedδ = M.consolidate(q, σ, β.top());
		if (consolidatedδ == null) {
			assert σ != null;
			return STUCK;
		}
		Word<Γ> rest = β.subList(1, β.size());
		if (consolidatedδ.α.isEmpty())
			return getType(consolidatedδ.q$, null, rest);
		return String.format("%s<%s>", requestTypeName(consolidatedδ.q$, consolidatedδ.α),
				M.Q().parallelStream().map(q$ -> getType(q$, null, rest)).collect(Collectors.joining(",")));
	}

	/**
	 * Get type name given a state and stack symbols to push. If this type is not
	 * present, it is created.
	 * 
	 * @param q current state
	 * @param α current stack symbols to be pushed
	 * @return type name
	 */
	private String requestTypeName(Q q, Word<Γ> α) {
		String className = pushTypeName(q, α);
		TypeIdentifier<Q, Γ> identifier = new TypeIdentifier<>(q, α);
		if (typesEncodings.containsKey(identifier))
			return className;
		typesEncodings.put(identifier, null); // Pending computation.
		typesEncodings.put(identifier, String.format("public interface %s<%s>extends %s{%s}", className,
				M.Q().stream().map(Enum::name).collect(Collectors.joining(",")), M.isAccepting(q) ? ACCEPT : TERMINATED,
				M.Σ().stream().map(σ -> String.format("%s %s();", getType(q, σ, α), σ.name()))
						.collect(Collectors.joining())));
		return className;
	}

	/**
	 * @param q current state
	 * @param α current stack symbols to be pushed
	 * @return type name
	 */
	private String pushTypeName(Q q, List<Γ> α) {
		return q.name() + "_" + α.stream().map(symbol -> symbol.name()).reduce("", String::concat);
	}

	/**
	 * Identifies a type by a state and a sequence of stack symbols to be pushed.
	 */
	private static class TypeIdentifier<Q extends Enum<Q>, Γ extends Enum<Γ>> {
		private final Q q;
		private final Word<Γ> α;

		public TypeIdentifier(Q q, Word<Γ> α) {
			this.q = q;
			this.α = new Word<>(α);
		}

		@Override
		public int hashCode() {
			int result = (1 * 31 + q.hashCode()) * 31 + α.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof TypeIdentifier))
				return false;
			TypeIdentifier<?, ?> other = (TypeIdentifier<?, ?>) o;
			return equals(other);
		}

		private boolean equals(TypeIdentifier<?, ?> other) {
			return q.equals(other.q) && α.equals(other.α);
		}
	}
}
