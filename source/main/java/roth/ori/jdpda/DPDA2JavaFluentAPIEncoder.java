package roth.ori.jdpda;

import java.util.List;

import roth.ori.jdpda.DPDA.ConsolidatedEdge;

public class DPDA2JavaFluentAPIEncoder {
	public static <Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> String getJavaFluentAPI(
			String name, DPDA<Q, Sigma, Gamma> dpda) {
		StringBuilder result = new StringBuilder();
		result.append("public class ").append(name).append("{");
		result.append("interface Stuck{void STUCK();}").append("interface Terminated{void TERMINATED();}")
				.append("interface Accept{void ACCEPT();}");
		result.append("public static push_").append(dpda.initialState().name()).append("_")
				.append(dpda.initialStackSymbol().name() + " START() {return null;}");
		return result.append("}").toString();
	}

	public static <Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> String getType(
			DPDA<Q, Sigma, Gamma> dpda, Q state, Sigma letter, List<Gamma> push) {
		if (push.isEmpty())
			return jumpTypeVariableName(state, letter);
		ConsolidatedEdge<Q, Sigma, Gamma> consolidatedTransition = dpda.getConsolidatedTransition(state, letter, push);
		return null;
	}

	public static <Q extends Enum<Q>, Sigma extends Enum<Sigma>, Gamma extends Enum<Gamma>> String jumpTypeVariableName(
			Q state, Sigma letter) {
		return "jump_" + state.name() + "_" + (letter == null ? "E" : letter.name());
	}
}
