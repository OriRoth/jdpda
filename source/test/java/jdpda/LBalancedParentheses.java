package jdpda;

import static jdpda.LBalancedParentheses.Letter.lp;
import static jdpda.LBalancedParentheses.Letter.rp;
import static jdpda.LBalancedParentheses.StackSymbol.E;
import static jdpda.LBalancedParentheses.StackSymbol.X;
import static jdpda.LBalancedParentheses.State.q0;
import static jdpda.LBalancedParentheses.State.q1;
import static jdpda.generated.LBalancedParenthesesAPI.START;

import jdpda.DPDA;

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
			.δ(q0, lp, E, q1, E, X) //
			.δ(q1, lp, X, q1, X, X) //
			.δ(q1, rp, X, q1) //
			.δ(q1, null, E, q0, E) //
			.q0(q0) //
			.F(q0) //
			.γ0(E) //
			.go();

	public static void main(String[] args) {
		START().lp().lp().lp().rp().rp().rp().ACCEPT();
	}
}
