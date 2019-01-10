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
public class Encoder<Q extends Enum<Q>, Σ extends Enum<Σ>, Γ extends Enum<Γ>> {
	private static final String ACCEPT = "$";
	private static final String TERMINATED = "¢";
	private static final String REJECT = "ø";

	final String name;

	final DPDA<Q, Σ, Γ> dpda;

	final String encoding;

	private final Map<String, String> types = new LinkedHashMap<>();

	public Encoder(final String name, final DPDA<Q, Σ, Γ> dpda) {
		this.name = name;
		this.dpda = dpda;
		this.encoding = encoding();
	}

	/**
	 * Computes the type representing the state of the automaton after consuming an
	 * input letter.
	 *
	 * @param q current state
	 * @param α all known information about the top of the stack
	 * @param σ current input letter
	 * @return next state type
	 */
	public String next(final Q q, final Word<Γ> α, final Σ σ) {
		if (σ == null)
			return consolidate(q, α);
		if (α.isEmpty()) {
			assert σ == null;
			return τ(q);
		}
		final δ<Q, Σ, Γ> δ = dpda.δδ(q, σ, α.top());
		return δ == null ? REJECT : consolidateWithEpsilon(δ, new Word<>(α).pop());
	}

	public String consolidate(final Q q, final Word<Γ> α) {
		return (α.isEmpty()) ? τ(q) : //
				dpda.δ(q, α.top()) == null ? String.format("%s<%s>", encodedName(q, α),
						dpda.Q().map(q$ -> τ(q$)).collect(Collectors.joining(", "))) : //
						consolidateWithEpsilon(dpda.δδ(q, α.top()), new Word<>(α).pop());
	}

	private String consolidateWithEpsilon(final δ<Q, Σ, Γ> δ, final Word<Γ> α) {
		return δ.α.isEmpty() ? consolidate(δ.q$, α)
				: String.format("%s<%s>", encodedName(δ.q$, δ.α),
						dpda.Q().map(q$ -> consolidate(q$, α)).collect(Collectors.joining(", ")));
	}

	private static String endInteraces() {
		return String.format("\t%s\n\t%s\n\t%s\n", //
				makeInterface(REJECT), //
				makeInterface(TERMINATED), //
				makeInterface(ACCEPT) //
		);
	}

	private static String makeInterface(final String name) {
		return String.format("public interface %s { void %s(); }", name, name);
	}

	private String encodedBody(final Q q, final Word<Γ> α, final String name) {
		return String.format("\tpublic interface %s<%s> extends %s {\n%s\t}", //
				name, //
				dpda.Q().map(x -> τ(x)).collect(Collectors.joining(", ")), //
				dpda.isAccepting(q) ? ACCEPT : TERMINATED, //
				dpda.Σ().map(σ -> String.format("\t\t%s %s();\n", next(q, α, σ), σ)).reduce("", String::concat)//
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
	private String encodedName(final Q q, final Word<Γ> α) {
		final String $ = pushTypeName(q, α);
		if (types.containsKey($))
			return $;
		types.put($, null); // Pending computation.
		types.put($, encodedBody(q, α, $));
		return $;
	}

	private String encoding() {
		return String.format("public class %s {\n%s%s\n%s\n}", //
				name, //
				endInteraces(), //
				start(), //
				String.join("\n", types.values())//
		);
	}

	private String initialEncodingType() {
		return String.format("%s<%s>", //
				encodedName(dpda.q0, new Word<>(dpda.γ0)),
				dpda.Q().map(q -> dpda.isAccepting(q) ? ACCEPT : REJECT).collect(Collectors.joining(", ")) //
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

	private String start() {
		return startMethod() + startVariable();
	}

	private String startMethod() {
		return String.format("\tprivate static %s __() { return null; }\n", initialEncodingType());
	}

	private String startVariable() {
		return String.format("\tpublic static final %s __ = __();\n", initialEncodingType());
	}

	String τ(final Q q) {
		return "τ" + q;
	}
}
