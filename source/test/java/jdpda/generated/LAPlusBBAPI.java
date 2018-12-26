package jdpda.generated;

public class LAPlusBBAPI {
	public interface Stuck { void STUCK(); }
	public interface Terminated { void TERMINATED(); }
	public interface Accept { void ACCEPT(); }
	public static q0_X<Stuck, Stuck, Stuck, Accept> START() { return null; }

	public interface q0_X<q0, q1, q2, q3> extends Terminated {
		q1_X_Y<q0, q1, q2, q3> a();
		Stuck b();
	}
	public interface q1_X_Y<q0, q1, q2, q3> extends Terminated {
		q1_X_X<q0_Y<q0, q1, q2, q3>, q1_Y<q0, q1, q2, q3>, q2_Y<q0, q1, q2, q3>, q3_Y<q0, q1, q2, q3>> a();
		q2_Y<q0, q1, q2, q3> b();
	}
	public interface q1_X_X<q0, q1, q2, q3> extends Terminated {
		q1_X_X<q0_X<q0, q1, q2, q3>, q1_X<q0, q1, q2, q3>, q2, q3_X<q0, q1, q2, q3>> a();
		q2 b();
	}
	public interface q1_X<q0, q1, q2, q3> extends Terminated {
		q1_X_X<q0, q1, q2, q3> a();
		q2 b();
	}
	public interface q3_X<q0, q1, q2, q3> extends Accept {
		Stuck a();
		Stuck b();
	}
	public interface q0_Y<q0, q1, q2, q3> extends Terminated {
		Stuck a();
		Stuck b();
	}
	public interface q1_Y<q0, q1, q2, q3> extends Terminated {
		Stuck a();
		Stuck b();
	}
	public interface q2_Y<q0, q1, q2, q3> extends Terminated {
		Stuck a();
		q3 b();
	}
	public interface q3_Y<q0, q1, q2, q3> extends Accept {
		Stuck a();
		Stuck b();
	}
}
