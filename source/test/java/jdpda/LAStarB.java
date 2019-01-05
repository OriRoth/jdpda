package jdpda;

import static jdpda.LAStarB.Σ.a;
import static jdpda.LAStarB.Σ.b;
import static jdpda.LAStarB.Γ.γ0;
import static jdpda.LAStarB.Q.q0;
import static jdpda.LAStarB.Q.q1;
import static jdpda.generated.LAStarBAPI.*;

import jdpda.DPDA;

public class LAStarB {
	enum Q {
		q0, q1
	}

	enum Σ {
		a, b
	}

	enum Γ {
		γ0
	}

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
			.δ(q0, a, γ0, q0, γ0, γ0) //
			.δ(q0, b, γ0, q1) //
			.δ(q1, null, γ0, q1) //
			.q0(q0) //
			.F(q1) //
			.γ0(γ0) //
			.go();

	public static void main(String[] args) {
		__.a().a().a().b().$();
		__.b().$();
	}
}
