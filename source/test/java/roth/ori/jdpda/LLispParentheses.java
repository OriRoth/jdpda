package roth.ori.jdpda;

import static roth.ori.jdpda.LLispParentheses.Letter.LP;
import static roth.ori.jdpda.LLispParentheses.Letter.RP;
import static roth.ori.jdpda.LLispParentheses.Letter.lp;
import static roth.ori.jdpda.LLispParentheses.Letter.rp;
import static roth.ori.jdpda.LLispParentheses.StackSymbol.E;
import static roth.ori.jdpda.LLispParentheses.StackSymbol.X;
import static roth.ori.jdpda.LLispParentheses.State.q0;
import static roth.ori.jdpda.LLispParentheses.State.q1;
import static roth.ori.jdpda.LLispParentheses.State.q2;
import static roth.ori.jdpda.LLispParentheses.State.q3;
import static roth.ori.jdpda.generated.LLispParenthesesAPI.START;

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

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.δ(q0, lp, E, q1, E, X) //
			.δ(q1, lp, X, q1, X, X) //
			.δ(q1, rp, X, q1) //
			.δ(q1, null, E, q0, E) //
			.δ(q1, RP, X, q2) //
			.δ(q2, null, X, q2) //
			.δ(q2, null, E, q0, E) //
			.δ(q0, LP, E, q3, E, X) //
			.δ(q1, LP, X, q3, X) //
			.δ(q3, lp, X, q3, X) //
			.δ(q3, rp, X, q3, X) //
			.δ(q3, LP, X, q3, X) //
			.δ(q3, RP, X, q2) //
			.q0(q0) //
			.q$(q0) //
			.γ0(E) //
			.go();

	public static void main(String[] args) {
		START().lp().lp().rp().rp().ACCEPT(); // (())
		START().lp().lp().rp().rp().rp().STUCK(); // (()))
		START().lp().LP().rp().rp().rp().lp().RP().lp().rp().ACCEPT(); // ([)))(]()
		START().lp().LP().rp().rp().TERMINATED(); // ([))
		START().LP().lp().rp().rp().RP().LP().LP().RP().ACCEPT(); // [())][[]
	}
}
