package roth.ori.jdpda;

import org.junit.Test;

import roth.ori.jdpda.DPDA.δ;
import roth.ori.jdpda.LLispParentheses.Letter;
import roth.ori.jdpda.LLispParentheses.StackSymbol;
import roth.ori.jdpda.LLispParentheses.State;
import static roth.ori.jdpda.LLispParentheses.Letter.*;

import java.util.Arrays;

public class DPDATest {
	@Test
	public void lispParenthesisSamplesTest() {
		DPDA<State, Letter, StackSymbol> M = LLispParentheses.M;
		State q = State.q0;
		Word<StackSymbol> S = new Word<>(StackSymbol.E);
		for (Letter σ : Arrays.asList(lp, LP, rp, rp, rp, lp, RP, lp, rp)) {
			δ<State, Letter, StackSymbol> δ = M.δ(q, σ, S.top());
			assert δ != null : "Computation terminated upon consuming " + σ;
			q = δ.q$;
			S.pop().push(δ.α);
			for (;;) {
				δ = M.δ(q, S.top());
				if (δ == null)
					break;
				q = δ.q$;
				S.pop().push(δ.α);
			}
		}
	}
}
