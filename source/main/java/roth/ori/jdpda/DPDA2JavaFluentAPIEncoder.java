package roth.ori.jdpda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	/**
	 * Types encoded.
	 */
	private final Set<TypeIdentifier<Q, Γ>> knownTypes;

	public DPDA2JavaFluentAPIEncoder(String name, DPDA<Q, Σ, Γ> M) {
		this.name = name;
		this.M = M;
		this.typesEncodings = new LinkedHashMap<>();
		this.knownTypes = new LinkedHashSet<>();
		this.encoding = getJM();
	}

	/**
	 * @return class encoding
	 */
	private String getJM() {
		String JM = "public class " + name + "{";
		JM += "public interface " + stuckName() + "{void STUCK();}" + "public interface " + terminatedName()
				+ "{void TERMINATED();}" + "public interface " + acceptName() + "{void ACCEPT();}";
		JM += "public static " + requestTypeName(M.q0(), Collections.singletonList(M.Z())) + "<";
		List<String> typeVariables = new ArrayList<>();
		for (Q q : M.Q())
			typeVariables.add(M.isAccepting(q) ? acceptName() : terminatedName());
		JM += String.join(",", typeVariables) + "> START() {return null;}";
		for (String typeEncoding : typesEncodings.values())
			JM += typeEncoding;
		return JM + "}";
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
	public String getType(Q q, Σ σ, List<Γ> β) {
		if (β.isEmpty()) {
			assert σ == null;
			return jumpTypeVariableName(q);
		}
		δ<Q, Σ, Γ> consolidatedΔ = M.consolidate(q, σ, β.get(0));
		List<Γ> rest = β.subList(1, β.size());
		if (consolidatedΔ == null) {
			assert σ != null;
			return stuckName();
		}
		if (consolidatedΔ.α.isEmpty())
			return getType(consolidatedΔ.q_, null, rest);
		String result = requestTypeName(consolidatedΔ.q_, consolidatedΔ.α) + "<";
		List<String> typeVariables = new ArrayList<>();
		for (Q q_ : M.Q())
			typeVariables.add(getType(q_, null, rest));
		return result + String.join(",", typeVariables) + ">";
	}

	/**
	 * Get type name given a state and stack symbols to push. If this type is not
	 * present, it is created.
	 * 
	 * @param q current state
	 * @param α current stack symbols to be pushed
	 * @return type name
	 */
	private String requestTypeName(Q q, List<Γ> α) {
		String className = pushTypeName(q, α);
		TypeIdentifier<Q, Γ> identifier = new TypeIdentifier<>(q, α);
		if (!knownTypes.add(identifier))
			return className;
		String typeEncoding = "public interface " + className + "<";
		List<String> typeVariables = new ArrayList<>();
		for (Q q_ : M.Q())
			typeVariables.add(jumpTypeVariableName(q_));
		typeEncoding += String.join(",", typeVariables) + ">extends "
				+ (M.isAccepting(q) ? acceptName() : terminatedName()) + "{";
		for (Σ σ : M.Σ())
			typeEncoding += getType(q, σ, α) + " " + σ.name() + "();";
		typesEncodings.put(identifier, typeEncoding + "}");
		return className;
	}

	/**
	 * @return name of the "stuck" interface.
	 */
	private static String stuckName() {
		return "Stuck";
	}

	/**
	 * @return name of the "terminated" interface.
	 */
	private static String terminatedName() {
		return "Terminated";
	}

	/**
	 * @return name of the "accept" interface.
	 */
	private static String acceptName() {
		return "Accept";
	}

	/**
	 * @param q jump destination
	 * @return jump type variable name
	 */
	private String jumpTypeVariableName(Q q) {
		return "j_" + q.name();
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
		private final List<Γ> α;

		public TypeIdentifier(Q q, List<Γ> α) {
			this.q = q;
			this.α = new ArrayList<>(α);
		}

		@Override
		public int hashCode() {
			return 31 * (q.hashCode() + 31) + α.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			return o == this || (o instanceof TypeIdentifier && q.equals(((TypeIdentifier<?, ?>) o).q)
					&& α.equals(((TypeIdentifier<?, ?>) o).α));
		}
	}
}
