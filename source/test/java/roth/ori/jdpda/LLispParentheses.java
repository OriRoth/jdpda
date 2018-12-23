package roth.ori.jdpda;

import static roth.ori.jdpda.LLispParentheses.Letter.*;
import static roth.ori.jdpda.LLispParentheses.StackSymbol.*;
import static roth.ori.jdpda.LLispParentheses.State.*;

import org.junit.Test;

public class LLispParentheses {
	enum State {
		q0, q1, q2, q3
	}

	enum Letter {
		lp, rp, LP, RP
	}

	enum StackSymbol {
		E, X
	}

	DPDA<State, Letter, StackSymbol> dpda = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, lp, E, q1, E, X) //
			.delta(q1, lp, X, q1, X, X) //
			.delta(q1, rp, X, q1) //
			.delta(q1, null, E, q0, E) //
			.delta(q1, RP, X, q2) //
			.delta(q2, null, X, q2) //
			.delta(q2, null, E, q0, E) //
			.delta(q0, LP, E, q3, E, X) //
			.delta(q1, LP, X, q3, X) //
			.delta(q3, lp, X, q3, X) //
			.delta(q3, rp, X, q3, X) //
			.delta(q3, LP, X, q3, X) //
			.delta(q3, RP, X, q2) //
			.setInitialState(q0) //
			.setAccepting(q0) //
			.setInitialStackSymbol(E) //
			.build();

	String clazz = new DPDA2JavaFluentAPIEncoder<>("LLispParentheses", dpda).encoding;

	@Test
	public void lispParentheses() {
		System.out.println(clazz);
	}
}
