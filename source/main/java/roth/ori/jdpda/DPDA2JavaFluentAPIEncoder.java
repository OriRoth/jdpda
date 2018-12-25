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
	private final Map<String, String> types = new LinkedHashMap<>();

	public DPDA2JavaFluentAPIEncoder(String name, DPDA<Q, Σ, Γ> M) {
		this.name = name;
		this.M = M;
		this.encoding = getJavaFluentAPI();
	}

	/**
	 * @return class encoding
	 */
	private String getJavaFluentAPI() {
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

	private static String makeInterface(String name) {
		return String.format("public interface %s { void %s(); }", name, name.toUpperCase());
	}

	private String startMethod() {
		return "\t" + String.format("public static %s<%s> START() { return null; }\n", //
				requestTypeName(M.q0, new Word<>(M.γ0)), //
				M.Q().map(q -> acceptStatus(q)).collect(Collectors.joining(","))//
		);
	}

	private String acceptStatus(Q q) {
		return M.isAccepting(q) ? ACCEPT : STUCK;
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
	public String getType(Q q, Σ σ, Word<Γ> α) {
		if (α.isEmpty()) {
			assert σ == null;
			return q + "";
		}
		δ<Q, Σ, Γ> δ = M.consolidate(q, σ, α.top());
		if (δ == null) {
			assert σ != null;
			return STUCK;
		}
		Word<Γ> rest = α.subList(1, α.size());
		if (δ.α.isEmpty())
			return getType(δ.q$, null, rest);
		return String.format("%s<%s>", //
				requestTypeName(δ.q$, δ.α), M.Q().map(q$ -> getType(q$, null, rest)).collect(Collectors.joining(", "))//
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
	private String requestTypeName(Q q, Word<Γ> α) {
		String $ = pushTypeName(q, α);
		if (types.containsKey($))
			return $;
		types.put($, null); // Pending computation.
		types.put($, encodeType(q, α, $));
		return $;
	}

	private String encodeType(Q q, Word<Γ> α, String $) {
		return String.format("\tpublic interface %s<%s> extends %s {\n%s\t}", //
				$, //
				M.Q().map(Enum::name).collect(Collectors.joining(", ")), //
				M.isAccepting(q) ? ACCEPT : TERMINATED, //
				M.Σ().map(σ -> String.format("\t\t%s %s();\n", getType(q, σ, α), σ)).reduce("", String::concat)//
		);
	}

	/**
	 * @param q current state
	 * @param α current stack symbols to be pushed
	 * @return type name
	 */
	private String pushTypeName(Q q, List<Γ> α) {
		return q + "_" + α.stream().map(Enum::name).collect(Collectors.joining("_"));
	}
}
