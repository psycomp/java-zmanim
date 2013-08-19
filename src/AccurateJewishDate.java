import java.util.*;
import net.sourceforge.zmanim.*;

public class AccurateJewishDate {

	public JewishDate JD;
	public HalachicTime HT;
	public ZmanimCalendar ZC;

// Test program ====================================================================================

	public static void main(String[] args) {
		//System.out.println(new AccurateJewishDate());
		AccurateJewishDate AJD = new AccurateJewishDate();
		AJD.getJD();
	}

// Object Methods ==================================================================================

	public AccurateJewishDate() {
		this(new JewishDate(), HalachicTime.now(), Zmanim.getInstance());
	}

	public AccurateJewishDate(JewishDate JD, HalachicTime HT, ZmanimCalendar ZC) {
		this.JD = JD;
		this.HT = HT;
		this.ZC = ZC;
	}

	public JewishDate getJD() {
		Date d1 = ZC.getSunset();
		d1.setYear(0);
		d1.setMonth(0);
		d1.setDate(0);
		// System.err.println(d1);

		Date d2 = HT.getCalendar().getTime();
		// System.err.println(d2);

		// System.err.println(JD);
		if(d2.compareTo(d1) > 0) { JD = JD.add(1); }
		// System.err.println(JD);

		return JD;
	}

	public String toString() {
		return "{ " + JD.toString() +" "+ HT.toString() +" "+ ZC.toString() + " }";
	}
}
