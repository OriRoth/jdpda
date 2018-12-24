package roth.ori.jdpda;

import static roth.ori.jdpda.LAStar.Letter.a;
import static roth.ori.jdpda.LAStar.StackSymbol.X;
import static roth.ori.jdpda.LAStar.State.q0;
import static roth.ori.jdpda.LAStar.State.q1;
import static roth.ori.jdpda.LAStar.State.q2;
import static roth.ori.jdpda.generated.LAStarAPI.START;

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
			.delta(q0, a, X, q1, X) //
			.delta(q1, a, X, q2, X) //
			.delta(q2, a, X, q2, X) //
			.setInitialState(q0) //
			.setAccepting(q1, q2) //
			.setInitialStackSymbol(X) //
			.build();

	public static void main(String[] args) {
		START().a().ACCEPT();
		START().a().a().ACCEPT();
		START().TERMINATED();
	}
}
