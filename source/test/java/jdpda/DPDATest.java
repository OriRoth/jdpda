package jdpda;

import org.junit.Test;

import jdpda.DPDA;
import jdpda.Word;
import jdpda.DPDA.δ;
import jdpda.LLispParentheses.Letter;
import jdpda.LLispParentheses.StackSymbol;
import jdpda.LLispParentheses.Q;

import static jdpda.LLispParentheses.Letter.*;

import java.util.Arrays;

public class DPDATest {
	@Test
	public void lispParenthesisSamplesTest() {
		DPDA<Q, Letter, StackSymbol> M = LLispParentheses.M;
		Q q = Q.q0;
		Word<StackSymbol> S = new Word<>(StackSymbol.E);
		for (Letter σ : Arrays.asList(c, Ↄ, ↄ, ↄ, ↄ, c, Ↄ, c, ↄ)) {
			δ<Q, Letter, StackSymbol> δ = M.δ(q, σ, S.top());
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
