package jdpda;

import static jdpda.LBalancedParentheses.Σ.lp;
import static jdpda.LBalancedParentheses.Σ.rp;
import static jdpda.LBalancedParentheses.Γ.E;
import static jdpda.LBalancedParentheses.Γ.X;
import static jdpda.LBalancedParentheses.Q.q0;
import static jdpda.LBalancedParentheses.Q.q1;
import static jdpda.generated.LBalancedParenthesesAPI.*;

import jdpda.DPDA;

public class LBalancedParentheses {
	enum Q {
		q0, q1
	}

	enum Σ {
		lp, rp
	}

	enum Γ {
		E, X
	}

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
			.δ(q0, lp, E, q1, E, X) //
			.δ(q1, lp, X, q1, X, X) //
			.δ(q1, rp, X, q1) //
			.δ(q1, null, E, q0, E) //
			.q0(q0) //
			.F(q0) //
			.γ0(E) //
			.go();

	public static void main(String[] args) {
		__.lp().lp().lp().rp().rp().rp().$();
	}
}
