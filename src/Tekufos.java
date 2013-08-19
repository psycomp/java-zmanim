import net.sourceforge.zmanim.*;
import net.sourceforge.zmanim.util.*;
import java.util.TimeZone;
public class Tekufos {
	boolean debug = false;
	Molad Tishrei = null, Teves = null, Nisan = null, Tammuz = null;
	Molad Chama = null;
	JewishDate Tal;
	ComplexZmanimCalendar czc = null;

	public Tekufos(int year) {
		init(year);
	}

	public Tekufos(int year, boolean debug) {
		this.debug = debug;
		init(year);
	}

	public static void main(String[] args) {

        int year;
        try { year = Integer.parseInt(args[0]); }
        catch (Exception e) { year = 5740; }

		Tekufos t = new Tekufos(year);
		System.out.println("Tekufas Tishrei "+year+": " + t.Tishrei.announce());
		System.out.println("\t" + t.Tishrei.getDate());

		System.out.println("Start saying Tal: "+t.Tal);

		System.out.println("Tekufas Teves "+year+": " + t.Teves.announce());
		System.out.println("\t" + t.Teves.getDate());

		System.out.println("Tekufas Nisan "+year+": " + t.Nisan.announce());
		System.out.println("\t" + t.Nisan.getDate());

		if(t.Chama != null)
			System.out.println("Bircas HaChama is said at sunrise on "+ t.Chama.getDate());

		System.out.println("Tekufas Tammuz "+year+": " + t.Tammuz.announce());
		System.out.println("\t" + t.Tammuz.getDate());
	}


	private void init(int year) {

		String locationName = "Brooklyn, NY";
		double latitude = 40.604162;
		double longitude = -73.951044;
		double elevation = 0; //optional elevation
		TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
		GeoLocation location = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
		this.czc = new ComplexZmanimCalendar(location);

		JewishDate RH = new JewishDate(JewishDate.TISHREI, 1, year);

		HalachicTime TU = HalachicTime.getInstance(HalachicTime.TEKUFA_UNIT);
		int machzorim = year / 19;
		int cycle = year % 19;
		if(cycle == 0) {
			machzorim--;
			cycle = 19;
		}

		TU.multiply(machzorim);

		HalachicTime OT = HalachicTime.getInstance(HalachicTime.ORIGINAL_TEKUFA);

		TU.add(OT);

		int[] corrections = {513485, 0, 282084, 564168, 80819, 362903, 644987, 161638, 443722, -39627, 242457, 524541, 41192, 323276, 605360, 122011, 404095, 686179, 202830, 539405};
        HalachicTime correction = new HalachicTime();
        correction.set(0,0,corrections[cycle]);

		TU.add(correction);

		Molad MO = new Molad(JewishDate.TISHREI, year);

		Tishrei = new Molad(MO);
		Tishrei.autoReduce(false);
		Tishrei.add(TU);
		Tishrei.closestDate = Tishrei.closestDate.add(TU.days);
		Tishrei.autoReduce(true);
		if(Tishrei.days == 0) Tishrei.days = 7;
		while(Tishrei.closestDate.dayOfWeek() < Tishrei.days) { Tishrei.closestDate = Tishrei.closestDate.add(1); }
		if(Tishrei.hours < 6) Tishrei.closestDate = Tishrei.closestDate.subtract(1);
		if(debug) System.err.println("Tekufas Tishrei, "+year+": " + Tishrei.announce() +" ("+ Tishrei.getDate() + ")");

		Tal = Tishrei.closestDate;
		Tal = Tal.add(59);
		while(MO.closestDate.dayOfWeek() < MO.days) { MO.closestDate = MO.closestDate.add(1); }
		if(debug) System.err.println("Start Saying Tal U'Matar : "+Tal);

		Teves = new Molad(Tishrei);
		Teves.autoReduce(true);
		Teves.add(new HalachicTime(91, 7, 540));
		Teves.closestDate = Teves.closestDate.add(91);
		if(Teves.days == 0) Teves.days = 7;
		while(Teves.closestDate.dayOfWeek() < Teves.days) { Teves.closestDate = Teves.closestDate.add(1); }
		if(Teves.hours < 6) Teves.closestDate = Teves.closestDate.subtract(1);
		if(debug) System.err.println("Tekufas Teves, "+year+" : "+Teves.announce()+" ("+ Teves.getDate() + ")");

		Nisan = new Molad(Teves);
		Nisan.autoReduce(true);
		Nisan.add(new HalachicTime(91, 7, 540));
		Nisan.closestDate = Nisan.closestDate.add(91);
		if(Nisan.days == 0) Nisan.days = 7;
		while(Nisan.closestDate.dayOfWeek() < Nisan.days) { Nisan.closestDate = Nisan.closestDate.add(1); }
		if(Nisan.hours < 6) Nisan.closestDate = Nisan.closestDate.subtract(1);
		if(debug) System.err.println("Tekufas Nisan, "+year+" : "+Nisan.announce()+" ("+ Nisan.getDate() + ")");

		if(year%28==1) {
			Chama = new Molad(Nisan);
			if(debug) System.err.println("Bircas HaChama is said at dawn, on "+Nisan.getDate() + ")");
		}

		Tammuz = new Molad(Nisan);
		Tammuz.autoReduce(true);
		Tammuz.add(new HalachicTime(91, 7, 540));
		Tammuz.closestDate = Tammuz.closestDate.add(91);
		if(Tammuz.days == 0) Tammuz.days = 7;
		while(Tammuz.closestDate.dayOfWeek() < Tammuz.days) { Tammuz.closestDate = Tammuz.closestDate.add(1); }
		if(Tammuz.hours < 6) Tammuz.closestDate = Tammuz.closestDate.subtract(1);
		if(debug) System.err.println("Tekufas Tammuz, "+year+" : "+Tammuz.announce()+" ("+ Tammuz.getDate() + ")");
	}
}

