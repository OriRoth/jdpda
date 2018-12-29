package jdpda.generated;

public class LBalancedParenthesesAPI {
	public interface ø { void ø(); }
	public interface ¢ { void ¢(); }
	public interface $ { void $(); }
	public static q0_E<$, ø> START() { return null; }

	public interface q0_E<τq0, τq1> extends $ {
		q1_X_E<τq0, τq1> lp();
		ø rp();
	}
	public interface q1_X_E<τq0, τq1> extends ¢ {
		q1_X_X<q0_E<τq0, τq1>, q0_E<τq0, τq1>> lp();
		q0_E<τq0, τq1> rp();
	}
	public interface q1_X_X<τq0, τq1> extends ¢ {
		q1_X_X<q0_X<τq0, τq1>, q1_X<τq0, τq1>> lp();
		q1_X<τq0, τq1> rp();
	}
	public interface q0_X<τq0, τq1> extends $ {
		ø lp();
		ø rp();
	}
	public interface q1_X<τq0, τq1> extends ¢ {
		q1_X_X<τq0, τq1> lp();
		τq1 rp();
	}
}
