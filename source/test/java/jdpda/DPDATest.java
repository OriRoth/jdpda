package jdpda;

import org.junit.Test;

import jdpda.DPDA;
import jdpda.Word;
import jdpda.DPDA.δ;
import jdpda.LExtendedBalancedParentheses.Letter;
import jdpda.LExtendedBalancedParentheses.StackSymbol;
import jdpda.LExtendedBalancedParentheses.State;

import static jdpda.LExtendedBalancedParentheses.Letter.*;

import java.util.Arrays;

public class DPDATest {
	@Test
	public void lispParenthesisSamplesTest() {
		DPDA<State, Letter, StackSymbol> M = LExtendedBalancedParentheses.M;
		State q = State.q0;
		Word<StackSymbol> S = new Word<>(StackSymbol.$);
		for (Letter σ : Arrays.asList(c, Ↄ, c, c, c, c, Ↄ, c, ↄ)) {
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
