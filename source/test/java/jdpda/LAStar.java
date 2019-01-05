package jdpda;

import static jdpda.LAStar.Letter.a;
import static jdpda.LAStar.StackSymbol.X;
import static jdpda.LAStar.Q.q0;
import static jdpda.LAStar.Q.q1;
import static jdpda.LAStar.Q.q2;
import static jdpda.generated.LAStarAPI.*;

import jdpda.DPDA;

public class LAStar {
	enum Q {
		q0, q1, q2
	}

	enum Letter {
		a
	}

	enum StackSymbol {
		X
	}

	public static DPDA<Q, Letter, StackSymbol> M = new DPDA.Builder<>(Q.class, Letter.class, StackSymbol.class) //
			.δ(q0, a, X, q1, X) //
			.δ(q1, a, X, q2, X) //
			.δ(q2, a, X, q2, X) //
			.q0(q0) //
			.F(q1, q2) //
			.γ0(X) //
			.go();

	public static void main(String[] args) {
		__.a().$();
		__.a().a().$();
		__.¢();
	}
}
