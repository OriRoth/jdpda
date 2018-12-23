package roth.ori.jdpda.generated;

public class LAPlusBB {
	interface Stuck {
		void STUCK();
	}

	interface Terminated {
		void TERMINATED();
	}

	interface Accept {
		void ACCEPT();
	}

	public static push_q0_X<Terminated, Terminated, Terminated, Accept> START() {
		return null;
	}

	interface push_q1_X<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q1_XX<jump_q0, jump_q1, jump_q2, jump_q3> a();

		jump_q2 b();
	}

	interface push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3> extends Accept {
		Stuck a();

		Stuck b();
	}

	interface push_q1_XX<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q1_XX<push_q0_X<jump_q0, jump_q1, jump_q2, jump_q3>, push_q1_X<jump_q0, jump_q1, jump_q2, jump_q3>, jump_q2, push_q3_X<jump_q0, jump_q1, jump_q2, jump_q3>> a();

		jump_q2 b();
	}

	interface push_q0_Y<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		Stuck a();

		Stuck b();
	}

	interface push_q1_Y<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		Stuck a();

		Stuck b();
	}

	interface push_q2_Y<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		Stuck a();

		jump_q3 b();
	}

	interface push_q3_Y<jump_q0, jump_q1, jump_q2, jump_q3> extends Accept {
		Stuck a();

		Stuck b();
	}

	interface push_q1_XY<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q1_XX<push_q0_Y<jump_q0, jump_q1, jump_q2, jump_q3>, push_q1_Y<jump_q0, jump_q1, jump_q2, jump_q3>, push_q2_Y<jump_q0, jump_q1, jump_q2, jump_q3>, push_q3_Y<jump_q0, jump_q1, jump_q2, jump_q3>> a();

		push_q2_Y<jump_q0, jump_q1, jump_q2, jump_q3> b();
	}

	interface push_q0_X<jump_q0, jump_q1, jump_q2, jump_q3> extends Terminated {
		push_q1_XY<jump_q0, jump_q1, jump_q2, jump_q3> a();

		Stuck b();
	}

	public static void main(String[] args) {
		START() //
				.a().a().a().a().b().b().ACCEPT() //
		;
		START().b().STUCK();
		START().a().b().a().STUCK();
	}
}
