package roth.ori.jdpda.generated;

public class LAStarB {
	interface Stuck {
		void STUCK();
	}

	interface Terminated {
		void TERMINATED();
	}

	interface Accept {
		void ACCEPT();
	}

	public static push_q0_X<Terminated, Accept> START() {
		return null;
	}

	interface push_q0_XX<jump_q0, jump_q1> extends Terminated {
		push_q0_XX<push_q0_X<jump_q0, jump_q1>, jump_q1> a();

		jump_q1 b();
	}

	interface push_q0_X<jump_q0, jump_q1> extends Terminated {
		push_q0_XX<jump_q0, jump_q1> a();

		jump_q1 b();
	}

	public static void main(String[] args) {
		START().a().a().a().b().ACCEPT();
		START().b().ACCEPT();
	}
}
