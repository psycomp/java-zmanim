import java.util.Calendar;
public class Molad extends HalachicTime {
	int month;
	int year;
	JewishDate closestDate;

	public Molad(int month, int year) {
		autoReduce(true);
		this.month = month;
		this.year = year;

		JewishDate JD = new JewishDate(month, 1, year);

		int[] conversion = {0,7,8,9,10,11,12,1,2,3,4,5,6,13};
		month = conversion[month];

		HalachicTime machzorim = HalachicTime.getInstance(HalachicTime.MACHZOR);

		int completedMachzorim = year / 19;
		machzorim.multiply(completedMachzorim);

		int remainingYears = year % 19;
	
		HalachicTime years = new HalachicTime();
		HalachicTime leapYears = new HalachicTime();

		for(int x = 1; x < remainingYears; x++) {
			switch(x) {
				case 3:
				case 6:
				case 8:
				case 11:
				case 14:
				case 17:
				case 19: // Will never happen. 19 years left is one machzor.
					leapYears.addLeapYear();
					break;
				default:
					years.addRegularYear();
					break;
			}
		}
		
		if(JewishDate.HebrewLeapYear(year) && month > 6)
			month++;
		if(month == 14)
			month = 7;

		HalachicTime months = new HalachicTime();
		for(int x = 1; x < month; x++)
			months.addMonth();

		set(HalachicTime.getInstance(HalachicTime.ORIGINAL_MOLAD));
		add(machzorim);
		add(years);
		add(leapYears);
		add(months);

		HalachicTime HT = new HalachicTime(this);

		HT = HT.subtract(new HalachicTime(0,6,0));
		while(JD.dayOfWeek() != HT.days) { JD = JD.subtract(1); }

		this.closestDate = JD;
	}

	public Molad(Molad m) {
		this.year = m.year;
		this.month = m.month;
		this.days = m.days;
		this.hours = m.hours;
		this.chalokim = m.chalokim;
		this.autoReduce = m.autoReduce;
		this.closestDate = m.closestDate;
	}

    public String announce() {
		HalachicTime HT = new HalachicTime(this);
		HT.round();

		String[] dayNames = {"Shabbos","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Shabbos"};

		// Since we already cleared out full weeks, make sure there is something to subtract from
		if(HT.days == 0 && HT.hours <= 6) HT.days = 7;
		HT.subtract(new HalachicTime(0,6,0));

		int minutes = HT.chalokim / 18;
		HT.chalokim %= 18;

        StringBuffer output = new StringBuffer();
        output.append(dayNames[HT.days] + ", ");
		if(HT.chalokim > 1)
			output.append(HT.chalokim + " chalokim after ");
		else if(HT.chalokim > 0)
			output.append(HT.chalokim + " chelek after ");

		String suffix="am";
        if(HT.hours > 11)
			suffix = "pm";

        if(HT.hours > 12) HT.hours -= 12;
        if(HT.hours == 0) HT.hours = 12;

        java.text.DecimalFormat df = new java.text.DecimalFormat();
        df.applyPattern("00");
		output.append(HT.hours + ":"+ df.format(minutes) + suffix);
        output.append(", in Yerushalayim.");

        return output.toString();
    }

	public JewishDate getDate() {
		return this.closestDate;

		/*
		JewishDate closestDate = new JewishDate(month, 1, year);
		HalachicTime HT = new HalachicTime(this);

		HT = HT.subtract(new HalachicTime(0,6,0));
		while(closestDate.dayOfWeek() != HT.days) { closestDate = closestDate.subtract(1); }
		
		return closestDate;
		*/
	}

	public String getTime() {
		int minutes = chalokim / 18;
		return("" + hours +":"+ minutes);
	}

	public Calendar getCalendar() {
		JewishDate closestDate = getDate();
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, closestDate.englishYear);
		c.set(Calendar.MONTH, closestDate.englishMonth - 1);
		c.set(Calendar.DATE, closestDate.englishDay);
		c.set(Calendar.HOUR, hours);
		c.set(Calendar.MINUTE, (int)(chalokim / 18));
		return c;
	}

	public static void main(String[] args) {
		for(int month = JewishDate.TISHREI; month<= JewishDate.ADAR; month++) {
			Molad m = new Molad(month, 5770);
			System.out.println(m.announce());
			System.out.println("The Molad was on: " + m.getDate());
			System.out.println();
		}
		for(int month = JewishDate.NISAN; month<= JewishDate.ELUL; month++) {
			Molad m = new Molad(month, 5770);
			System.out.println(m.announce());
			System.out.println("The Molad was on: " + m.getDate());
			System.out.println();
		}
	}
}
