package roth.ori.jdpda;

import static roth.ori.jdpda.LAStarB.Letter.a;
import static roth.ori.jdpda.LAStarB.Letter.b;
import static roth.ori.jdpda.LAStarB.StackSymbol.X;
import static roth.ori.jdpda.LAStarB.State.q0;
import static roth.ori.jdpda.LAStarB.State.q1;
import static roth.ori.jdpda.generated.LAStarBAPI.START;

public class LAStarB {
	enum State {
		q0, q1
	}

	enum Letter {
		a, b
	}

	enum StackSymbol {
		X
	}

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, a, X, q0, X, X) //
			.delta(q0, b, X, q1) //
			.delta(q1, null, X, q1) //
			.setInitialState(q0) //
			.setAccepting(q1) //
			.setInitialStackSymbol(X) //
			.build();

	public static void main(String[] args) {
		START().a().a().a().b().ACCEPT();
		START().b().ACCEPT();
	}
}
