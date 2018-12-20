package roth.ori.jdpda;

import static roth.ori.jdpda.LAStarB.Letter.a;
import static roth.ori.jdpda.LAStarB.Letter.b;
import static roth.ori.jdpda.LAStarB.StackSymbol.X;
import static roth.ori.jdpda.LAStarB.State.q0;
import static roth.ori.jdpda.LAStarB.State.q1;

import org.junit.Test;

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

	DPDA<State, Letter, StackSymbol> dpda = new DPDA.Builder<>(State.class, Letter.class, StackSymbol.class) //
			.delta(q0, a, X, q0, X, X) //
			.delta(q0, b, X, q1) //
			.delta(q1, null, X, q1) //
			.setInitialState(q0) //
			.setAccepting(q1) //
			.setInitialStackSymbol(X) //
			.build();

	String clazz = DPDA2JavaFluentAPIEncoder.getJavaFluentAPI("LAStarB", dpda);

	@Test
	public void aStarB() {
		System.out.println(clazz);
	}
}
