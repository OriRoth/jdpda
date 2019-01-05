package jdpda;

import static jdpda.LLispParentheses.Σ.C;
import static jdpda.LLispParentheses.Σ.Ↄ;
import static jdpda.LLispParentheses.Σ.c;
import static jdpda.LLispParentheses.Σ.ↄ;
import static jdpda.LLispParentheses.Γ.γ0;
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
		γ0, X
	}

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
			.δ(q0, c, γ0, q1, γ0, X) //
			.δ(q1, c, X, q1, X, X) //
			.δ(q1, ↄ, X, q1) //
			.δ(q1, null, γ0, q0, γ0) //
			.δ(q1, Ↄ, X, q2) //
			.δ(q2, null, X, q2) //
			.δ(q2, null, γ0, q0, γ0) //
			.δ(q0, C, γ0, q3, γ0, X) //
			.δ(q1, C, X, q3, X) //
			.δ(q3, c, X, q3, X) //
			.δ(q3, ↄ, X, q3, X) //
			.δ(q3, C, X, q3, X) //
			.δ(q3, Ↄ, X, q2) //
			.q0(q0) //
			.F(q0) //
			.γ0(γ0) //
			.go();

	public static void main(String[] args) {
		__.c().c().ↄ().ↄ().$(); // (())
		__.c().c().ↄ().ↄ().ↄ().ø(); // (()))
		__.c().C().ↄ().ↄ().ↄ().c().Ↄ().c().ↄ().$(); // ([)))(]()
		__.c().C().ↄ().ↄ().¢(); // ([))
		__.C().c().ↄ().ↄ().Ↄ().C().C().Ↄ().$(); // [())][[]
	}
}
