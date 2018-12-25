package roth.ori.jdpda;

import static roth.ori.jdpda.L1.Letter.*;
import static roth.ori.jdpda.L1.StackSymbol.*;
import static roth.ori.jdpda.L1.State.*;

import org.junit.Test;

public class L1 {
	enum State {
		q0, q1, q2, q3, q4
	}

	enum Letter {
		a, b
	}

	enum StackSymbol {
		X
	}

	DPDA<State, Letter, StackSymbol> dpda = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.δ(q0, a, X, q0, X, X) //
			.δ(q0, b, X, q1) //
			.δ(q1, null, X, q2) //
			.δ(q2, null, X, q3, X, X, X) //
			.δ(q3, null, X, q4) //
			.δ(q4, null, X, q4) //
			.q0(q0) //
			.q$(q4) //
			.γ0(X) //
			.go();

	String clazz = new DPDA2JavaFluentAPIEncoder<>("L1", dpda).encoding;

	@Test
	public void aStarB() {
		System.out.println(clazz);
	}
}
