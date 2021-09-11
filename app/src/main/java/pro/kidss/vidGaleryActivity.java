package pro.kidss;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
    String type = "";
    String datess = "";
    Roomdb roomdb;
    private Handler mHandler = new Handler();
    List<MsinData> all;
    List<String> vidaddress;
    private MusicUtils utils;
    String fileaddres;
    Uri videoUri;
    ArrayList<MsinData> dataList = new ArrayList<>();
    MediaPlayer mp;
    OnvideoDate videodate;
    VideoView videoView;
    View parent_view;
    private ProgressBar download_progress;
    FloatingActionButton ply;

    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_galery);
        roomdb = Roomdb.getInstance(this);
        dataList.addAll(roomdb.mainDao().getall());

        parent_view = findViewById( android.R.id.content );
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
        int i = 0;
        while (i < vidaddress.size()) {
            all = roomdb.mainDao().getaall(vidaddress.get(i));

            i++;
        }


//        List<MsinData> all= roomdb.mainDao().getaall(vidaddress.get(i));
        Log.e("Vidadress", vidaddress.toString());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_item);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        Log.e("Typer", type);
        dataAdapter = new RecyclerviewVIDGAL(vidGaleryActivity.this, all, vidGaleryActivity.this);
        recyclerView.setAdapter(dataAdapter);
    }

    private void ShowTry() {
        dialog1.setContentView(R.layout.try_alert);
        close = (ImageView) dialog1.findViewById(R.id.close_try);
        accept = (Button) dialog1.findViewById(R.id.bt_try);
        messageTv = (TextView) dialog1.findViewById(R.id.messaage_try);
        messageTv.setText("please check the connection");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();
    }


    private void ShowDialog() {

        dialog1.setContentView(R.layout.alert_wait);
        close = (ImageView) dialog1.findViewById(R.id.close_accept);
        accept = (Button) dialog1.findViewById(R.id.btnAccept);
        timer = (TextView) dialog1.findViewById(R.id.text_timer);
        titleTv = (TextView) dialog1.findViewById(R.id.title_go);
        messageTv = (TextView) dialog1.findViewById(R.id.messaage_acceot);
        titleTv.setText("Please Wait");
        messageTv.setText("Connecting To Server...");
        long duration = TimeUnit.SECONDS.toMillis(1);
        new CountDownTimer(duration, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format(Locale.ENGLISH, "%02d:%02d"
                        , TimeUnit.MINUTES.toSeconds(0)
                        , TimeUnit.SECONDS.toSeconds(59) -
                                TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toSeconds(1)));
                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                timer.setVisibility(View.GONE);
                accept.setVisibility(View.VISIBLE);


            }
        }.start();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();
    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }

    @Override
    public void onImageClick(String videodate) {
        ply.setVisibility(View.GONE);

        if (roomdb.mainDao().checkdown( videodate ) == 1) {


            mediaController = new MediaController( vidGaleryActivity.this );
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.setVideoURI(videoUri);
            videoView.start();;

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
            request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, "kidvideo"+datess+type+".mp4" );
            long donid = downloadManager.enqueue( request );

        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "please wait one minute" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_loading );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.blue_500 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
//        Toast.makeText( this, "please wait one minute", Toast.LENGTH_SHORT ).show();
            BroadcastReceiver time = new BroadcastReceiver() {
                @RequiresApi(api = 29)
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

    }



