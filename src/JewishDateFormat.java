import java.util.*;
import net.sourceforge.zmanim.*;
import net.sourceforge.zmanim.util.*;
import java.util.TimeZone;

public class JewishDateFormat {

	private static String[] englishMonthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November","December"};
	private static String[] englishChodeshNames = {"Nissan", "Iyar", "Sivan", "Tamuz", "Av", "Elul", "Tishrei", "MarCheshvan", "Kislev", "Teves", "Sh'vat","Adar", "Adar II"};

	private static String[] englishWeekdayNames = {null, "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Shabbos"};

	private static String[] hebrewChodeshNames = {"\u05E0\u05D9\u05E1\u05DF", "\u05D0\u05D9\u05D9\u05E8", "\u05E1\u05D9\u05D5\u05DF", "\u05EA\u05BC\u05DE\u05D5\u05D6", "\u05D0\u05D1", "\u05D0\u05DC\u05D5\u05BC\u05DC", "\u05EA\u05BC\u05B4\u05E9\u05C1\u05E8\u05D9", "\u05D7\u05E9\u05C1\u05D5\u05DF", "\u05DB\u05BC\u05E1\u05DC\u05D5", "\u05D8\u05D1\u05D8", "\u05E9\u05C1\u05D1\u05D8", "\u05D0\u05D3\u05E8", "\u05D0\u05D3\u05E8 \u05D1\u05F3"};
	private static String[] hebrewWeekdayNames = {null, "\u05D9\u05D5\u05B9\u05DD \u05E8\u05D0\u05E9\u05D5\u05B9\u05DF", "\u05D9\u05D5\u05B9\u05DD \u05E9\u05C1\u05E0\u05D9", "\u05D9\u05D5\u05B9\u05DD \u05E9\u05C1\u05DC\u05D9\u05E9\u05C1\u05D9", "\u05D9\u05D5\u05B9\u05DD \u05E8\u05D1\u05D9\u05E2\u05D9", "\u05D9\u05D5\u05B9\u05DD \u05D7\u05DE\u05D9\u05E9\u05C1\u05D9", "\u05D9\u05D5\u05B9\u05DD \u05E9\u05C1\u05E9\u05C1\u05D9", "\u05E9\u05D1\u05EA"};

	private JewishDate JD;

// Test program ====================================================================================

	public static void main(String[] args) {
		JewishDate jd = new JewishDate();
		JewishDateFormat jdf = new JewishDateFormat(jd);
		System.err.println(jdf.format("a: %a\nb: %b\nc: %c\nd: %d\ne: %e\nf: %f\ng: %g\nh: %h\ni: %i\nj: %j\nk: %k\nl: %l\nm: %m\nn: %n"));
	}

	public JewishDateFormat() { this.JD = new JewishDate(); }
	public JewishDateFormat(JewishDate jd) { this.JD = jd; }

	public String format(JewishDate jd, String fmt) {
		this.JD = jd;
		return format(fmt);
	}

	public String format(String fmt) {
		fmt = fmt.replaceAll("%a", Integer.toString(JD.year));
		fmt = fmt.replaceAll("%b", Gematria.getLetters(JD.year, false));
		fmt = fmt.replaceAll("%c", Integer.toString(JD.month));
		fmt = fmt.replaceAll("%d", englishChodeshNames[JD.month - 1]);
		fmt = fmt.replaceAll("%e", hebrewChodeshNames[JD.month - 1]);
		fmt = fmt.replaceAll("%f", Integer.toString(JD.day));
		fmt = fmt.replaceAll("%g", Gematria.getLetters(JD.day));
		fmt = fmt.replaceAll("%h", hebrewWeekdayNames[JD.dayOfWeek()]);
		fmt = fmt.replaceAll("%i", englishWeekdayNames[JD.dayOfWeek()]);
		fmt = fmt.replaceAll("%j", Integer.toString(JD.dayOfWeek()));
		fmt = fmt.replaceAll("%k", Integer.toString(JD.englishYear));
		fmt = fmt.replaceAll("%l", Integer.toString(JD.englishMonth));
		fmt = fmt.replaceAll("%m", englishMonthNames[JD.englishMonth - 1]);
		fmt = fmt.replaceAll("%n", Integer.toString(JD.englishDay));
		return fmt;
	}

}
