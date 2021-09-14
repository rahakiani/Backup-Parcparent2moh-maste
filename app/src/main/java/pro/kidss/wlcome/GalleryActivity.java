package pro.kidss.wlcome;

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
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

import pro.kidss.OnvideoDate;
import pro.kidss.R;
import pro.kidss.database.Roomdb;
import pro.kidss.database.MsinData;

public class GalleryActivity extends AppCompatActivity implements OnvideoDate {
    RecyclerView recyclerView;
    Roomdb roomdb;
    ArrayList<MsinData> dataList = new ArrayList<>();
    ArrayList<String> dataaddres = new ArrayList<>();
    ArrayList<String> datadate = new ArrayList<>();
    ArrayList<String> datatime = new ArrayList<>();
    ArrayList<String> datatype = new ArrayList<>();
    ArrayList<String> typee = new ArrayList<>();
    GridLayoutManager gridLayoutManager;
    RecyclerviewGallery dataAdapter;
    FloatingActionButton ply;
    DownloadManager downloadManager;
    VideoView videoView;
    Uri videoUri;
    MediaController mediaController;
    View parent_view;
    String date,type;
    FloatingActionButton fabremove;

    MsinData msinData;
    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gallery );
        roomdb = Roomdb.getInstance( this );
        parent_view = findViewById( android.R.id.content );
        ply = findViewById(R.id.bt_play);
        videoView = findViewById(R.id.image);

        int i = 0;

        dialog1 = new Dialog( this );
        ShowAlert();
        dataList.addAll( roomdb.mainDao().getlike() );
        while (i < dataList.size()) {
            msinData = dataList.get( i );
            dataaddres.add( msinData.getAddress() );
            datadate.add( msinData.getDate() );
            datatime.add( msinData.getTime() );
            datatype.add( msinData.getType() );
            date = datadate.get( i );
            type = datatype.get( i );
            typee.add( msinData.getType() );
            Log.e( "DATAADDRESS", dataaddres.toString() );

            videoUri = Uri.parse( Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/kidvideo"+date+type+".mp4");
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/kidvideo.mp4");
            if (file.exists()) {
                file.delete();

            }
            i++;
}
        recyclerView = findViewById( R.id.recycler_item );
        dialog1.dismiss();


        gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
        recyclerView.setLayoutManager( gridLayoutManager );
        dataAdapter = new RecyclerviewGallery( dataList, GalleryActivity.this, dataaddres, datadate,datatime,GalleryActivity.this );
        recyclerView.setAdapter( dataAdapter );
    }


    private void ShowAlert() {
        dialog1.setContentView( R.layout.alert_accept );
        ImageView close = (ImageView) dialog1.findViewById( R.id.close_accept );
        Button accept = (Button) dialog1.findViewById( R.id.btnAccept );
        TextView timer = (TextView) dialog1.findViewById( R.id.text_timer );
        TextView titleTv = (TextView) dialog1.findViewById( R.id.title_go );
        TextView messageTv = (TextView) dialog1.findViewById( R.id.messaage_acceot );

        titleTv.setText( "coming soon" );
        messageTv.setText( "This section will be placed in the next updates of the application" );
        timer.setVisibility( View.GONE );

        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        } );

        dialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog1.show();
    }

    @Override
    public void onImageClick(String videodate) {
        ply.setVisibility(View.GONE);

        if (roomdb.mainDao().checkdown( videodate ) == 1) {


            mediaController = new MediaController( GalleryActivity.this );
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
            request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, "kidvideo"+date+type+".mp4" );
            long donid = downloadManager.enqueue( request );

        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "please wait one minute" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.blue_500 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
//            Toast.makeText( this, "please wait one minute", Toast.LENGTH_SHORT ).show();
            BroadcastReceiver time = new BroadcastReceiver() {
                @RequiresApi(api = 29)
                @Override
                public void onReceive(Context context, Intent intent) {


                    if (mediaController == null) {
                        // create an object of media controller class
                        mediaController = new MediaController( GalleryActivity.this );
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
                            ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
                            (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.green_500 ) );
                            snackBarView.addView( custom_view, 0 );
                            snackbar.show();
//                            Toast.makeText( getApplicationContext(), "Thank You...!!!", Toast.LENGTH_LONG ).show(); // display a toast when an video is completed
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

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please Click On The Videos" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.blue_grey_400 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }
}
