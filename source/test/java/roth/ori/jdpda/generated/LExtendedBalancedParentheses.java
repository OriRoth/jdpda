package roth.ori.jdpda.generated;

public class LExtendedBalancedParentheses {
	interface Stuck {
		void STUCK();
	}

	interface Terminated {
		void TERMINATED();
	}

	interface Accept {
		void ACCEPT();
	}

	public static push_q0_$<Accept, Terminated, Terminated> START() {
		return null;
	}

	interface push_q0_X<jump_q0, jump_q1, jump_q2> extends Accept {
		Stuck lp();

		Stuck rp();

		Stuck RP();
	}

	interface push_q1_X<jump_q0, jump_q1, jump_q2> extends Terminated {
		push_q1_XX<jump_q0, jump_q1, jump_q2> lp();

		jump_q1 rp();

		jump_q2 RP();
	}

	interface push_q1_XX<jump_q0, jump_q1, jump_q2> extends Terminated {
		push_q1_XX<push_q0_X<jump_q0, jump_q1, jump_q2>, push_q1_X<jump_q0, jump_q1, jump_q2>, jump_q2> lp();

		push_q1_X<jump_q0, jump_q1, jump_q2> rp();

		jump_q2 RP();
	}

	interface push_q1_X$<jump_q0, jump_q1, jump_q2> extends Terminated {
		push_q1_XX<push_q0_$<jump_q0, jump_q1, jump_q2>, push_q0_$<jump_q0, jump_q1, jump_q2>, push_q0_$<jump_q0, jump_q1, jump_q2>> lp();

		push_q0_$<jump_q0, jump_q1, jump_q2> rp();

		push_q0_$<jump_q0, jump_q1, jump_q2> RP();
	}

	interface push_q0_$<jump_q0, jump_q1, jump_q2> extends Accept {
		push_q1_X$<jump_q0, jump_q1, jump_q2> lp();

		Stuck rp();

		Stuck RP();
	}

	public static void main(String[] args) {
		START().lp().rp().ACCEPT();
		START().lp().rp().rp().STUCK();
		START().lp().lp().lp().rp().rp().TERMINATED();
		START().lp().lp().lp().rp().rp().rp().ACCEPT();
		START().lp().lp().lp().rp().RP().ACCEPT();
	}
}
