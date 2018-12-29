package jdpda;

import static jdpda.LAStar.Letter.a;
import static jdpda.LAStar.StackSymbol.X;
import static jdpda.LAStar.State.q0;
import static jdpda.LAStar.State.q1;
import static jdpda.LAStar.State.q2;
import static jdpda.generated.LAStarAPI.START;

import jdpda.DPDA;

public class LAStar {
	enum State {
		q0, q1, q2
	}

	enum Letter {
		a
	}

	enum StackSymbol {
		X
	}

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.δ(q0, a, X, q1, X) //
			.δ(q1, a, X, q2, X) //
			.δ(q2, a, X, q2, X) //
			.q0(q0) //
			.F(q1, q2) //
			.γ0(X) //
			.go();

	public static void main(String[] args) {
		START().a().$();
		START().a().a().$();
		START().¢();
	}
}
