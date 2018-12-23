package roth.ori.jdpda.generated;

public class LBalancedParentheses {
	interface Stuck {
		void STUCK();
	}

	interface Terminated {
		void TERMINATED();
	}

	interface Accept {
		void ACCEPT();
	}

	public static push_q0_E<Accept, Terminated> START() {
		return null;
	}

	interface push_q0_X<jump_q0, jump_q1> extends Accept {
		Stuck lp();

		Stuck rp();
	}

	interface push_q1_X<jump_q0, jump_q1> extends Terminated {
		push_q1_XX<jump_q0, jump_q1> lp();

		jump_q1 rp();
	}

	interface push_q1_XX<jump_q0, jump_q1> extends Terminated {
		push_q1_XX<push_q0_X<jump_q0, jump_q1>, push_q1_X<jump_q0, jump_q1>> lp();

		push_q1_X<jump_q0, jump_q1> rp();
	}

	interface push_q1_XE<jump_q0, jump_q1> extends Terminated {
		push_q1_XX<push_q0_E<jump_q0, jump_q1>, push_q0_E<jump_q0, jump_q1>> lp();

		push_q0_E<jump_q0, jump_q1> rp();
	}

	interface push_q0_E<jump_q0, jump_q1> extends Accept {
		push_q1_XE<jump_q0, jump_q1> lp();

		Stuck rp();
	}
	
	public static void main(String[] args) {
		START().lp().lp().lp().rp().rp().rp().ACCEPT();
	}
}
