import java.util.*;
import net.sourceforge.zmanim.*;
import net.sourceforge.zmanim.util.*;
import java.util.TimeZone;
import java.util.Arrays;

public class JewishDate {

	public int month;
	public int day;
	public int year;

	public int englishMonth;
	public int englishDay;
	public int englishYear;

	public int absoluteDay;

	private int hour = -1;
	private int minute = -1;
	public int second = -1;

// Test program ====================================================================================

	public static void main(String[] args) {
		System.out.println(new JewishDate());
	}

// Object Methods ==================================================================================

	// Initialize with current date
	public JewishDate() { this(java.util.Calendar.getInstance()); }

	// Specifying the exact jewish date
	public JewishDate(int m, int d, int y) {
		this.month = m;
		this.day = d;
		this.year = y;

		convert();
		this.absoluteDay = JewishDate.GregorianToAbsolute(englishMonth, englishDay, englishYear);
	}

	// based on the absolute day
	public JewishDate(int absolute) {
		this.absoluteDay = absolute;

		int[] mdy = JewishDate.AbsoluteToHebrew(absolute);
		this.month = mdy[0];
		this.day = mdy[1];
		this.year = mdy[2];
        convert();
	}

	// clone of another day
	public JewishDate(JewishDate JD) { this(JD.absoluteDay); }

	// based on a java.util.Calendar
	public JewishDate(Calendar c) {
		this(JewishDate.GregorianToAbsolute(c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE), c.get(Calendar.YEAR)));
	}

	// based on the english Y-M-D
	public static JewishDate fromEnglishDate(int m, int d, int y) {
		JewishDate jd = new JewishDate();

		jd.englishMonth = m;
		jd.englishDay = d;
		jd.englishYear = y;
		jd.absoluteDay = JewishDate.GregorianToAbsolute(jd.englishMonth, jd.englishDay, jd.englishYear);

		int[] conversion = GregorianToHebrew(m,d,y);
		jd.month = conversion[0];
		jd.day = conversion[1];
		jd.year = conversion[2];

		return jd;
	}

	// Assign english values fgrom jewish
	private void convert() {
		int[] conversion = HebrewToGregorian(month, day, year);
		englishMonth = conversion[0];
		englishDay = conversion[1];
		englishYear = conversion[2];
	}

// Date Math ========================================================================================
// SHOULD THEY MODIFY THE OBJECT OR NOT???

	public JewishDate add(int x) {
		return new JewishDate(absoluteDay + x);
	}
	public JewishDate subtract(int x) {
		return new JewishDate(absoluteDay - x);
	}

	// Jump to a specific day
	// e.g. jd = jd.next(JewishDate.SHABBOS)
	//      jd = jd.previous(JewishDate.SUNDAY)
	public JewishDate next(int x) {
		JewishDate JD = new JewishDate(this);
		while(JD.dayOfWeek() != x) { JD = JD.add(1); }
		return JD;
	}

	public JewishDate previous(int x) {
		JewishDate JD = new JewishDate(this);
		while(JD.dayOfWeek() != x) { JD = JD.subtract(1); }
		return JD;
	}

	// Helper Methods

	public int dayOfWeek() { return this.absoluteDay%7+1; }

	public int weekOfYear() {
		int AD0 = JewishDate.HebrewToAbsolute(TISHREI, 1, year);
        int a = AD0+6-(AD0%7+1);
		int b = this.absoluteDay+6-(this.absoluteDay%7+1);
		return (b-a)/7+1;
	}


    public Calendar getCalendar() {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.YEAR, englishYear);
        c.set(Calendar.MONTH, englishMonth);
        c.set(Calendar.DAY_OF_MONTH, englishDay);
        return c;
    }
// String output ========================================================================================

	public String toString() {
		int[] h = {year, month, day};
		int[] e = {englishYear, englishMonth, englishDay};
		return "{JEWISH: "+Arrays.toString(h)+", ENGLISH: "+Arrays.toString(e)+"}";
	}

