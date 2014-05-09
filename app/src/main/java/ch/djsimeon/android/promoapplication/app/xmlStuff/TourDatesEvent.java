package ch.djsimeon.android.promoapplication.app.xmlStuff;

/**
 * Created by ebruma on 09.05.14.
 */
public class TourDatesEvent {
    public String time;
    public String location;
    public String name;

    public static final String TOURDATES = "tourdates";
    public static final String EVENT = "event";
    public static final String TIME = "time";
    public static final String LOCATION = "location";
    public static final String NAME = "name";

    @Override
    public String toString() {
        return time + ", " + location + ", " + name;
    }
}
