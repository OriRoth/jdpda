package jdpda.generated;

public class LAStarAPI {
	public interface Stuck { void STUCK(); }
	public interface Terminated { void TERMINATED(); }
	public interface Accept { void ACCEPT(); }
	public static q0_X<Stuck, Accept, Accept> START() { return null; }

	public interface q0_X<q0, q1, q2> extends Terminated {
		q1_X<q0, q1, q2> a();
	}
	public interface q1_X<q0, q1, q2> extends Accept {
		q2_X<q0, q1, q2> a();
	}
	public interface q2_X<q0, q1, q2> extends Accept {
		q2_X<q0, q1, q2> a();
	}
}
