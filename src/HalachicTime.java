import java.util.Calendar;
import net.sourceforge.zmanim.*;
import net.sourceforge.zmanim.util.*;
import java.util.TimeZone;

public class HalachicTime {

	int days, hours, chalokim;
	boolean autoReduce = false;

// Public Contructors

	public HalachicTime() {
		days = 0;
		hours = 0;
		chalokim = 0;

        String locationName = "Brooklyn, NY";
        double latitude = 40.604162;
        double longitude = -73.951044;
        double elevation = 0; //optional elevation
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
        GeoLocation location = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        ComplexZmanimCalendar czc = new ComplexZmanimCalendar(location);
	}

	public HalachicTime(int a, int b, int c, boolean ar) {
		days = a;
		hours = b;
		chalokim = c;
		autoReduce = ar;
		if(autoReduce == true)
			balance();
	}

	public HalachicTime(int a, int b, int c) { this(a, b, c, false); }

	public HalachicTime(HalachicTime h) {
		days = h.days;
		hours = h.hours;
		chalokim = h.chalokim;
		balance();
	}

// Setters

	public HalachicTime set(int c) {
        days = 0;
        hours = 0;
        chalokim = c;
		balance();
		return this;
    }

	public HalachicTime set(int a, int b, int c) {
        days = a;
        hours = b;
        chalokim = c;
		balance();
		return this;
    }

	public HalachicTime set(HalachicTime h) {
        days = h.days;
        hours = h.hours;
        chalokim = h.chalokim;
        balance();
		return this;
    }

	public boolean autoReduce(boolean b) {
		if(b==true) {
			round();
			return autoReduce = true;
		} else {
			return autoReduce = false;
		}
	}

// Utility functions

	public HalachicTime reduce() { return chalokimOnly(); }

	public HalachicTime chalokimOnly() {
		chalokim = inChalokim();
		days = 0;
		hours = 0;
		return this;
	}

	public int inChalokim() { return chalokim + (hours * 1080) + (days * 25920); }

	public HalachicTime normalize() { return balance(); }

	public HalachicTime balance() {
		chalokimOnly();
		days = chalokim / 25920;
		if(autoReduce) round();
		chalokim %= 25920;
		hours = chalokim / 1080;
		chalokim %= 1080;
		return this;
	}

	public HalachicTime round() { return dropWeeks(); }

	public HalachicTime dropWeeks() {
		days %= 7;
		return this;
	}

// Object math

	public HalachicTime add(HalachicTime m) {
		addChalokim(m.inChalokim());
		balance();
		return this;
	}

	public HalachicTime subtract(HalachicTime t) {
		if(days == 0) days = 7;
		chalokimOnly();
		chalokim -= t.inChalokim();
		balance();
		if(days == 0) days = 7;
		return this;
	}

	public HalachicTime multiply(int x) {
		chalokimOnly();
		chalokim *= x;
		balance();
		return this;
	}

	public HalachicTime addChalokim(int x) {
		chalokim += x;
		balance();
		return this;
	}

	public HalachicTime addHours(int x) {
		hours += x;
		balance();
		return this;
	}

	public HalachicTime addDays(int x) {
		days += x;
		balance();
		return this;
	}

// Shortcuts

	public HalachicTime addMachzor()			{ return add(new HalachicTime(2, 16, 595)); }
	public HalachicTime addRegularYear()		{ return add(new HalachicTime(4, 8, 876)); }
	public HalachicTime addLeapYear()			{ return add(new HalachicTime(5, 21, 589)); }
	public HalachicTime addMonth()				{ return add(new HalachicTime(1, 12, 793)); }

	public HalachicTime addMachzor(int x)		{ return add(new HalachicTime(2, 16, 595).multiply(x)); }
	public HalachicTime addRegularYear(int x)	{ return add(new HalachicTime(4, 8, 876).multiply(x)); }
	public HalachicTime addLeapYear(int x)		{ return add(new HalachicTime(5, 21, 589).multiply(x)); }
	public HalachicTime addMonth(int x)			{ return add(new HalachicTime(1, 12, 793).multiply(x)); }

// Output methods

	public String toString() { return days +" days/"+ hours +" hours/"+ chalokim + " chalokim"; }

// Static Methods

	public static HalachicTime getInstance(int x) {
		switch(x) {
			case ORIGINAL_MOLAD:
				return new HalachicTime(MONDAY,5,204);
			case MACHZOR:
				return new HalachicTime(2,16,595);
			case LEAP_YEAR:
				return new HalachicTime(5, 21, 589);
			case REGULAR_YEAR:
				return new HalachicTime(4, 8, 876);
			case MONTH:
				return new HalachicTime(1, 12, 793);
			case TEKUFA_UNIT:
				return new HalachicTime(0, 1, 485);
			case ORIGINAL_TEKUFA:
				HalachicTime m = new HalachicTime();
				// m.autoReduce();
				m.set(-12, -20, -204);
				return m;
			default:
				return new HalachicTime();
		}
	}

	public static HalachicTime now() {
		HalachicTime m = new HalachicTime();
		Calendar rightNow = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		int days, hours;
		double chalokim;

		days = rightNow.get(Calendar.DAY_OF_WEEK);
		hours = rightNow.get(Calendar.HOUR_OF_DAY);
		chalokim = ((rightNow.get(Calendar.MINUTE) * 60.0) + rightNow.get(Calendar.SECOND)) * 0.3;
		m.set(days, hours, (int)chalokim);
		return m;
	}

	public static void main(String[] args) {
		System.out.println(HalachicTime.now());
	}

// Constants

	public static final int SUNDAY = 1;
	public static final int MONDAY = 2;
	public static final int TUESDAY = 3;
	public static final int WEDNESDAY = 4;
	public static final int THURSDAY = 5;
	public static final int FRIDAY = 6;
	public static final int SATURDAY = 7;
	public static final int SHABBOS = 7;

	public static final int ORIGINAL_MOLAD = 0;
	public static final int MACHZOR = 1;
	public static final int LEAP_YEAR = 2;
	public static final int REGULAR_YEAR = 3;
	public static final int MONTH = 4;
	public static final int TEKUFA_UNIT = 5;
	public static final int ORIGINAL_TEKUFA = 6;

}

