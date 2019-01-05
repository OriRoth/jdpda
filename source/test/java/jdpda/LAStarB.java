package jdpda;

import static jdpda.LAStarB.Letter.a;
import static jdpda.LAStarB.Letter.b;
import static jdpda.LAStarB.Γ.X;
import static jdpda.LAStarB.State.q0;
import static jdpda.LAStarB.State.q1;
import static jdpda.generated.LAStarBAPI.*;

import jdpda.DPDA;

public class LAStarB {
	enum State {
		q0, q1
	}

	enum Letter {
		a, b
	}

	enum Γ {
		X
	}

	public static DPDA<State, Letter, Γ> M = new DPDA.Builder<>(State.class, Letter.class, Γ.class) //
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
