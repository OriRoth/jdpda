package roth.ori.jdpda;

import static roth.ori.jdpda.LBalancedParentheses.Letter.*;
import static roth.ori.jdpda.LBalancedParentheses.StackSymbol.*;
import static roth.ori.jdpda.LBalancedParentheses.State.*;

import org.junit.Test;

public class LBalancedParentheses {
	enum State {
		q0, q1
	}

	enum Letter {
		lp, rp
	}

	enum StackSymbol {
		E, X
	}

	DPDA<State, Letter, StackSymbol> dpda = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, lp, E, q1, E, X) //
			.delta(q1, lp, X, q1, X, X) //
			.delta(q1, rp, X, q1) //
			.delta(q1, null, E, q0, E) //
			.setInitialState(q0) //
			.setAccepting(q0) //
			.setInitialStackSymbol(E) //
			.build();

	String clazz = new DPDA2JavaFluentAPIEncoder<>("LBalancedParentheses", dpda).encoding;

	@Test
	public void balancedParentheses() {
		System.out.println(clazz);
	}
}
