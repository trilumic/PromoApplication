package ch.djsimeon.android.promoapplication.app;


import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends Activity {

    public TextView songName,startTimeField,endTimeField;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private ImageButton playButton,pauseButton;
    public static int oneTimeOnly = 0;


    //    public String customFormat(String pattern, double value){
//        DecimalFormat myFormat = new DecimalFormat(pattern);
//        String output = myFormat.format(value);
//        return output;
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        startTimeField =(TextView)findViewById(R.id.timeElapsed);
        endTimeField =(TextView)findViewById(R.id.initial_time);
        seekbar = (SeekBar)findViewById(R.id.seek_slider);
        playButton = (ImageButton)findViewById(R.id.play);
//        pauseButton = (ImageButton)findViewById(R.id.imageButton2);
        mediaPlayer = MediaPlayer.create(this, R.raw.skankout);
        seekbar.setClickable(false);
//        pauseButton.setEnabled(false);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
    }

    public void stop(View view) {
        Toast.makeText(getApplicationContext(), "Stopped",
                Toast.LENGTH_SHORT).show();

        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
    }

    private void setTime() {

    }
    public void play(){
        Toast.makeText(getApplicationContext(), "Playing",
                Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        if(oneTimeOnly == 0){
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        endTimeField.setText(String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) finalTime)))
        );
//        endTimeField.setText(myFormat.format(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)) + " : " +
//                myFormat.format(TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
//                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
//                        toMinutes((long) finalTime)))
//        );



        startTimeField.setText(String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) startTime)))
        );

        seekbar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);

        playButton.setImageResource(android.R.drawable.ic_media_pause);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });
//        pauseButton.setEnabled(true);
//        playButton.setEnabled(false);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            startTimeField.setText(String.format("%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    public void pause(){
        Toast.makeText(getApplicationContext(), "Pausing",
                Toast.LENGTH_SHORT).show();

        mediaPlayer.pause();
//        pauseButton.setEnabled(false);
//        playButton.setEnabled(true);

        playButton.setImageResource(android.R.drawable.ic_media_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
    }

    public void forward(View view){
        int temp = (int)startTime;
        if((temp+forwardTime)<=finalTime){
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Cannot jump forward 5 seconds",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void rewind(View view){
        int temp = (int)startTime;
        if((temp-backwardTime)>0){
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Cannot jump backward 5 seconds",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_player, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            case R.id.action_gallery:
                Intent gallery = new Intent(this, Gallery.class);
                startActivity(gallery);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}