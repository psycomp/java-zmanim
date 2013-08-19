import java.util.*;
public class DafYomi {

	private static String[] masechtaNames = {"Brachos", "Shabbos", "Eruvin", "Pesachim", "Shekalim", "Yoma", "Sukkah", "Beitza", "Rosh HaShana", "Taanis", "Megillah", "Moed Katan", "Chagigah", "Yevamos", "Kesuvos", "Nedarim", "Nazir", "Sotah", "Gittin", "Kiddushin", "Bava Kamma", "Bava Metzia", "Bava Basra", "Sanhedrin", "Makkos", "Shevuos", "Avodah Zarah", "Horayos", "Zevachim", "Menachos", "Chullin", "Bechoros", "Erchin", "Temurah", "Kerisus", "Meilah, etc.", "Niddah"};

	private static String[] masechtaNamesHebrew = {
        "\u05D1\u05BC\u05E8\u05DB\u05D5\u05B9\u05EA",
        "\u05E9\u05C1\u05D1\u05BC\u05EA",
        "\u05E2\u05E8\u05D5\u05BC\u05D1\u05D9\u05DF",
        "\u05E4\u05BC\u05E1\u05D7\u05D9\u05DD",
        "\u05E9\u05C1\u05E7\u05DC\u05D9\u05DD",
        "\u05D9\u05D5\u05BC\u05DE\u05D0",
        "\u05E1\u05BB\u05DB\u05BC\u05D4",
        "\u05D1\u05BC\u05D9\u05E6\u05D4",
        "\u05E8\u05B9\u05D0\u05E9\u05C1 \u05D4\u05E9\u05C1\u05E0\u05D4",
        "\u05EA\u05BC\u05E2\u05E0\u05D9\u05EA",
        "\u05DE\u05D2\u05D9\u05DC\u05D4",
        "\u05DE\u05D5\u05B9\u05E2\u05D3 \u05E7\u05D8\u05DF",
        "\u05D7\u05D2\u05D9\u05D2\u05D4",
        "\u05D9\u05D1\u05DE\u05D5\u05B9\u05EA",
        "\u05DB\u05EA\u05D5\u05BC\u05D1\u05D5\u05B9\u05EA",
        "\u05E0\u05D3\u05E8\u05D9\u05DD",
        "\u05E0\u05D6\u05D9\u05E8",
        "\u05E1\u05D5\u05B9\u05D8\u05D4",
        "\u05D2\u05D9\u05EA\u05BC\u05D9\u05DF",
        "\u05E7\u05D9\u05D3\u05D5\u05BC\u05E9\u05D9\u05DF",
        "\u05D1\u05BC\u05D1\u05D0 \u05E7\u05DE\u05D0",
        "\u05D1\u05BC\u05D1\u05D0 \u05DE\u05E6\u05D9\u05D0\u05D4",
        "\u05D1\u05BC\u05D1\u05D0 \u05D1\u05EA\u05E8\u05D0",
        "\u05E1\u05E0\u05D4\u05D3\u05E8\u05D9\u05DF",
        "\u05DE\u05DB\u05BC\u05D5\u05B9\u05EA",
        "\u05E9\u05C1\u05D1\u05D5\u05BC\u05E2\u05D5\u05B9\u05EA",
        "\u05E2\u05D1\u05D5\u05B9\u05D3\u05D4 \u05D6\u05E8\u05D4",
        "\u05D4\u05B9\u05E8\u05D9\u05D5\u05B9\u05EA",
        "\u05D6\u05D1\u05D7\u05D9\u05DD",
        "\u05DE\u05E0\u05D7\u05D5\u05B9\u05EA",
        "\u05D7\u05D5\u05BC\u05DC\u05D9\u05DF",
        "\u05D1\u05DB\u05D5\u05B9\u05E8\u05D5\u05B9\u05EA",
        "\u05E2\u05E8\u05DB\u05D9\u05DF",
        "\u05EA\u05BC\u05DE\u05D5\u05BC\u05E8\u05D4",
        "\u05DB\u05BC\u05E8\u05D9\u05EA\u05D5\u05BC\u05EA",
        "\u05DE\u05E2\u05D9\u05DC\u05D4",
        "\u05E0\u05D9\u05D3\u05D4"
	};

	private static int[] masechtaSizes = {63, 156, 104, 120, 21, 87, 55, 39, 34, 30, 31, 28, 26, 121, 111, 90, 65, 48, 89, 81, 118, 118, 175, 112, 23, 48, 75, 13, 119, 109, 141, 60, 33, 33, 27, 36, 72};

	public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        JewishDate jd = new JewishDate(c);

		System.out.println(getDaf(jd, HEBREW));
	}

	public static String getDaf(JewishDate jd) { return getDaf(jd, ENGLISH); }

	public static String getDaf(JewishDate jd, int language) {
		int absoluteDate = jd.absolute();
		int dafStart = JewishDate.GregorianToAbsolute(9, 11, 1923);
		int dafChange = JewishDate.GregorianToAbsolute(6, 24, 1975);

		int cycleNumber = 0;
		int dafNumber = 0;

		if(absoluteDate < dafStart) { return null; }
		if(absoluteDate >= dafChange) {
			cycleNumber = ((absoluteDate - dafChange) / 2711) + 8;
			dafNumber = (absoluteDate - dafChange) % 2711 + 1;
		}
		else {
			cycleNumber = ((absoluteDate - dafStart) / 2702) + 1;
			dafNumber = (absoluteDate - dafStart) % 2702;
		}

		// I have no idea why this is done
		if(cycleNumber <= 7)
			masechtaSizes[4] = 13;

		int dafMax = 37;
		int total = 0;
		int count = -1;

		for(int j = 0; j < dafMax; j++) {
			count++;

			if(dafNumber > (total + masechtaSizes[j])) {
				total += masechtaSizes[j];
			} else {
				dafNumber -= total;
				break;
			}
		}
		dafNumber++;

		if(language == ENGLISH)
			return masechtaNames[count] + " " + dafNumber;
		else 
			return masechtaNamesHebrew[count] + " " + Gematria.getLetters(dafNumber, false);
	}

	public static final int ENGLISH = 1;
	public static final int HEBREW = 2;
}
