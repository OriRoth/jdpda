package jdpda;

import static jdpda.LAStarB.Σ.a;
import static jdpda.LAStarB.Σ.b;
import static jdpda.LAStarB.Γ.X;
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
		X
	}

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
			.δ(q0, a, X, q0, X, X) //
			.δ(q0, b, X, q1) //
			.δ(q1, null, X, q1) //
			.q0(q0) //
			.F(q1) //
			.γ0(X) //
			.go();

	public static void main(String[] args) {
		__.a().a().a().b().$();
		__.b().$();
	}
}
