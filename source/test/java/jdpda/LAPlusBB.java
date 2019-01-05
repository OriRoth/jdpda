package jdpda;

import static jdpda.LAPlusBB.Σ.a;
import static jdpda.LAPlusBB.Σ.b;
import static jdpda.LAPlusBB.Γ.γ0;
import static jdpda.LAPlusBB.Γ.Y;
import static jdpda.LAPlusBB.Q.q0;
import static jdpda.LAPlusBB.Q.q1;
import static jdpda.LAPlusBB.Q.q2;
import static jdpda.LAPlusBB.Q.q3;
import static jdpda.generated.LAPlusBBAPI.*;

import jdpda.DPDA;

public class LAPlusBB {
	enum Q {
		q0, q1, q2, q3
	}

	enum Σ {
		a, b
	}

	enum Γ {
		γ0, Y
	}

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
			.δ(q0, a, γ0, q1, Y, γ0) //
			.δ(q1, a, γ0, q1, γ0, γ0) //
			.δ(q1, b, γ0, q2) //
			.δ(q2, null, γ0, q2) //
			.δ(q2, b, Y, q3) //
			.q0(q0) //
			.F(q3) //
			.γ0(γ0) //
			.go();
	
	public static void main(String[] args) {
		__.a().a().a().a().b().b().$();
		__.b().ø();
		__.a().b().a().ø();
	}
}
