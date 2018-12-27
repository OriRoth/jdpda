package jdpda.generated;

public class LLispParenthesesAPI {
	public interface Stuck { void STUCK(); }
	public interface Terminated { void TERMINATED(); }
	public interface Accept { void ACCEPT(); }

	public static q0_E<Accept, Stuck, Stuck, Stuck> START() { return null; }

	public interface q0_E<q0, q1, q2, q3> extends Accept {
		q1_X_E<q0, q1, q2, q3> c();
		Stuck ↄ();
		q3_X_E<q0, q1, q2, q3> C();
		Stuck Ↄ();
	}
	public interface q1_X_E<q0, q1, q2, q3> extends Terminated {
		q1_X_X<q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q3_E<q0, q1, q2, q3>> c();
		q0_E<q0, q1, q2, q3> ↄ();
		q3_X<q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q3_E<q0, q1, q2, q3>> C();
		q0_E<q0, q1, q2, q3> Ↄ();
	}
	public interface q1_X_X<q0, q1, q2, q3> extends Terminated {
		q1_X_X<q0_X<q0, q1, q2, q3>, q1_X<q0, q1, q2, q3>, q2, q3_X<q0, q1, q2, q3>> c();
		q1_X<q0, q1, q2, q3> ↄ();
		q3_X<q0_X<q0, q1, q2, q3>, q1_X<q0, q1, q2, q3>, q2, q3_X<q0, q1, q2, q3>> C();
		q2 Ↄ();
	}
	public interface q0_X<q0, q1, q2, q3> extends Accept {
		Stuck c();
		Stuck ↄ();
		Stuck C();
		Stuck Ↄ();
	}
	public interface q1_X<q0, q1, q2, q3> extends Terminated {
		q1_X_X<q0, q1, q2, q3> c();
		q1 ↄ();
		q3_X<q0, q1, q2, q3> C();
		q2 Ↄ();
	}
	public interface q3_X<q0, q1, q2, q3> extends Terminated {
		q3_X<q0, q1, q2, q3> c();
		q3_X<q0, q1, q2, q3> ↄ();
		q3_X<q0, q1, q2, q3> C();
		q2 Ↄ();
	}
	public interface q3_E<q0, q1, q2, q3> extends Terminated {
		Stuck c();
		Stuck ↄ();
		Stuck C();
		Stuck Ↄ();
	}
	public interface q3_X_E<q0, q1, q2, q3> extends Terminated {
		q3_X<q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q3_E<q0, q1, q2, q3>> c();
		q3_X<q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q3_E<q0, q1, q2, q3>> ↄ();
		q3_X<q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q0_E<q0, q1, q2, q3>, q3_E<q0, q1, q2, q3>> C();
		q0_E<q0, q1, q2, q3> Ↄ();
	}
}
