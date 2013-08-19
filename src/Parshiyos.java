import java.util.*;

public class Parshiyos {
	private static String[] parshaNamesEnglish = {"Ha'azinu", "Bereshis", "Noach", "Lech Lecha", "Vayeira", "Chayei Sarah", "Toldos", "Vayeitzei", "Vayishlach", "Vayeishev", "Miketz", "Vayigash", "Vayechi", "Sh'mos", "Vaeira", "Bo", "Beshalach", "Yisro", "Mishpatim", "Trumah", "Tetzaveh", "Ki Sisa", "Vayakhel", "Pekudei", "Vayikra", "Tzav", "Shmini", "Tazria", "Metzora", "Achrei Mos", "Kedoshim", "Emor", "B'har", "Bechokosai", "Bamidbar", "Nasso", "Beha'aloscha", "Shlach", "Korach", "Chukas", "Balak", "Pinchas", "Mattos", "Masei", "Devarim", "Va'eschanan", "Eikev", "Re'eh", "Shoftim", "Ki Seitzei", "Ki Savo", "Nitzavim"};
	private static String[] parshaNamesHebrew = {"\u05D4\u05D0\u05D6\u05D9\u05E0\u05D5", "\u05D1\u05E8\u05D0\u05E9\u05D9\u05EA", "\u05E0\u05D7", "\u05DC\u05DA \u05DC\u05DA", "\u05D5\u05D9\u05E8\u05D0", "\u05D7\u05D9\u05D9 \u05E9\u05E8\u05D4", "\u05EA\u05D5\u05DC\u05D3\u05D5\u05EA", "\u05D5\u05D9\u05E6\u05D0", "\u05D5\u05D9\u05E9\u05DC\u05D7", "\u05D5\u05D9\u05E9\u05D1", "\u05DE\u05E7\u05E5", "\u05D5\u05D9\u05D2\u05E9", "\u05D5\u05D9\u05D7\u05D9", "\u05E9\u05DE\u05D5\u05EA", "\u05D5\u05D0\u05E8\u05D0", "\u05D1\u05D0", "\u05D1\u05E9\u05DC\u05D7", "\u05D9\u05EA\u05E8\u05D5", "\u05DE\u05E9\u05E4\u05D8\u05D9\u05DD", "\u05EA\u05E8\u05D5\u05DE\u05D4", "\u05EA\u05E6\u05D5\u05D4", "\u05DB\u05D9 \u05EA\u05E9\u05D0", "\u05D5\u05D9\u05E7\u05D4\u05DC", "\u05E4\u05E7\u05D5\u05D3\u05D9", "\u05D5\u05D9\u05E7\u05E8\u05D0", "\u05E6\u05D5", "\u05E9\u05DE\u05D9\u05E0\u05D9", "\u05EA\u05D6\u05E8\u05D9\u05E2", "\u05DE\u05E6\u05D5\u05E8\u05E2", "\u05D0\u05D7\u05E8\u05D9 \u05DE\u05D5\u05EA", "\u05E7\u05D3\u05D5\u05E9\u05D9\u05DD", "\u05D0\u05DE\u05D5\u05E8", "\u05D1\u05D4\u05E8", "\u05D1\u05D7\u05D5\u05E7\u05D5\u05EA\u05D9", "\u05D1\u05DE\u05D3\u05D1\u05E8", "\u05E0\u05E1\u05E2", "\u05D1\u05D4\u05E2\u05DC\u05D5\u05EA\u05DA", "\u05E9\u05DC\u05D7", "\u05E7\u05E8\u05D7", "\u05D7\u05D5\u05E7\u05EA", "\u05D1\u05DC\u05E7", "\u05E4\u05D9\u05E0\u05D7\u05E1", "\u05DE\u05D8\u05D5\u05EA", "\u05DE\u05E1\u05E2\u05D9", "\u05D3\u05D1\u05E8\u05D9\u05DD", "\u05D5\u05D0\u05EA\u05D7\u05E0\u05DF", "\u05E2\u05E7\u05D1", "\u05E8\u05D0\u05D4", "\u05E9\u05D5\u05E4\u05D8\u05D9\u05DD", "\u05DB\u05D9 \u05EA\u05E6\u05D0", "\u05DB\u05D9 \u05EA\u05D1\u05D0", "\u05E0\u05E6\u05D1\u05D9\u05DD"};

