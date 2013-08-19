import net.sourceforge.zmanim.*;
import net.sourceforge.zmanim.util.*;
import java.util.TimeZone;
class SimpleZmanim {
	public static void main(String[] args) {

        String locationName = "Brooklyn, NY";
        double latitude = 40.604162;
        double longitude = -73.951044;
        double elevation = 0;

        //use a Valid Olson Database timezone listed in java.util.TimeZone.getAvailableIDs()
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
        //create the location object
        GeoLocation location = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        //create the ZmanimCalendar
        ZmanimCalendar zc = new ZmanimCalendar(location);
        //optionally set the internal calendar
        //zc.getCalendar().set(1969, Calendar.FEBRUARY, 8);

        System.out.println("Today's Zmanim for " + locationName);
        System.out.println("Sunrise: " + zc.getSunrise()); //output sunrise
        System.out.println("Sof Zman Shema GRA: " + zc.getSofZmanShmaGRA()); //output Sof Zman Shema GRA
        System.out.println("Sunset: " + zc.getSunset()); //output sunset

	}
}
