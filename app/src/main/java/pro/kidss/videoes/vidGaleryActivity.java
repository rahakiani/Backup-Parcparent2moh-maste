package pro.kidss.videoes;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.widget.TintableCheckedTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.database.MsinData;
import pro.kidss.voice.MusicUtils;
import pro.kidss.OnvideoDate;
import pro.kidss.R;
import pro.kidss.database.Roomdb;

public class vidGaleryActivity extends AppCompatActivity implements OnvideoDate {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    Dialog dialog1;
    Button accept;

    private AppCompatSeekBar seek_song_progressbar;
    TextView messageTv, titleTv, timer;
    ImageView close;
    DownloadManager downloadManager;
    MediaController mediaController;
    RecyclerviewVIDGAL dataAdapter;
    Intent intent3;
    MsinData msinData;
    RelativeLayout relat;
    ImageView img;
    String type = "",date,time,addresss;
    String datess = "";
    Roomdb roomdb;
    private Handler mHandler = new Handler();
    List<MsinData> all ;
    List<MsinData> vidaddress;
    List<String> datee;
    private MusicUtils utils;
    String fileaddres;
    Uri videoUri;
    ArrayList<MsinData> dataList = new ArrayList<>();
    MediaPlayer mp;
    OnvideoDate videodate;
    int adad;
    VideoView videoView;
    View parent_view;
    private ProgressBar download_progress;
    FloatingActionButton ply;
    LinearLayout linearLayout;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_vid_galery);
        roomdb = Roomdb.getInstance(this);
        relat=findViewById( R.id.relative );
        dataList.addAll(roomdb.mainDao().getallvideo());
        linearLayout = findViewById( R.id.lyt_progress );
        parent_view = findViewById( android.R.id.content );
        img = findViewById( R.id.bt_toggle_text );
        dialog1 = new Dialog(this);
        download_progress = (ProgressBar) findViewById(R.id.song_progressbar);
        videoView = findViewById(R.id.image);


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/kidvideo.mp4");
        if (file.exists()) {
            file.delete();
        }
        ply = findViewById(R.id.bt_play);
        seekBar = findViewById(R.id.seek_bar);

        intent3 = getIntent();

        if (intent3 != null) {
            type = intent3.getStringExtra("Type");
//            datess = intent3.getStringExtra( "Date" ).split( ",,::" )[0];
            datess = intent3.getStringExtra("Date");
//            path = intent3.getStringExtra("path");
            videoUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/kidvideo"+datess+type+".mp4");

        }
        vidaddress = roomdb.mainDao().getaddressss(type, datess);
        Log.e( "ADDDRESS",vidaddress.toString() );



        recyclerView = (RecyclerView) findViewById(R.id.recycler_item);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        Log.e("Typer", type);

//        adad = datee.size();
        Log.e( "gadsfgbadb", String.valueOf( adad ) );
        dataAdapter = new RecyclerviewVIDGAL(vidGaleryActivity.this,vidaddress, vidGaleryActivity.this);
        recyclerView.setAdapter(dataAdapter);
    }



    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }

    @Override
    public void onImageClick(String videodate) {
        relat.setVisibility( View.VISIBLE );

        ply.setVisibility(View.GONE);

        if (roomdb.mainDao().checkdown( videodate ) == 1) {


            mediaController = new MediaController( vidGaleryActivity.this );
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.setVideoURI(videoUri);
            videoView.start();

        }else {
            downloadfile(videodate);
        }



    }

    private void downloadfile(String videodate) {


            Uri uri = Uri.parse( videodate );
            downloadManager = (DownloadManager) getSystemService( DOWNLOAD_SERVICE );
            DownloadManager.Request request = new DownloadManager.Request( uri );
            request.setTitle( "downloading" );
            request.setDescription( "wait" );
            request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS,  "kidvideo"+datess+type+".mp4" );
            long donid = downloadManager.enqueue( request );

        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please Wait One Minute" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_loading );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.blue_500 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
//        Toast.makeText( this, "please wait one minute", Toast.LENGTH_SHORT ).show();
            BroadcastReceiver time = new BroadcastReceiver() {
                @RequiresApi(api = 31)
                @Override
                public void onReceive(Context context, Intent intent) {


                    if (mediaController == null) {
                        // create an object of media controller class
                        mediaController = new MediaController( vidGaleryActivity.this );
                        mediaController.setAnchorView( videoView );

                    }
                    // set the media controller for video view
                    videoView.setMediaController( mediaController );
                    // set the uri for the video view
                    videoView.setVideoURI( videoUri );
                    // start a video
                    videoView.start();

                    roomdb.mainDao().adddown( videodate );

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
            };
            IntentFilter intentFilter = new IntentFilter( DownloadManager.ACTION_DOWNLOAD_COMPLETE );
            this.registerReceiver( time, intentFilter );
        }









    public void play_bt(View view) {
        Snackbarsucess();
    }

    private void Snackbarsucess() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please click on the video" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.blue_grey_400 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }

    public void full(View view) {


    }
}