// Conversion routines (static) ============================================================================

	private static int HebrewEpoch = -1373429;
	private static int[] monthLengths = {0,31,28,31,30,31,30,31,31,30,31,30,31};

	public static int[] GregorianToHebrew(int month, int day, int year) {
		return AbsoluteToHebrew(GregorianToAbsolute(month,day,year));
	}

	public static int[] HebrewToGregorian(int month, int day, int year) {
		return AbsoluteToGregorian(HebrewToAbsolute(month,day,year));
	}

	public static int GregorianToAbsolute(int mon, int day, int year) {
		int month_sum = 0;
		int tempyear, AbsoluteDay;

		tempyear = year - 1;
		AbsoluteDay = 365 * tempyear + (int)(tempyear / 4) - (int)(tempyear / 100) + (int)(tempyear / 400);
		for(int x = 0; x <= mon - 1; x++)
			month_sum += monthLengths[x];
		AbsoluteDay += month_sum + day;

		if (GregorianLeapYear(year) &&  mon > 2) {
			AbsoluteDay++;
		}

		return AbsoluteDay;
	}

	public static int LastDayOfGregorianMonth(int month, int year) {
		if (GregorianLeapYear(year) && month == 2) {
			return 29;
		} else {
			return monthLengths[month];
		}
	}

	public static int[] AbsoluteToGregorian(int d) {
		int year, month, day;

		year = (int)(d / 366);
		while(d >= GregorianToAbsolute(1,1,year + 1)) { year++; }
		month = 1;
		while(d > GregorianToAbsolute(month, LastDayOfGregorianMonth(month,year), year)) { month++; }
		day = d - GregorianToAbsolute(month,1,year) + 1;

		int answer[] = {month, day, year};
		return answer;
	}

	public static boolean GregorianLeapYear(int year) {

		if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)))
			return true; 
		else
			return false;
	}

	public static boolean HebrewLeapYear(int year) {

		if ((((7 * year) + 1) % 19) < 7) 
			return true;
		else
			return false;
	}

	public static int LastMonthOfHebrewYear(int year) {

		if (HebrewLeapYear(year)) { return 13; }
		else { return 12; }
	}

	public static int HebrewCalendarElapsedDays(int year) {
		int MonthsElapsed, PartsElapsed, HoursElapsed,  ConjunctionDay, ConjunctionParts, AlternativeDay;

		MonthsElapsed = (235 * (int)((year - 1) / 19)) + (12 * ((year - 1) % 19)) + (int)((7 * ((year - 1) % 19) + 1) / 19);
		PartsElapsed = 204 + 793 * (MonthsElapsed % 1080);
		HoursElapsed = 5 + 12 * MonthsElapsed + 793 * (int)(MonthsElapsed / 1080) + (int)(PartsElapsed / 1080);
		ConjunctionDay = 1 + 29 * MonthsElapsed + (int)(HoursElapsed / 24);
		ConjunctionParts = 1080 * (HoursElapsed % 24) + PartsElapsed % 1080;

		AlternativeDay = 0;
		if ((ConjunctionParts >= 19440) ||
		(((ConjunctionDay % 7) == 2)
		&&  (ConjunctionParts >= 9924)
		&&  (!HebrewLeapYear(year))) ||
		(((ConjunctionDay % 7) == 1)
		&&  (ConjunctionParts >= 16789)
		&&  (HebrewLeapYear(year - 1))))
		{ AlternativeDay = ConjunctionDay + 1; }
		else    { AlternativeDay = ConjunctionDay; }

		if (((AlternativeDay % 7) == 0) ||
		((AlternativeDay % 7) == 3) ||
		((AlternativeDay % 7) == 5))
		{ return (1 + AlternativeDay); }
		else    { return AlternativeDay; }
	}

	public static int DaysInHebrewYear(int year) {
		return ((HebrewCalendarElapsedDays(year + 1)) - (HebrewCalendarElapsedDays(year)));
	}

	public static boolean LongHeshvan(int year) {
		if ((DaysInHebrewYear(year) % 10) == 5)
			return true;
		else
			return false;
	}       

	public static boolean ShortKislev(int year) {
		if ((DaysInHebrewYear(year) % 10) == 3)
			return true;
		else
			return false;
	}

	public static boolean isShlemah(int year) {
		return LongHeshvan(year) && ! ShortKislev(year);
	}

	public static boolean isChaserah(int year) {
		return ShortKislev(year) && ! LongHeshvan(year);
	}

	public static boolean isKesidrah(int year) {
		return ! ShortKislev(year) && ! LongHeshvan(year);
	}

	public static int LastDayOfHebrewMonth(int month, int year) {

		if ((month == 2) ||
		(month == 4) ||
		(month == 6) ||
		((month == 8) && (!LongHeshvan(year))) ||
		((month == 9) && ShortKislev(year)) ||
		(month == 10) ||
		((month == 12) && (!HebrewLeapYear(year))) ||
		(month == 13)) {
		return 29; 
		}
		else {
		return 30; 
		}
	}

	public static int HebrewToAbsolute(int month, int day, int year) {
		int DayInYear, m;

		DayInYear = day;
		if (month < 7) {
		m = 7;
		while (m <= (LastMonthOfHebrewYear(year))) {
			DayInYear += LastDayOfHebrewMonth(m++, year);
		}
		m = 1;
		while (m < month) {
			DayInYear += LastDayOfHebrewMonth(m, year);
			m++;
		}
		}
		else {
		m = 7;
		while (m < month) {
			DayInYear += LastDayOfHebrewMonth(m, year);
			m++;
		}
		}

		return(DayInYear + (HebrewCalendarElapsedDays(year) +
				 HebrewEpoch));
	}

	public static int[] AbsoluteToHebrew(int d) {
		int year, month, day;

		year = (int)((d - HebrewEpoch) / 366);
		while (d >= HebrewToAbsolute(7, 1, year + 1)) { year++; }
		if (d < HebrewToAbsolute(1, 1, year)) { month = 7; }
		else { month = 1; }
		while (d > HebrewToAbsolute(month, (LastDayOfHebrewMonth(month, year)), year)) { month++; }
		day = d - HebrewToAbsolute(month, 1, year) + 1;
		
		int answer[] = {month, day, year};
		return answer;
	}

	public static final int NISSAN = 1;
	public static final int NISAN = 1;
	public static final int IYAR = 2;
	public static final int SIVAN = 3;
	public static final int TAMUZ = 4;
	public static final int TAMMUZ = 4;
	public static final int AV= 5;
	public static final int ELUL = 6;
	public static final int TISHREI = 7;
	public static final int CHESHVAN = 8;
	public static final int MARCHESHVAN = 8;
	public static final int KISLEV = 9;
	public static final int TEVES = 10;
	public static final int TEVET = 10;
	public static final int SHVAT = 11;
	public static final int SHEVAT = 11;
	public static final int ADAR = 12;
	public static final int ADAR1 = 12;
	public static final int ADAR_I = 12;
	public static final int ADAR_II = 13;
	public static final int ADAR2 = 13;

	public static final int SUNDAY = 1;
	public static final int MONDAY = 2;
	public static final int TUESDAY = 3;
	public static final int WEDNESDAY = 4;
	public static final int THURSDAY = 5;
	public static final int FRIDAY = 6;
	public static final int SATURDAY = 7;
	public static final int SHABBOS = 7;
}
