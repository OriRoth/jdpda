package roth.ori.jdpda.generated;

public class LLispParentheses {
	interface Stuck {
		void STUCK();
	}

	interface Terminated {
		void TERMINATED();
	}

	interface Accept {
		void ACCEPT();
	}

	public static push_q0_E<Accept, Terminated, Terminated, Terminated> START() {
		return null;
	}

	interface push_q0_X<jump_q0, jump_q1, jump_q2, jump_q3> extends Accept {
		Stuck lp();

		Stuck rp();

		Stuck LP();

		Stuck RP();
	}

	interface push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3> lp();

		push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3> rp();

		push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3> LP();

		jump_q2 RP();
	}

	interface push_q1_X<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q1_XX<jump_q0, jump_q1, jump_q2, jump_q3> lp();

		jump_q1 rp();

		push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3> LP();

		jump_q2 RP();
	}

	interface push_q1_XX<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q1_XX<push_q0_X<jump_q0, jump_q1, jump_q2, jump_q3>, push_q1_X<jump_q0, jump_q1, jump_q2, jump_q3>, jump_q2, push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3>> lp();

		push_q1_X<jump_q0, jump_q1, jump_q2, jump_q3> rp();

		push_q3_X<push_q0_X<jump_q0, jump_q1, jump_q2, jump_q3>, push_q1_X<jump_q0, jump_q1, jump_q2, jump_q3>, jump_q2, push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3>> LP();

		jump_q2 RP();
	}

	interface push_q3_E<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		Stuck lp();

		Stuck rp();

		Stuck LP();

		Stuck RP();
	}

	interface push_q1_XE<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q1_XX<push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q3_E<jump_q0, jump_q1, jump_q2, jump_q3>> lp();

		push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3> rp();

		push_q3_X<push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q3_E<jump_q0, jump_q1, jump_q2, jump_q3>> LP();

		push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3> RP();
	}

	interface push_q3_XE<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q3_X<push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q3_E<jump_q0, jump_q1, jump_q2, jump_q3>> lp();

		push_q3_X<push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q3_E<jump_q0, jump_q1, jump_q2, jump_q3>> rp();

		push_q3_X<push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3>, push_q3_E<jump_q0, jump_q1, jump_q2, jump_q3>> LP();

		push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3> RP();
	}

	interface push_q0_E<jump_q0, jump_q1, jump_q2, jump_q3> extends Accept {
		push_q1_XE<jump_q0, jump_q1, jump_q2, jump_q3> lp();

		Stuck rp();

		push_q3_XE<jump_q0, jump_q1, jump_q2, jump_q3> LP();

		Stuck RP();
	}

	public static void main(String[] args) {
		START().lp().lp().rp().rp().ACCEPT(); // (())
		START().lp().lp().rp().rp().rp().STUCK(); // (()))
		START().lp().LP().rp().rp().rp().RP().lp().rp().ACCEPT(); // ([)))]()
		START().lp().LP().rp().rp().TERMINATED(); // ([))
	}
}
