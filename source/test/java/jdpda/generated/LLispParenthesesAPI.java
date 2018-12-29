package jdpda.generated;

public class LLispParenthesesAPI {
	public interface ø { void ø(); }
	public interface ¢ { void ¢(); }
	public interface $ { void $(); }
	public static q0_E<$, ø, ø, ø> START() { return null; }

	public interface q0_E<τq0, τq1, τq2, τq3> extends $ {
		q1_X_E<τq0, τq1, τq2, τq3> c();
		ø ↄ();
		q3_X_E<τq0, τq1, τq2, τq3> C();
		ø Ↄ();
	}
	public interface q1_X_E<τq0, τq1, τq2, τq3> extends ¢ {
		q1_X_X<q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q3_E<τq0, τq1, τq2, τq3>> c();
		q0_E<τq0, τq1, τq2, τq3> ↄ();
		q3_X<q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q3_E<τq0, τq1, τq2, τq3>> C();
		q0_E<τq0, τq1, τq2, τq3> Ↄ();
	}
	public interface q1_X_X<τq0, τq1, τq2, τq3> extends ¢ {
		q1_X_X<q0_X<τq0, τq1, τq2, τq3>, q1_X<τq0, τq1, τq2, τq3>, τq2, q3_X<τq0, τq1, τq2, τq3>> c();
		q1_X<τq0, τq1, τq2, τq3> ↄ();
		q3_X<q0_X<τq0, τq1, τq2, τq3>, q1_X<τq0, τq1, τq2, τq3>, τq2, q3_X<τq0, τq1, τq2, τq3>> C();
		τq2 Ↄ();
	}
	public interface q0_X<τq0, τq1, τq2, τq3> extends $ {
		ø c();
		ø ↄ();
		ø C();
		ø Ↄ();
	}
	public interface q1_X<τq0, τq1, τq2, τq3> extends ¢ {
		q1_X_X<τq0, τq1, τq2, τq3> c();
		τq1 ↄ();
		q3_X<τq0, τq1, τq2, τq3> C();
		τq2 Ↄ();
	}
	public interface q3_X<τq0, τq1, τq2, τq3> extends ¢ {
		q3_X<τq0, τq1, τq2, τq3> c();
		q3_X<τq0, τq1, τq2, τq3> ↄ();
		q3_X<τq0, τq1, τq2, τq3> C();
		τq2 Ↄ();
	}
	public interface q3_E<τq0, τq1, τq2, τq3> extends ¢ {
		ø c();
		ø ↄ();
		ø C();
		ø Ↄ();
	}
	public interface q3_X_E<τq0, τq1, τq2, τq3> extends ¢ {
		q3_X<q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q3_E<τq0, τq1, τq2, τq3>> c();
		q3_X<q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q3_E<τq0, τq1, τq2, τq3>> ↄ();
		q3_X<q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q0_E<τq0, τq1, τq2, τq3>, q3_E<τq0, τq1, τq2, τq3>> C();
		q0_E<τq0, τq1, τq2, τq3> Ↄ();
	}
}
