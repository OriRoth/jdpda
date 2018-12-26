package jdpda.generated;

public class LExtendedBalancedParenthesesAPI {
	public interface Stuck { void STUCK(); }
	public interface Terminated { void TERMINATED(); }
	public interface Accept { void ACCEPT(); }
	public static q0_$<Accept, Stuck, Stuck> START() { return null; }

	public interface q0_$<q0, q1, q2> extends Accept {
		q1_X_$<q0, q1, q2> lp();
		Stuck rp();
		Stuck RP();
	}
	public interface q1_X_$<q0, q1, q2> extends Terminated {
		q1_X_X<q0_$<q0, q1, q2>, q0_$<q0, q1, q2>, q0_$<q0, q1, q2>> lp();
		q0_$<q0, q1, q2> rp();
		q0_$<q0, q1, q2> RP();
	}
	public interface q1_X_X<q0, q1, q2> extends Terminated {
		q1_X_X<q0_X<q0, q1, q2>, q1_X<q0, q1, q2>, q2> lp();
		q1_X<q0, q1, q2> rp();
		q2 RP();
	}
	public interface q0_X<q0, q1, q2> extends Accept {
		Stuck lp();
		Stuck rp();
		Stuck RP();
	}
	public interface q1_X<q0, q1, q2> extends Terminated {
		q1_X_X<q0, q1, q2> lp();
		q1 rp();
		q2 RP();
	}
}
