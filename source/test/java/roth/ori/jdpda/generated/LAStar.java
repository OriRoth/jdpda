package roth.ori.jdpda.generated;

public class LAStar {
	interface Stuck {
		void STUCK();
	}

	interface Terminated {
		void TERMINATED();
	}

	interface Accept {
		void ACCEPT();
	}

	public static push_q0_X<Terminated, Accept, Accept> START() {
		return null;
	}

	interface push_q2_X<jump_q0, jump_q1, jump_q2> extends Accept {
		push_q2_X<jump_q0, jump_q1, jump_q2> a();
	}

	interface push_q1_X<jump_q0, jump_q1, jump_q2> extends Accept {
		push_q2_X<jump_q0, jump_q1, jump_q2> a();
	}

	interface push_q0_X<jump_q0, jump_q1, jump_q2> extends Terminated {
		push_q1_X<jump_q0, jump_q1, jump_q2> a();
	}

	public static void main(String[] args) {
		START().a().ACCEPT();
		START().a().a().ACCEPT();
		START().TERMINATED();
	}
}
