package jdpda.generated;

public class LBalancedParenthesesAPI {
	public interface Stuck { void STUCK(); }
	public interface Terminated { void TERMINATED(); }
	public interface Accept { void ACCEPT(); }
	public static q0_E<Accept, Stuck> START() { return null; }

	public interface q0_E<q0, q1> extends Accept {
		q1_X_E<q0, q1> lp();
		Stuck rp();
	}
	public interface q1_X_E<q0, q1> extends Terminated {
		q1_X_X<q0_E<q0, q1>, q0_E<q0, q1>> lp();
		q0_E<q0, q1> rp();
	}
	public interface q1_X_X<q0, q1> extends Terminated {
		q1_X_X<q0_X<q0, q1>, q1_X<q0, q1>> lp();
		q1_X<q0, q1> rp();
	}
	public interface q0_X<q0, q1> extends Accept {
		Stuck lp();
		Stuck rp();
	}
	public interface q1_X<q0, q1> extends Terminated {
		q1_X_X<q0, q1> lp();
		q1 rp();
	}
}
