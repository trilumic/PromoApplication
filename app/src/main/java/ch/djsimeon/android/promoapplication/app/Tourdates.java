package ch.djsimeon.android.promoapplication.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.djsimeon.android.promoapplication.app.xmlStuff.TourDatesEvent;
import ch.djsimeon.android.promoapplication.app.xmlStuff.TourDatesParser;

public class Tourdates extends Activity {
    class MyArrayAdapter extends ArrayAdapter<TourDatesEvent>{


        public MyArrayAdapter(Context context, int resource, List<TourDatesEvent> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list_item_event, null);


            //Text
            TextView tv = (TextView)view.findViewById(R.id.tourdate_text);
            tv.setText(getItem(position).toHtmlString());

            return view;
        }
    }

    private class DownloadXMLTask extends AsyncTask<String, Void, ArrayList<TourDatesEvent>> {
        ArrayList<TourDatesEvent> tourDatesEvents;
        @Override
        protected ArrayList<TourDatesEvent> doInBackground(String... params) {
            try {
                tourDatesEvents = TourDatesParser.parse(new URL("http://10.0.2.2/tourdates.xml").openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return tourDatesEvents;
        }

        @Override
        protected void onPostExecute(final ArrayList<TourDatesEvent> tourdates) {
            MyArrayAdapter myArrayAdapter = new MyArrayAdapter(Tourdates.this, R.layout.list_item_event, tourdates);

            ListView listView = (ListView) findViewById(R.id.tourdates);
            listView.setAdapter(myArrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Tourdates.this, TourDateInfo.class);
                    intent.putExtra("text", tourdates.get(i).description );
                    startActivity(intent);
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourdates);


        new DownloadXMLTask().execute();
//        ListView listView = (ListView) findViewById(R.id.tourdates);

//        try {
//            final ArrayList<TourDatesEvent> tourdates;
//            tourdates = TourDatesParser.parse(getAssets().open("tourdates.xml"));
//            tourdates = TourDatesParser.parse(new URL("http://10.0.2.2/tourdates.xml").openStream());

            //ArrayAdapter adapter = new ArrayAdapter<TourDatesEvent>(this,
                   // R.layout.list_item_event, tourdates);
//            MyArrayAdapter myArrayAdapter = new MyArrayAdapter(this, R.layout.list_item_event, tourdates);
//            listView.setAdapter(myArrayAdapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Intent intent = new Intent(Tourdates.this, TourDateInfo.class);
//                    intent.putExtra("text", tourdates.get(i).description );
//                    startActivity(intent);
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //asdf
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_contact:
                Intent contact = new Intent(this,Contact.class);
                startActivity(contact);
                break;
//            case  R.id.action_tourdates:
//                Intent tour = new Intent(this,Tourdates.class);
//                startActivity(tour);
//                break;
//            case R.id.action_music:
//                Intent music = new Intent(this, Music.class);
//                startActivity(music);
//                break;
//            case R.id.action_gallery:
//                Intent gallery = new Intent(this, Gallery.class);
//                startActivity(gallery);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
