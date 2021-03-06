package ch.djsimeon.android.promoapplication.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
            case  R.id.action_tourdates:
                Intent tour = new Intent(this,Tourdates.class);
                startActivity(tour);
                break;
            case R.id.action_music:
                Intent music = new Intent(this, MusicPlayer.class);
                startActivity(music);
                break;
            case R.id.action_gallery:
                Intent gallery = new Intent(this, Gallery.class);
                startActivity(gallery);
                break;

        }
//        if (id == R.id.action_contact) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

}
