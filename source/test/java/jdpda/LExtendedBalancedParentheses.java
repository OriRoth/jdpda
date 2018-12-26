package jdpda;

import static jdpda.LExtendedBalancedParentheses.Letter.Ↄ;
import static jdpda.LExtendedBalancedParentheses.Letter.c;
import static jdpda.LExtendedBalancedParentheses.Letter.ↄ;
import static jdpda.LExtendedBalancedParentheses.StackSymbol.$;
import static jdpda.LExtendedBalancedParentheses.StackSymbol.X;
import static jdpda.LExtendedBalancedParentheses.State.q0;
import static jdpda.LExtendedBalancedParentheses.State.q1;
import static jdpda.LExtendedBalancedParentheses.State.q2;
import static jdpda.generated.LExtendedBalancedParenthesesAPI.START;

import jdpda.DPDA;

public class LExtendedBalancedParentheses {
	enum State {
		q0, q1, q2
	}

	enum Letter {
		c, ↄ, Ↄ
	}

	enum StackSymbol {
		$, X
	}

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.δ(q0, c, $, q1, $, X) //
			.δ(q1, c, X, q1, X, X) //
			.δ(q1, ↄ, X, q1) //
			.δ(q1, null, $, q0, $) //
			.δ(q1, Ↄ, X, q2) //
			.δ(q2, null, X, q2) //
			.δ(q2, null, $, q0, $) //
			.q0(q0) //
			.F(q0) //
			.γ0($) //
			.go();

	public static void main(String[] args) {
		START().c().ↄ().ACCEPT();
		START().c().ↄ().ↄ().STUCK();
		START().c().c().c().ↄ().ↄ().TERMINATED();
		START().c().c().c().ↄ().ↄ().ↄ().ACCEPT();
		START().c().c().c().ↄ().Ↄ().c().ↄ().ACCEPT();
		START().c().c().c().ↄ().Ↄ().c().TERMINATED();
		START().c().c().c().ↄ().Ↄ().c().Ↄ().ACCEPT();
	}
}
