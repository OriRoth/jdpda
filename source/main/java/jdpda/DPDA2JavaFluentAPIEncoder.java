package jdpda;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jdpda.DPDA.δ;

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
	public final DPDA<Q, Σ, Γ> dpda;
	/**
	 * Class encoding.
	 */
	public final String encoding;
	/**
	 * Encoded types.
	 */
	private final Map<String, String> types = new LinkedHashMap<>();

	public DPDA2JavaFluentAPIEncoder(final String name, final DPDA<Q, Σ, Γ> dpda) {
		this.name = name;
		this.dpda = dpda;
		this.encoding = encoding();
	}

	/**
	 * @return class encoding
	 */
	private String encoding() {
		return String.format("public class %s {\n%s%s\n%s\n}", //
				name, //
				endInteraces(), //
				startMethod(), //
				String.join("\n", types.values())//
		);
	}

	private String endInteraces() {
		return String.format("\t%s\n\t%s\n\t%s\n", //
				makeInterface(STUCK), //
				makeInterface(TERMINATED), //
				makeInterface(ACCEPT) //
		);
	}

	private static String makeInterface(final String name) {
		return String.format("public interface %s { void %s(); }", name, name.toUpperCase());
	}

	private String startMethod() {
		return "\t" + String.format("public static %s<%s> START() { return null; }\n", //
				requestTypeName(dpda.q0, new Word<>(dpda.γ0)), //
				dpda.Q().map(q -> dpda.isAccepting(q) ? ACCEPT : STUCK).collect(Collectors.joining(", "))//
		);
	}

	/**
	 * Computes the type representing the state of the automaton after consuming an
	 * input letter.
	 * 
	 * @param q current state
	 * @param σ current input letter
	 * @param α current stack symbols to be pushed
	 * @return next state type
	 */
	public String consolidatedTransitionType(final Q q, final Σ σ, final Word<Γ> α) {
		if (α.isEmpty()) {
			assert σ == null;
			return q + "";
		}
		final δ<Q, Σ, Γ> δ = dpda.consolidate(q, σ, α.top());
		if (δ == null) // assert σ != null;
			return STUCK;
		final Word<Γ> rest = new Word<>(α).pop();
		if (δ.α.isEmpty())
			return consolidatedTransitionType(δ.q$, null, rest);
		return String.format("%s<%s>", //
				requestTypeName(δ.q$, δ.α), dpda.Q().map(q$ -> consolidatedTransitionType(q$, null, rest)).collect(Collectors.joining(", "))//
		);
	}

	/**
	 * Get type name given a state and stack symbols to push. If this type is not
	 * present, it is created.
	 * 
	 * @param q current state
	 * @param α current stack symbols to be pushed
	 * @return type name
	 */
	private String requestTypeName(final Q q, final Word<Γ> α) {
		final String $ = pushTypeName(q, α);
		if (types.containsKey($))
			return $;
		types.put($, null); // Pending computation.
		types.put($, encodeType(q, α, $));
		return $;
	}

	private String encodeType(final Q q, final Word<Γ> α, final String name) {
		return String.format("\tpublic interface %s<%s> extends %s {\n%s\t}", //
				name, //
				dpda.Q().map(Enum::name).collect(Collectors.joining(", ")), //
				dpda.isAccepting(q) ? ACCEPT : TERMINATED, //
				dpda.Σ().map(σ -> String.format("\t\t%s %s();\n", consolidatedTransitionType(q, σ, α), σ)).reduce("", String::concat)//
		);
	}

	/**
	 * @param q current state
	 * @param α current stack symbols to be pushed
	 * @return type name
	 */
	private String pushTypeName(final Q q, final List<Γ> α) {
		return q + "_" + α.stream().map(Enum::name).collect(Collectors.joining("_"));
	}
}
