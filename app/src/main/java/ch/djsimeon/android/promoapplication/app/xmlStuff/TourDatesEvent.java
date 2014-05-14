package ch.djsimeon.android.promoapplication.app.xmlStuff;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by ebruma on 09.05.14.
 */
public class TourDatesEvent {
    public String time = "";
    public String location = "";
    public String name = "";
    public String description = "No description";

    public static final String TOURDATES = "tourdates";
    public static final String EVENT = "event";
    public static final String TIME = "time";
    public static final String LOCATION = "location";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    @Override
    public String toString() {
        return time + " " + location + ", " + name;
    }

    public Spanned toHtmlString() {
        return Html.fromHtml("<div style='height:10dp'><strong>"+time+"</strong> <i>"+location+"</i> "+ name + "</div>");
    }
}
