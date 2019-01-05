package jdpda;

import static jdpda.LLispParentheses.Σ.C;
import static jdpda.LLispParentheses.Σ.Ↄ;
import static jdpda.LLispParentheses.Σ.c;
import static jdpda.LLispParentheses.Σ.ↄ;
import static jdpda.LLispParentheses.Γ.E;
import static jdpda.LLispParentheses.Γ.X;
import static jdpda.LLispParentheses.Q.q0;
import static jdpda.LLispParentheses.Q.q1;
import static jdpda.LLispParentheses.Q.q2;
import static jdpda.LLispParentheses.Q.q3;
import static jdpda.generated.LLispParenthesesAPI.__;

import jdpda.DPDA;

public class LLispParentheses {
	enum Q {
		q0, q1, q2, q3
	}

	enum Σ {
		c, ↄ, C, Ↄ
	}

	enum Γ {
		E, X
	}

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
			.δ(q0, c, E, q1, E, X) //
			.δ(q1, c, X, q1, X, X) //
			.δ(q1, ↄ, X, q1) //
			.δ(q1, null, E, q0, E) //
			.δ(q1, Ↄ, X, q2) //
			.δ(q2, null, X, q2) //
			.δ(q2, null, E, q0, E) //
			.δ(q0, C, E, q3, E, X) //
			.δ(q1, C, X, q3, X) //
			.δ(q3, c, X, q3, X) //
			.δ(q3, ↄ, X, q3, X) //
			.δ(q3, C, X, q3, X) //
			.δ(q3, Ↄ, X, q2) //
			.q0(q0) //
			.F(q0) //
			.γ0(E) //
			.go();

	public static void main(String[] args) {
		__.c().c().ↄ().ↄ().$(); // (())
		__.c().c().ↄ().ↄ().ↄ().ø(); // (()))
		__.c().C().ↄ().ↄ().ↄ().c().Ↄ().c().ↄ().$(); // ([)))(]()
		__.c().C().ↄ().ↄ().¢(); // ([))
		__.C().c().ↄ().ↄ().Ↄ().C().C().Ↄ().$(); // [())][[]
	}
}
