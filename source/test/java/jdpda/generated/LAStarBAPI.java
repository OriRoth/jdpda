package jdpda.generated;

public class LAStarBAPI {
	public interface Stuck { void STUCK(); }
	public interface Terminated { void TERMINATED(); }
	public interface Accept { void ACCEPT(); }

	public static q0_X<Stuck, Accept> START() { return null; }

	public interface q0_X<q0, q1> extends Terminated {
		q0_X_X<q0, q1> a();
		q1 b();
	}
	public interface q0_X_X<q0, q1> extends Terminated {
		q0_X_X<q0_X<q0, q1>, q1> a();
		q1 b();
	}
}
