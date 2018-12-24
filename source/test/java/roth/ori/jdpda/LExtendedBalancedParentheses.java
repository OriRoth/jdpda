package roth.ori.jdpda;

import static roth.ori.jdpda.LExtendedBalancedParentheses.Letter.RP;
import static roth.ori.jdpda.LExtendedBalancedParentheses.Letter.lp;
import static roth.ori.jdpda.LExtendedBalancedParentheses.Letter.rp;
import static roth.ori.jdpda.LExtendedBalancedParentheses.StackSymbol.$;
import static roth.ori.jdpda.LExtendedBalancedParentheses.StackSymbol.X;
import static roth.ori.jdpda.LExtendedBalancedParentheses.State.q0;
import static roth.ori.jdpda.LExtendedBalancedParentheses.State.q1;
import static roth.ori.jdpda.LExtendedBalancedParentheses.State.q2;
import static roth.ori.jdpda.generated.LExtendedBalancedParenthesesAPI.START;

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

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
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

	public static void main(String[] args) {
		START().lp().rp().ACCEPT();
		START().lp().rp().rp().STUCK();
		START().lp().lp().lp().rp().rp().TERMINATED();
		START().lp().lp().lp().rp().rp().rp().ACCEPT();
		START().lp().lp().lp().rp().RP().ACCEPT();
	}
}
