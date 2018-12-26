package jdpda.generated;

public class LExtendedBalancedParenthesesAPI {
	public interface Stuck { void STUCK(); }
	public interface Terminated { void TERMINATED(); }
	public interface Accept { void ACCEPT(); }
	public static q0_$<Accept, Stuck, Stuck> START() { return null; }

	public interface q0_$<q0, q1, q2> extends Accept {
		q1_X_$<q0, q1, q2> c();
		Stuck ↄ();
		Stuck Ↄ();
	}
	public interface q1_X_$<q0, q1, q2> extends Terminated {
		q1_X_X<q0_$<q0, q1, q2>, q0_$<q0, q1, q2>, q0_$<q0, q1, q2>> c();
		q0_$<q0, q1, q2> ↄ();
		q0_$<q0, q1, q2> Ↄ();
	}
	public interface q1_X_X<q0, q1, q2> extends Terminated {
		q1_X_X<q0_X<q0, q1, q2>, q1_X<q0, q1, q2>, q2> c();
		q1_X<q0, q1, q2> ↄ();
		q2 Ↄ();
	}
	public interface q0_X<q0, q1, q2> extends Accept {
		Stuck c();
		Stuck ↄ();
		Stuck Ↄ();
	}
	public interface q1_X<q0, q1, q2> extends Terminated {
		q1_X_X<q0, q1, q2> c();
		q1 ↄ();
		q2 Ↄ();
	}
}
