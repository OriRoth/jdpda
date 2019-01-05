package jdpda;

import static jdpda.LAPlusBB.Σ.a;
import static jdpda.LAPlusBB.Σ.b;
import static jdpda.LAPlusBB.Γ.X;
import static jdpda.LAPlusBB.Γ.Y;
import static jdpda.LAPlusBB.State.q0;
import static jdpda.LAPlusBB.State.q1;
import static jdpda.LAPlusBB.State.q2;
import static jdpda.LAPlusBB.State.q3;
import static jdpda.generated.LAPlusBBAPI.*;

import jdpda.DPDA;

public class LAPlusBB {
	enum State {
		q0, q1, q2, q3
	}

	enum Σ {
		a, b
	}

	enum Γ {
		X, Y
	}

	public static DPDA<State, Σ, Γ> M = new DPDA.Builder<>(State.class, Σ.class, Γ.class) //
			.δ(q0, a, X, q1, Y, X) //
			.δ(q1, a, X, q1, X, X) //
			.δ(q1, b, X, q2) //
			.δ(q2, null, X, q2) //
			.δ(q2, b, Y, q3) //
			.q0(q0) //
			.F(q3) //
			.γ0(X) //
			.go();
	
	public static void main(String[] args) {
		__.a().a().a().a().b().b().$();
		__.b().ø();
		__.a().b().a().ø();
	}
}
