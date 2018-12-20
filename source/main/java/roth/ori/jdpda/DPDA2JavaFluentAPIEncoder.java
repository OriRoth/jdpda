package roth.ori.jdpda;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import roth.ori.jdpda.DPDA.Edge;

public class DPDA2JavaFluentAPIEncoder {
	public static <Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> String getJavaFluentAPI(
			String name, DPDA<Q, Sigma, Gamma> dpda) {
		StringBuilder result = new StringBuilder();
		result.append("public class ").append(name).append("{");
		result.append("interface ").append(stuckName()).append("{void STUCK();}").append("interface ")
				.append(terminatedName()).append("{void TERMINATED();}").append("interface ").append(acceptName())
				.append("{void ACCEPT();}");
		result.append("public static push_").append(dpda.initialState().name()).append("_")
				.append(dpda.initialStackSymbol().name() + " START() {return null;}");
		return result.append("}").toString();
	}

	public static <Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> String getType(
			DPDA<Q, Sigma, Gamma> dpda, Q state, Sigma letter, Stack<Gamma> push) {
		if (push.isEmpty())
			return jumpTypeVariableName(state, letter);
		Edge<Q, Sigma, Gamma> consolidatedTransition = dpda.getConsolidatedTransition(state, letter, push.pop());
		if (consolidatedTransition == null)
			return stuckName();
		if (consolidatedTransition.string.isEmpty())
			return getType(dpda, consolidatedTransition.destination, null, push);
		StringBuilder result = new StringBuilder();
		result.append(pushTypeName(consolidatedTransition.destination, consolidatedTransition.string)).append("<");
		List<String> typeVariables = new ArrayList<>();
		for (Q q : dpda.states()) {
			for (Sigma s : dpda.alphabet())
				typeVariables.add(getType(dpda, q, s, push));
			typeVariables.add(getType(dpda, q, null, push));
		}
		return result.append(String.join(",", typeVariables)).append(">").toString();
	}

	public static String stuckName() {
		return "Stuck";
	}

	public static String terminatedName() {
		return "Terminated";
	}

	public static String acceptName() {
		return "Accept";
	}

	public static <Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> String jumpTypeVariableName(
			Q state, Sigma letter) {
		return "jump_" + state.name() + "_" + (letter == null ? "E" : letter.name());
	}

	public static <Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> String pushTypeName(Q state,
			List<Gamma> string) {
		return "push_" + state.name() + "_" + string.stream().map(symbol -> symbol.name()).reduce("", String::concat);
	}
}
