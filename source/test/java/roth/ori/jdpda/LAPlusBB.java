package roth.ori.jdpda;

import static roth.ori.jdpda.LAPlusBB.Letter.*;
import static roth.ori.jdpda.LAPlusBB.StackSymbol.*;
import static roth.ori.jdpda.LAPlusBB.State.*;

import org.junit.Test;

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

	DPDA<State, Letter, StackSymbol> dpda = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, a, X, q1, Y, X) //
			.delta(q1, a, X, q1, X, X) //
			.delta(q1, b, X, q2) //
			.delta(q2, null, X, q2) //
			.delta(q2, b, Y, q3) //
			.setInitialState(q0) //
			.setAccepting(q3) //
			.setInitialStackSymbol(X) //
			.build();

	String clazz = new DPDA2JavaFluentAPIEncoder<>("LAPlusBB", dpda).encoding;

	@Test
	public void aPlusBB() {
		System.out.println(clazz);
	}
}
