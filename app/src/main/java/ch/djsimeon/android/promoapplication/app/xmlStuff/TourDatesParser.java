package ch.djsimeon.android.promoapplication.app.xmlStuff;

import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.XMLFormatter;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by ebruma on 09.05.14.
 */
public class TourDatesParser {
    public static ArrayList<TourDatesEvent> parse(InputStream is){
        ArrayList<TourDatesEvent> tourDatesEvents = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();

            TourDatesEventHandler tourDatesEventHandler = new TourDatesEventHandler();

            xmlReader.setContentHandler(tourDatesEventHandler);

            xmlReader.parse(new InputSource(is));

            tourDatesEvents = tourDatesEventHandler.getTourDates();

        } catch(Exception ex) {
            Log.d("XML", "TourDatesParser: parse() failed");
        }

        return tourDatesEvents;
    }
}