	Hashtable h;

	public static void main(String[] args) {
		Parshiyos p = new Parshiyos();
		if(!p.parshaThisWeek()) System.out.println("No parsha is read this week.");
		System.err.println("The next parsha we read is: " + p.nextParsha());
		System.err.println("We read it on: " + (p.nextParshaDate()).format("%i %d %f (%k/%l/%n)"));

/*
		// DEBUG
		System.out.println("Parshiyos for "+year);
		// Loop through every day of the year
		for(	int b = JewishDate.HebrewToAbsolute(JewishDate.TISHREI, 1, year);
				b <= JewishDate.HebrewToAbsolute(JewishDate.ELUL, 29, year);
				b++) {

			jd = new JewishDate(b);

			// if it's shabbos, and we have a parsha for that week
			if(jd.dayOfWeek() == 7 && p.getParsha(jd) != null)
				System.out.println(jd + " : " + p.getParsha(jd));
		}
*/

	}

	public Parshiyos() { this(new JewishDate(), ENGLISH); }

	public Parshiyos(JewishDate jd, int language) {
		int year = jd.year();
		String[] a = calculateParshiyos(year, language);

		int p = 0;
		h = new Hashtable();

		for(	int b = JewishDate.HebrewToAbsolute(JewishDate.TISHREI, 1, year);
				b <= JewishDate.HebrewToAbsolute(JewishDate.ELUL, 29, year);
				b++) {

			JewishDate jd2 = new JewishDate(b);
			if(jd2.dayOfWeek() == 7)  {

				switch (jd2.month()) {
					case JewishDate.TISHREI:
						switch (jd2.day()) {
							case 1: case 2: case 10: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23: break;
							default: h.put(new Integer(jd2.weekOfYear()), a[p++]); break;
						}
					break;
					case JewishDate.NISSAN:
						switch (jd2.day()) {
							case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: break;
							default: h.put(new Integer(jd2.weekOfYear()), a[p++]); break;
						}
					break;
					case JewishDate.SIVAN:
						switch (jd2.day()) {
							case 6: case 7: break;
							default: h.put(new Integer(jd2.weekOfYear()), a[p++]); break;
						}
					break;

					default: h.put(new Integer(jd2.weekOfYear()), a[p++]); break;
				}
			}
		}
	}

	public String nextParsha() { return nextParsha(new JewishDate()); }
	public JewishDate nextParshaDate() { return nextParshaDate(new JewishDate()); }

	public boolean parshaThisWeek() { return parshaThisWeek(new JewishDate()); }

	public boolean parshaThisWeek(JewishDate jd) {
		jd = jd.next(JewishDate.SHABBOS);
		if(getParsha(jd) == null)
			return false;
		return true;
	}

	public JewishDate nextParshaDate(JewishDate jd) {
		jd = jd.next(JewishDate.SHABBOS);
		while(getParsha(jd) == null) { jd = jd.add(7); }
		return jd;
	}

	public String nextParsha(JewishDate jd) {
		jd = jd.next(JewishDate.SHABBOS);
		while(getParsha(jd) == null) { jd = jd.add(7); }
		return getParsha(jd);
	}

	public String getParsha(JewishDate jd) {
		Integer i = new Integer(jd.weekOfYear());
		if(h.containsKey(i))
			return (String)h.get(i);
		else
			return null;
	}

