package ch.djsimeon.android.promoapplication.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.djsimeon.android.promoapplication.app.xmlStuff.TourDatesEvent;
import ch.djsimeon.android.promoapplication.app.xmlStuff.TourDatesParser;

public class Tourdates extends Activity {
    public static String XMLURL = "http://10.0.2.2/tourdates.xml";

//    ArrayList<TourDatesEvent> tourDatesEvents;

    //Adapter for the ListView
    class MyArrayAdapter extends ArrayAdapter<TourDatesEvent>{


        public MyArrayAdapter(Context context, int resource, List<TourDatesEvent> objects) {
            super(context, resource, objects);
        }

        //Will be called for every element in list. In other words: Convert list element to view.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //create View from xml template
            View view = View.inflate(Tourdates.this, R.layout.list_item_event, null);


            //set text of TextView in created view
            TextView tv = (TextView)view.findViewById(R.id.tourdate_text);
            tv.setText(getItem(position).toHtmlString());

            return view;
        }
    }

    private class LoadDataTask extends AsyncTask<Boolean, Void, ArrayList<TourDatesEvent>> {
        boolean loadedFromCache = false;
        boolean loadedFromURL = false;

        private void saveDataInCache(ArrayList<TourDatesEvent> tourDatesEvents) {
            //try to save data in cache
            try {
                ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(getCacheDir(),"cacheFileTourDates")));
                out.writeObject(tourDatesEvents);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private ArrayList<TourDatesEvent> loadDataFromCache() {
            //try to load data from cache
            ArrayList<TourDatesEvent> tourDatesEvents = null;
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(getCacheDir(),"cacheFileTourDates")));
                tourDatesEvents = (ArrayList<TourDatesEvent>) in.readObject();
                in.close();
                loadedFromCache = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return tourDatesEvents;
        }

        private ArrayList<TourDatesEvent> downloadFromURL() {
            ArrayList<TourDatesEvent> tourDatesEvents = null;
            try {
                tourDatesEvents = TourDatesParser.parse(new URL(XMLURL).openStream());
                saveDataInCache(tourDatesEvents);
                loadedFromURL = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tourDatesEvents;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //display loading text only if list is empty
            if (((ListView) findViewById(R.id.tourdates)).getCount() == 0) {
                TextView textView = (TextView) findViewById(R.id.tourdates_text_info);
                textView.setVisibility(View.VISIBLE);
                textView.setText(getResources().getString(R.string.loading));
            }

        }

        boolean tryLoadingFromCache = true;

        @Override
        protected ArrayList<TourDatesEvent> doInBackground(Boolean... params) {
//            //GUI manipulation must be done in GUI Thread
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    //Show loading text
//                    TextView textView = (TextView) findViewById(R.id.tourdates_text_info);
//                    textView.setVisibility(View.VISIBLE);
//                    textView.setText(getResources().getString(R.string.loading));
//                }
//            });

            //One argument is expected. it sets, whether data should be loaded from cache. default is true
            if (params.length == 1) {
                tryLoadingFromCache = (boolean) params[0];
            }

            ArrayList<TourDatesEvent> tourDatesEvents = null;

            if (tryLoadingFromCache) {
                //First, try to load from cache
                tourDatesEvents = loadDataFromCache();
            }

            //if not successful, try to download xml and convert it to an ArrayList
            if (tourDatesEvents == null) tourDatesEvents = downloadFromURL();

//            try {
//                //The method onPostExecute gets this
//                return TourDatesParser.parse(new URL(XMLURL).openStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


//            //try to save data in cache
//            try {
//                ObjectOutput out = null;
//                out = new ObjectOutputStream(new FileOutputStream(new File(getCacheDir(),"")+"cacheFileTourDates.srl"));
//                out.writeObject((ArrayList<TourDatesEvent>) tourDatesEvents);
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            return tourDatesEvents;
        }

        @Override
        protected void onPostExecute(final ArrayList<TourDatesEvent> tourDatesEvents) {
            TextView textView = (TextView) findViewById(R.id.tourdates_text_info);
            if (tourDatesEvents != null) {
                //Remove loading text if download was successfull
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {

                        textView.setVisibility(View.GONE);
//                    }
//                });

                //Show data in update ListView
                updateListView(tourDatesEvents);

            } else {

                //set fail text
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {

                //Nur zeigen, wenn keine List-Items da
                if (((ListView) findViewById(R.id.tourdates)).getCount() == 0) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(getResources().getString(R.string.no_tourdates_loaded));
                }
//                    }
//                });

                //display only when refreshing
                if(!tryLoadingFromCache && ((ListView) findViewById(R.id.tourdates)).getCount() != 0) {
                     Toast.makeText(Tourdates.this, getResources().getString(R.string.refresh_failed),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateListView(final ArrayList<TourDatesEvent> tourDatesEvents) {

            //Create adapter with textview and list
            MyArrayAdapter myArrayAdapter = new MyArrayAdapter(Tourdates.this, R.layout.list_item_event, tourDatesEvents);

            //get ListView and set adapter
            ListView listView = (ListView) findViewById(R.id.tourdates);
            listView.setAdapter(myArrayAdapter);

            //open on click new activity with details
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Tourdates.this, TourDateInfo.class);

                    //transport information to new activity
                    intent.putExtra("itemText", tourDatesEvents.get(i).toHtmlString());
                    intent.putExtra("description", tourDatesEvents.get(i).description);

                    startActivity(intent);
                }
            });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("m","Tourdates created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourdates);



    }

    @Override
    protected void onStart() {
        super.onStart();

//        new File(getCacheDir(),"cacheFileTourDates").delete();

         //try to load data
         new LoadDataTask().execute();




        Log.d("m","Tourdates started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("m","Tourdates resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("m","Tourdates paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("m","Tourdates stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("m","Tourdates destroyed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.tour_date_menu,menu);

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
            case R.id.refresh_tour_dates:
                //false means: don't try to load from cache
                new LoadDataTask().execute(false);
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("message", "Hey, onsaveinstancestate called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("message", "Hey, onRestoreInstanceState called");
    }
}
