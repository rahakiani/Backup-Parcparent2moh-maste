package pro.kidss.voice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import pro.kidss.R;

public class Player_Voice extends AppCompatActivity {
    private View parent_view;
    private FloatingActionButton bt_play;
    private ProgressBar song_progressbar;
    // Media Player
    private MediaPlayer mp;
    TextView textView;
    private int playbackPosition = 0;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();
    ArrayList<String> voicename;
    ArrayList<String> voiceaddres;
    VideoView videoView;
    int position;

    //private SongsManager songManager;
    private MusicUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.playervoice );
        Intent intent = getIntent();

        position = intent.getIntExtra( "Voice_position", 0 );
        voicename = intent.getStringArrayListExtra( "Voice_name" );

        voiceaddres = intent.getStringArrayListExtra( "Voice_address" );
        Log.e( "VOICEADD", voiceaddres.get( position ) );
        Log.e( "VOICENAME", voicename.get( position ) );
        textView = findViewById( R.id.textView15 );
        ;
//        initToolbar();
        initComponent();
    }

    private void initComponent() {
        parent_view = findViewById( R.id.parent_view );
        bt_play = (FloatingActionButton) findViewById( R.id.bt_play );
        song_progressbar = (ProgressBar) findViewById( R.id.song_progressbar );

        // set Progress bar values
        song_progressbar.setProgress( 0 );
        song_progressbar.setMax( MusicUtils.MAX_PROGRESS );

        // Media Player
        mp = new MediaPlayer();
        mp.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Changing button image to play button
                bt_play.setImageResource( R.drawable.ic_play_arrow );
            }
        } );

        try {
            mp.setDataSource( voiceaddres.get( position ) );
            textView.setText( voicename.get( position ) );
            mp.setAudioStreamType( AudioManager.STREAM_MUSIC );
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            Log.e( "ERROR", e.getMessage() );
            Snackbar.make( parent_view, "Cannot load audio file", Snackbar.LENGTH_SHORT ).show();
        }

        utils = new MusicUtils();

        buttonPlayerAction();
    }

    private void updateTimerAndSeekbar() {
        long totalDuration = mp.getDuration();
        long currentDuration = mp.getCurrentPosition();

        // Updating progress bar
        int progress = (int) (utils.getProgressSeekBar( currentDuration, totalDuration ));
        song_progressbar.setProgress( progress );
    }

    // stop player when destroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks( mUpdateTimeTask );
        mp.release();
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            updateTimerAndSeekbar();
            // Running this thread after 10 milliseconds
            if (mp.isPlaying()) {
                mHandler.postDelayed( this, 100 );
            }
        }
    };

    private void buttonPlayerAction() {
        bt_play.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // check for already playing
                if (mp.isPlaying()) {
                    mp.pause();
                    // Changing button image to play button
                    bt_play.setImageResource( R.drawable.ic_play_arrow );
                } else {
                    // Resume song
                    mp.start();
                    // Changing button image to pause button
                    bt_play.setImageResource( R.drawable.ic_pause );
                    mHandler.post( mUpdateTimeTask );
                }

            }
        } );
    }

    @SuppressLint("NonConstantResourceId")
    public void controlClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_prev: {

                if (position == 0) {
                    toggleButtonColor( (ImageButton) v );
                    Snackbar.make( parent_view, "There is no Music", Snackbar.LENGTH_SHORT ).show();
                } else {
                    position--;


                    try {
                        mp.stop();
                        mp.reset();
                        mp.setDataSource( voiceaddres.get( position ) );
                        textView.setText( voicename.get( position ) );
                        mp.prepare();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e( "ERROR", e.getMessage() );
                        Snackbar.make( parent_view, "Cannot load audio file", Snackbar.LENGTH_SHORT ).show();
                    }

                }
                break;

            }
            case R.id.bt_next: {

                if (position > voiceaddres.size()) {

                    toggleButtonColor( (ImageButton) v );
                    Snackbar.make( parent_view, "There is no Music", Snackbar.LENGTH_SHORT ).show();
                } else {
                    position++;
                    try {
                        mp.stop();
                        mp.reset();
                        mp.setDataSource( voiceaddres.get( position ) );
                        textView.setText( voicename.get( position ) );
                        mp.prepare();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e( "ERROR", e.getMessage() );
                        Snackbar.make( parent_view, "Cannot load audio file", Snackbar.LENGTH_SHORT ).show();
                    }
                }
                break;

            }
        }
    }

    private boolean toggleButtonColor(ImageButton bt) {
        String selected = (String) bt.getTag( bt.getId() );
        if (selected != null) { // selected
            bt.setColorFilter( getResources().getColor( R.color.red_500 ), PorterDuff.Mode.SRC_ATOP );
            bt.setTag( bt.getId(), null );
            return false;
        } else {
            bt.setTag( bt.getId(), "selected" );
            bt.setColorFilter( getResources().getColor( R.color.red_500 ), PorterDuff.Mode.SRC_ATOP );
            return true;
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_menu );
        toolbar.getNavigationIcon().setColorFilter( getResources().getColor( R.color.colorPrimary ), PorterDuff.Mode.SRC_ATOP );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( null );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Snackbar.make( parent_view, item.getTitle(), Snackbar.LENGTH_SHORT ).show();
        }
        return super.onOptionsItemSelected( item );
    }
}