	public String[] calculateParshiyos(int year, int language) {
		String[] parshaNames;
		if(language == ENGLISH)
			parshaNames = parshaNamesEnglish;
		else
			parshaNames = parshaNamesHebrew;

		Vector fullList = new Vector();

		for(int x = 0; x < parshaNames.length; x++)
			fullList.addElement(parshaNames[x]);

		Enumeration e = fullList.elements();

		String answer[];
		JewishDate RoshHashana = new JewishDate(JewishDate.TISHREI, 1, year);
		JewishDate Shavuos = new JewishDate(JewishDate.SIVAN, 6, year);
		JewishDate NextRoshHashana = new JewishDate(JewishDate.TISHREI, 1, year + 1);
		Vector parshiyos = new Vector();

		switch(RoshHashana.dayOfWeek()) {
			case JewishDate.MONDAY:
			case JewishDate.TUESDAY:
				if(language == ENGLISH)
					parshiyos.addElement("Vayeilech");
				else
					parshiyos.addElement("\u05D5\u05D9\u05DC\u05DA");
				break;
		}

		if(JewishDate.HebrewLeapYear(year)) {
			while(e.hasMoreElements()) parshiyos.addElement(e.nextElement());
		} else { 
			// Don't double any of the first 22 (21 + Haazinu)
			for(int x = 0; x < 22; x++)
				parshiyos.addElement(e.nextElement());
			
			// Separate Vayakhel & Pekudei when there is an extra Shabbos
			if(RoshHashana.dayOfWeek() == JewishDate.THURSDAY && JewishDate.isShlemah(year)) {
				parshiyos.addElement(e.nextElement());
				parshiyos.addElement(e.nextElement());
			} else {
				parshiyos.addElement((String)e.nextElement() +"/"+ (String)e.nextElement());
			}

			// The next 3 don't combine
			for(int x=0; x < 3; x++)
				parshiyos.addElement(e.nextElement());

			// Separate Tazria/Metzora & Acharei/Kedoshim in a Leap-Year
			parshiyos.addElement((String)e.nextElement() +"/"+ (String)e.nextElement());
			parshiyos.addElement((String)e.nextElement() +"/"+ (String)e.nextElement());

			// Emor
			parshiyos.addElement(e.nextElement());

			// Behar/Bechukosai
			parshiyos.addElement((String)e.nextElement() +"/"+ (String)e.nextElement());

			// Next 5 (Bamidbar-Korach)
			for(int x=0; x < 5; x++)
				parshiyos.addElement(e.nextElement());

			// Chukas/Balak
			switch(Shavuos.dayOfWeek()) {
				case JewishDate.SUNDAY:
				case JewishDate.MONDAY:
				case JewishDate.WEDNESDAY:
					for(int x=0; x < 3; x++)
						parshiyos.addElement(e.nextElement());
					parshiyos.addElement((String)e.nextElement() +"/"+ (String)e.nextElement());
					break;
				case JewishDate.FRIDAY:
					parshiyos.addElement((String)e.nextElement() +"/"+ (String)e.nextElement());
					parshiyos.addElement(e.nextElement());
					parshiyos.addElement((String)e.nextElement() +"/"+ (String)e.nextElement());
					break;
			}

			// finish off the rest
			while(e.hasMoreElements())
				parshiyos.addElement(e.nextElement());

			switch(NextRoshHashana.dayOfWeek()) {
				case JewishDate.THURSDAY:
				case JewishDate.SATURDAY:
					parshiyos.remove(parshiyos.size() - 1);
					if(language == ENGLISH)
						parshiyos.addElement("Nitzavim/Vayeilech");
					else
						parshiyos.addElement("\u05D5\u05D9\u05DC\u05DA/\u05E0\u05E6\u05D1\u05D9\u05DD");
					break;
			}
		}

		// Make the answer array
		answer = new String[parshiyos.size()];
		for(int x = 0; x < parshiyos.size(); x++)
			answer[x] = (String)parshiyos.elementAt(x);
		
		return answer;
	}

	public static final int HEBREW = 1;
	public static final int ENGLISH = 2;

}
