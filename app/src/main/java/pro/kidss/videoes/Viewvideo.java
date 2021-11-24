package pro.kidss.videoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;

import pro.kidss.R;

public class Viewvideo extends AppCompatActivity {
VideoView videoView;
String videoUri;
    View parent_view;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_viewvideo );
        videoView =findViewById( R.id.videoview );
        Intent intent= getIntent();
        parent_view = findViewById( android.R.id.content );
        videoUri = intent.getStringExtra( "ADDRES" );
        videoView.setMediaController( mediaController );
        mediaController = new MediaController( Viewvideo.this );
        // set the uri for the video view
        videoView.setVideoURI( Uri.parse( videoUri ) );
        // start a video
        videoView.start();


        // implement on completion listener on video view
        videoView.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {



                final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
                //inflate view
                View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

                snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
                Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
                snackBarView.setPadding( 0, 0, 0, 0 );

                ((TextView) custom_view.findViewById( R.id.message )).setText( "Thank You..." );
                ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_done );
                (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.green_500 ) );
                snackBarView.addView( custom_view, 0 );
                snackbar.show();

            }
        } );
        videoView.setOnErrorListener( new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
                //inflate view
                View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

                snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
                Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
                snackBarView.setPadding( 0, 0, 0, 0 );

                ((TextView) custom_view.findViewById( R.id.message )).setText( "Oops An Error Occur While Playing Video...!!!" );
                ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
                (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.red_500 ) );
                snackBarView.addView( custom_view, 0 );
                snackbar.show();

                return false;
            }
        } );
    }
}