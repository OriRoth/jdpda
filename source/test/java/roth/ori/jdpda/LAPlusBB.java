package roth.ori.jdpda;

import static roth.ori.jdpda.LAPlusBB.Letter.a;
import static roth.ori.jdpda.LAPlusBB.Letter.b;
import static roth.ori.jdpda.LAPlusBB.StackSymbol.X;
import static roth.ori.jdpda.LAPlusBB.StackSymbol.Y;
import static roth.ori.jdpda.LAPlusBB.State.q0;
import static roth.ori.jdpda.LAPlusBB.State.q1;
import static roth.ori.jdpda.LAPlusBB.State.q2;
import static roth.ori.jdpda.LAPlusBB.State.q3;

import static roth.ori.jdpda.generated.LAPlusBBAPI.START;

public class LAPlusBB {
	enum State {
		q0, q1, q2, q3
	}

	enum Letter {
		a, b
	}

	enum StackSymbol {
		X, Y
	}

	public static DPDA<State, Letter, StackSymbol> M = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, a, X, q1, Y, X) //
			.delta(q1, a, X, q1, X, X) //
			.delta(q1, b, X, q2) //
			.delta(q2, null, X, q2) //
			.delta(q2, b, Y, q3) //
			.setInitialState(q0) //
			.setAccepting(q3) //
			.setInitialStackSymbol(X) //
			.build();
	
	public static void main(String[] args) {
		START().a().a().a().a().b().b().ACCEPT();
		START().b().STUCK();
		START().a().b().a().STUCK();
	}
}
