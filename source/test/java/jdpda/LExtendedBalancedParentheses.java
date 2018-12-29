package jdpda;

import static jdpda.LExtendedBalancedParentheses.Letter.Ↄ;
import static jdpda.LExtendedBalancedParentheses.Letter.c;
import static jdpda.LExtendedBalancedParentheses.Letter.ↄ;
import static jdpda.LExtendedBalancedParentheses.StackSymbol.γ0;
import static jdpda.LExtendedBalancedParentheses.StackSymbol.γ1;
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
		γ0, γ1
	}

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.δ(q0, c, γ0, q1, γ0, γ1) //
			.δ(q1, c, γ1, q1, γ1, γ1) //
			.δ(q1, ↄ, γ1, q1) //
			.δ(q1, null, γ0, q0, γ0) //
			.δ(q1, Ↄ, γ1, q2) //
			.δ(q2, null, γ1, q2) //
			.δ(q2, null, γ0, q0, γ0) //
			.q0(q0) //
			.F(q0) //
			.γ0(γ0) //
			.go();

	public static void main(String[] args) {
		START().c().ↄ().$();
		START().c().ↄ().ↄ().ø();
		START().c().c().c().ↄ().ↄ().¢();
		START().c().c().c().ↄ().ↄ().ↄ().$();
		START().c().c().c().ↄ().Ↄ().c().ↄ().$();
		START().c().c().c().ↄ().Ↄ().c().¢();
		START().c().c().c().ↄ().Ↄ().c().Ↄ().$();
	}
}
