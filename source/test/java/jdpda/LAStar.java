package jdpda;

import static jdpda.LAStar.Σ.a;
import static jdpda.LAStar.Γ.γ0;
import static jdpda.LAStar.Q.q0;
import static jdpda.LAStar.Q.q1;
import static jdpda.LAStar.Q.q2;
import static jdpda.generated.LAStarAPI.*;

import jdpda.DPDA;

public class LAStar {
	enum Q {
		q0, q1, q2
	}

	enum Σ {
		a
	}

	enum Γ {
		γ0
	}

	public static DPDA<Q, Σ, Γ> M = new DPDA.Builder<>(Q.class, Σ.class, Γ.class) //
			.δ(q0, a, γ0, q1, γ0) //
			.δ(q1, a, γ0, q2, γ0) //
			.δ(q2, a, γ0, q2, γ0) //
			.q0(q0) //
			.F(q1, q2) //
			.γ0(γ0) //
			.go();

	public static void main(String[] args) {
		__.a().$();
		__.a().a().$();
		__.¢();
	}
}
