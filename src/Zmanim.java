import net.sourceforge.zmanim.*;
import net.sourceforge.zmanim.util.*;
import java.util.TimeZone;
import java.util.Calendar;
public class Zmanim {

	public static final String locationName = "Brooklyn, NY";
	public static final String timeZoneName = "America/New_York";
	public static final double latitude = 40.604162;
	public static final double longitude = -73.951044;
	public static final double elevation = 0;
	
	public static ZmanimCalendar getInstance() {
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneName);
		GeoLocation location = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
		ZmanimCalendar ZC = new ZmanimCalendar(location);
		return ZC;
	}
	
	public static ZmanimCalendar getInstance(Calendar c) {
		ZmanimCalendar ZC = getInstance();
		ZC.setCalendar(c);
		return ZC;
	}
	
	public static ZmanimCalendar getInstance(int y, int m, int d) {
		ZmanimCalendar ZC = getInstance();
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, y);
		c.set(Calendar.MONTH, m);
		c.set(Calendar.DAY_OF_MONTH, d);
System.err.println(c);
		ZC.setCalendar(c);
		return ZC;
	}

	public static void main(String[] args) {
        // System.out.println("Today's Zmanim for " + locationName);
		ZmanimCalendar ZC = Zmanim.getInstance();
        System.out.println("Sunrise: " + ZC.getSunrise()); //output sunrise
        System.out.println("Sof Zman Shema GRA: " + ZC.getSofZmanShmaGRA()); //output Sof Zman Shema GRA
        System.out.println("Sunset: " + ZC.getSunset()); //output sunset
	}
}
