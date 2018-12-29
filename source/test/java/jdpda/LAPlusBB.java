package jdpda;

import static jdpda.LAPlusBB.Letter.a;
import static jdpda.LAPlusBB.Letter.b;
import static jdpda.LAPlusBB.StackSymbol.X;
import static jdpda.LAPlusBB.StackSymbol.Y;
import static jdpda.LAPlusBB.State.q0;
import static jdpda.LAPlusBB.State.q1;
import static jdpda.LAPlusBB.State.q2;
import static jdpda.LAPlusBB.State.q3;
import static jdpda.generated.LAPlusBBAPI.START;

import jdpda.DPDA;

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
			.δ(q0, a, X, q1, Y, X) //
			.δ(q1, a, X, q1, X, X) //
			.δ(q1, b, X, q2) //
			.δ(q2, null, X, q2) //
			.δ(q2, b, Y, q3) //
			.q0(q0) //
			.F(q3) //
			.γ0(X) //
			.go();
	
	public static void main(String[] args) {
		START().a().a().a().a().b().b().$();
		START().b().ø();
		START().a().b().a().ø();
	}
}
