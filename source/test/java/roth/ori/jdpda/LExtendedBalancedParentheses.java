package roth.ori.jdpda;

import static roth.ori.jdpda.LExtendedBalancedParentheses.Letter.*;
import static roth.ori.jdpda.LExtendedBalancedParentheses.StackSymbol.*;
import static roth.ori.jdpda.LExtendedBalancedParentheses.State.*;

import org.junit.Test;

public class LExtendedBalancedParentheses {
	enum State {
		q0, q1, q2
	}

	enum Letter {
		lp, rp, RP
	}

	enum StackSymbol {
		$, X
	}

	DPDA<State, Letter, StackSymbol> dpda = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, lp, $, q1, $, X) //
			.delta(q1, lp, X, q1, X, X) //
			.delta(q1, rp, X, q1) //
			.delta(q1, null, $, q0, $) //
			.delta(q1, RP, X, q2) //
			.delta(q2, null, X, q2) //
			.delta(q2, null, $, q0, $) //
			.setInitialState(q0) //
			.setAccepting(q0) //
			.setInitialStackSymbol($) //
			.build();

	String clazz = new DPDA2JavaFluentAPIEncoder<>("LExtendedBalancedParentheses", dpda).encoding;

	@Test
	public void aStarB() {
		System.out.println(clazz);
	}
}
