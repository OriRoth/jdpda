package jdpda;

import org.junit.Test;

import jdpda.DPDA;
import jdpda.Word;
import jdpda.DPDA.δ;
import jdpda.LExtendedBalancedParentheses.Σ;
import jdpda.LExtendedBalancedParentheses.Γ;
import jdpda.LExtendedBalancedParentheses.Q;

import static jdpda.LExtendedBalancedParentheses.Σ.*;

import java.util.Arrays;

@SuppressWarnings("static-method")
public class DPDATest {

	@Test
	public void lispParenthesisSamplesTest() {
		DPDA<Q, Σ, Γ> M = LExtendedBalancedParentheses.M;
		Q q = Q.q0;
		Word<Γ> S = new Word<>(Γ.γ0);
		for (Σ σ : Arrays.asList(c, Ↄ, c, c, c, c, Ↄ, c, ↄ)) {
			δ<Q, Σ, Γ> δ = M.δ(q, σ, S.top());
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
