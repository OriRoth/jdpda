package roth.ori.jdpda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import roth.ori.jdpda.DPDA.Edge;

/**
 * Encodes deterministic pushdown automaton ({@link DPDA}) as a Java class; A
 * method calls chain of the form {@code START().a().b()...z().ACCEPT()}
 * type-checks against this class if, and only if, the original automaton
 * accepts the input word {@code ab...z};
 * 
 * @author Ori Roth
 *
 * @param <Q> states enum
 * @param <Sigma> alphabet enum
 * @param <Gamma> stack symbols enum
 */
public class DPDA2JavaFluentAPIEncoder<Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> {
	/**
	 * Class name.
	 */
	public final String name;
	/**
	 * {@link DPDA} origin.
	 */
	public final DPDA<Q, Sigma, Gamma> dpda;
	/**
	 * Class encoding.
	 */
	public final String encoding;
	/**
	 * Encoded types.
	 */
	private final Map<TypeIdentifier<Q, Gamma>, String> types;
	/**
	 * Types encoded.
	 */
	private final Set<TypeIdentifier<Q, Gamma>> knownTypes;

	public DPDA2JavaFluentAPIEncoder(String name, DPDA<Q, Sigma, Gamma> dpda) {
		this.name = name;
		this.dpda = dpda;
		this.types = new LinkedHashMap<>();
		this.knownTypes = new LinkedHashSet<>();
		this.encoding = getJavaFluentAPI();
	}

	/**
	 * @return class encoding
	 */
	private String getJavaFluentAPI() {
		StringBuilder result = new StringBuilder();
		result.append("public class ").append(name).append("{");
		result.append("interface ").append(stuckName()).append("{void STUCK();}").append("interface ")
				.append(terminatedName()).append("{void TERMINATED();}").append("interface ").append(acceptName())
				.append("{void ACCEPT();}");
		result.append("public static ")
				.append(requestTypeName(dpda.initialState(), Collections.singletonList(dpda.initialStackSymbol())))
				.append("<");
		List<String> typeVariables = new ArrayList<>();
		for (Q q : dpda.states())
			typeVariables.add(dpda.isAccepting(q) ? acceptName() : terminatedName());
		result.append(String.join(",", typeVariables)).append("> START() {return null;}");
		for (String classEncoding : types.values())
			result.append(classEncoding);
		return result.append("}").toString();
	}

	/**
	 * Computes the type representing the state of the automaton after consuming an
	 * input letter.
	 * 
	 * @param state  current state
	 * @param letter current input letter
	 * @param push   current stack symbols to be pushed
	 * @return next state type
	 */
	public String getType(Q state, Sigma letter, List<Gamma> push) {
		if (push.isEmpty()) {
			assert letter == null;
			return jumpTypeVariableName(state);
		}
		Edge<Q, Sigma, Gamma> consolidatedTransition = dpda.getConsolidatedTransition(state, letter, push.get(0));
		List<Gamma> rest = push.subList(1, push.size());
		if (consolidatedTransition == null)
			return stuckName();
		if (consolidatedTransition.string.isEmpty())
			return getType(consolidatedTransition.destination, null, rest);
		StringBuilder result = new StringBuilder();
		result.append(requestTypeName(consolidatedTransition.destination, consolidatedTransition.string)).append("<");
		List<String> typeVariables = new ArrayList<>();
		for (Q q : dpda.states())
			typeVariables.add(getType(q, null, rest));
		return result.append(String.join(",", typeVariables)).append(">").toString();
	}

	/**
	 * Get type name given a state and stack symbols to push.
	 * If this type is not present, it is created.
	 * 
	 * @param state current state
	 * @param string current stack symbols to be pushed
	 * @return type name
	 */
	private String requestTypeName(Q state, List<Gamma> string) {
		String className = pushTypeName(state, string);
		TypeIdentifier<Q, Gamma> identifier = new TypeIdentifier<>(state, string);
		if (!knownTypes.add(identifier))
			return className;
		StringBuilder classEncoding = new StringBuilder();
		classEncoding.append("interface ").append(className).append("<");
		List<String> typeVariables = new ArrayList<>();
		for (Q q : dpda.states())
			typeVariables.add(jumpTypeVariableName(q));
		classEncoding.append(String.join(",", typeVariables)).append(">extends ")
				.append(dpda.isAccepting(state) ? acceptName() : terminatedName()).append("{");
		for (Sigma letter : dpda.alphabet())
			classEncoding.append(getType(state, letter, string)).append(" ").append(letter.name()).append("();");
		types.put(identifier, classEncoding.append("}").toString());
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
	 * @param state jump destination
	 * @return jump type variable name
	 */
	private String jumpTypeVariableName(Q state) {
		return "jump_" + state.name();
	}

	/**
	 * @param state current state
	 * @param string current stack symbols to be pushed
	 * @return type name
	 */
	private String pushTypeName(Q state, List<Gamma> string) {
		return "push_" + state.name() + "_" + string.stream().map(symbol -> symbol.name()).reduce("", String::concat);
	}

	/**
	 * Identifies a type by a state and a sequence of stack symbols to be pushed.
	 */
	private static class TypeIdentifier<Q extends Enum<Q>, Gamma extends Enum<Gamma>> {
		private final Q state;
		private final List<Gamma> string;

		public TypeIdentifier(Q state, List<Gamma> string) {
			this.state = state;
			this.string = new ArrayList<>(string);
		}

		@Override
		public int hashCode() {
			int result = 1;
			result = result * 31 + state.hashCode();
			result = result * 31 + string.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof TypeIdentifier))
				return false;
			TypeIdentifier<?, ?> other = (TypeIdentifier<?, ?>) obj;
			return state.equals(other.state) && string.equals(other.string);
		}
	}
}
