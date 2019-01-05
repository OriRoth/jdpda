package jdpda;

import static jdpda.LExtendedBalancedParentheses.Σ.*;
import static jdpda.LExtendedBalancedParentheses.Γ.*;
import static jdpda.LExtendedBalancedParentheses.Q.*;
import static jdpda.generated.LExtendedBalancedParenthesesAPI.__;

import jdpda.DPDA;

public class LExtendedBalancedParentheses {
	enum Q { q0, q1, q2 }
	enum Σ { c, ↄ, Ↄ }
	enum Γ { γ0, γ1 }

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
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
		__.c().ↄ().$();
		__.c().ↄ().ↄ().ø();
		__.c().c().c().ↄ().ↄ().¢();
		__.c().c().c().ↄ().ↄ().ↄ().$();
		__.c().c().c().ↄ().Ↄ().c().ↄ().$();
		__.c().c().c().ↄ().Ↄ().c().¢();
		__.c().c().c().ↄ().Ↄ().c().Ↄ().$();
	}
}
