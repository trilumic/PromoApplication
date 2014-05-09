package ch.djsimeon.android.promoapplication.app.xmlStuff;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ebruma on 09.05.14.
 */
public class TourDatesEventHandler extends DefaultHandler {
    ArrayList<TourDatesEvent> tourDatesEvents = new ArrayList<TourDatesEvent>();
    String startedElement;
    TourDatesEvent objectInCreation;

    public ArrayList<TourDatesEvent> getTourDates() {
        return tourDatesEvents;
    }
    @Override
    public void startDocument() throws SAXException {
        //
    }

    @Override
    public void endDocument() throws SAXException {
        //
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        startedElement = localName;

        if(localName.equals(TourDatesEvent.EVENT)) {
            objectInCreation = new TourDatesEvent();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equals(TourDatesEvent.EVENT)){
            tourDatesEvents.add(objectInCreation);
        }
        startedElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        Log.d("debug", new String(ch, start, length));
        if(startedElement.equals(TourDatesEvent.TIME)) {
            String chars = new String(ch, start, length);
            chars = chars.trim();

            objectInCreation.time = chars;
        } else if(startedElement.equals(TourDatesEvent.LOCATION)) {
            String chars = new String(ch, start, length);
            chars = chars.trim();

            objectInCreation.location = chars;
        } else if(startedElement.equals(TourDatesEvent.NAME)) {
            String chars = new String(ch, start, length);
            chars = chars.trim();

            objectInCreation.name = chars;
        } else if(startedElement.equals((TourDatesEvent.DESCRIPTION))) {
            String chars = new String(ch, start, length);
            chars = chars.trim();

            objectInCreation.description = chars;
        }
    }
}
