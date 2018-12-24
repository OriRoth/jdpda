package roth.ori.jdpda;

import static roth.ori.jdpda.LBalancedParentheses.Letter.lp;
import static roth.ori.jdpda.LBalancedParentheses.Letter.rp;
import static roth.ori.jdpda.LBalancedParentheses.StackSymbol.E;
import static roth.ori.jdpda.LBalancedParentheses.StackSymbol.X;
import static roth.ori.jdpda.LBalancedParentheses.State.q0;
import static roth.ori.jdpda.LBalancedParentheses.State.q1;
import static roth.ori.jdpda.generated.LBalancedParenthesesAPI.START;

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

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, lp, E, q1, E, X) //
			.delta(q1, lp, X, q1, X, X) //
			.delta(q1, rp, X, q1) //
			.delta(q1, null, E, q0, E) //
			.setInitialState(q0) //
			.setAccepting(q0) //
			.setInitialStackSymbol(E) //
			.build();

	public static void main(String[] args) {
		START().lp().lp().lp().rp().rp().rp().ACCEPT();
	}
}
