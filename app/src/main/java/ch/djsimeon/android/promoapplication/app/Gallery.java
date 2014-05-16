package ch.djsimeon.android.promoapplication.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class Gallery extends Activity {


    private ImageSwitcher imageSwitcher;
    private int[] images = {R.drawable.bild1, R.drawable.bild2};
    private int index = 0;

    // Declare the animations and initialize them


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);



        // set the animation type to imageSwitcher


        imageSwitcher.setFactory( new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.
                        MATCH_PARENT,ImageSwitcher.LayoutParams.MATCH_PARENT));
                return myView;
            }
        });
        imageSwitcher.setImageResource(images[index]);
    }

    public void next(View view) {
        index = Math.min(index + 1, images.length-1);
        imageSwitcher.setImageResource(images[index]);
    }

    public void previous(View view) {
        index = Math.max(index - 1, 0);
        imageSwitcher.setImageResource(images[index]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.gallery, menu);

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
//            case R.id.action_gallery:
//                Intent gallery = new Intent(this, Gallery.class);
//                startActivity(gallery);
//                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
