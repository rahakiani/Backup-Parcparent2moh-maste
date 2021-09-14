package pro.kidss.videoes;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.kidss.Alert;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.database.MsinData;
import pro.kidss.OnvideoDate;
import pro.kidss.R;
import pro.kidss.database.Roomdb;
import pro.kidss.SendEror;

public class RecyclerviewVIDGAL extends RecyclerView.Adapter<RecyclerviewVIDGAL.ViewHolder> {
    ArrayList<String> imageUrls;

    Context context;


    ArrayList<String> removeList = new ArrayList<String>();

    FloatingActionButton removefab;

    Button accept;
    String typee;
    TextView messageTv;
    ImageView close;
    Dialog dialog1;


    List<MsinData> all;
    Roomdb roomdb;
    MsinData msinData;
    OnvideoDate vidodate;

    public RecyclerviewVIDGAL(Context context, List<MsinData> all,OnvideoDate vidodate) {

        this.context = context;
        this.all = all;
        this.vidodate=vidodate;
    }

    @NonNull
    @Override
    public RecyclerviewVIDGAL.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.image_card_view, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewVIDGAL.ViewHolder viewHolder, int i) {

        roomdb = Roomdb.getInstance( context );
        msinData = all.get(i);
        String date = msinData.getDate();
        String time = msinData.getTime();
        String addresss = msinData.getAddress();

        viewHolder.txtdate.setText( date );
        viewHolder.txttime.setText( time );
        viewHolder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();


                CtokenDataBaseManager ctokenDataBaseManager = new CtokenDataBaseManager( context );
                try {
                    jsonObject.put( "token", ctokenDataBaseManager.getctoken() );
                    jsonObject.put( "videoUrl", addresss );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //send to server
                Log.e( "fuuuuuuuuuuuuuuukit", jsonObject.toString() );
                AlertDialog.Builder alertClose = new AlertDialog.Builder( context );
                alertClose.setMessage( "Do you want to delete the videos?" )
                        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //send
                                StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/delete-video/",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.e( "RESPONSE", response );

                                                Toast.makeText( context, "Successfully removed", Toast.LENGTH_SHORT ).show();


                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ShowTry();
                                        Alert.shows( context, "", "please check the connection", "ok", "" );
                                        SendEror.sender( context, error.toString() );
                                    }

                                } ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put( "data", jsonObject.toString() );
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue( context );
                                requestQueue.add( stringRequest );


                            }
                        } ).setNegativeButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                } ).show();

            }


        } );
        viewHolder.like.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomdb.mainDao().like( addresss );
                Log.e( "ADDLIKE", roomdb.mainDao().getall().toString() );


                viewHolder.like.setImageResource( R.drawable.ic_saved );


            }
        } );
        if (roomdb.mainDao().checklike( addresss ) == 1) {
            viewHolder.like.setImageResource( R.drawable.ic_saved );
            Log.e( "LIKE", roomdb.mainDao().getall().toString() );


        } else {
            viewHolder.like.setImageResource( R.drawable.ic_savent );
        }
        if (roomdb.mainDao().checkdown( addresss ) == 1) {
            viewHolder.download.setImageResource( R.drawable.ic_done );
            viewHolder.download.setEnabled( false );

        } else {
            viewHolder.download.setImageResource( R.drawable.ic_download );
        }
        long thumb = i * 1000;
        RequestOptions options = new RequestOptions().frame( thumb );
        Glide.with( context ).load( addresss ).apply( options ).into( viewHolder.img );
        viewHolder.download.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission( context, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {


                } else {
                    ActivityCompat.requestPermissions( (Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100 );
                }
                PRDownloader.initialize( context );
                PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                        .setDatabaseEnabled( true )
                        .build();
                PRDownloader.initialize( context, config );


                config = PRDownloaderConfig.newBuilder()
                        .setReadTimeout( 30_000 )
                        .setConnectTimeout( 30_000 )
                        .build();
                PRDownloader.initialize( context, config );
                int downloadId = PRDownloader.download( addresss, Environment.getExternalStorageDirectory() + "/" + "parent" + "/", namefail( addresss ) )
                        .build()
                        .setOnStartOrResumeListener( new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {

                            }
                        } )
                        .setOnPauseListener( new OnPauseListener() {
                            @Override
                            public void onPause() {

                            }
                        } )
                        .setOnCancelListener( new OnCancelListener() {
                            @Override
                            public void onCancel() {

                            }
                        } )
                        .setOnProgressListener( new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                viewHolder.download.setVisibility( View.GONE );
                                viewHolder.progress.setVisibility( View.VISIBLE );


                                Log.e( "GGGGGGGG", "AAAAAAAAAA" );
                            }
                        } )
                        .start( new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                roomdb.mainDao().adddown( addresss );
                                viewHolder.progress.setVisibility( View.GONE );
                                viewHolder.download.setImageResource( R.drawable.ic_done );
                                viewHolder.download.setVisibility( View.VISIBLE );
                                viewHolder.download.setClickable( false );

                                Toast.makeText( context, "The file you want was saved in the prent folder ", Toast.LENGTH_SHORT ).show();

                                Log.e( "ABBBBA", "AAAAAAAAAA" );


                            }


                            @Override
                            public void onError(Error error) {
                                viewHolder.progress.setVisibility( View.GONE );

                                Toast.makeText( context, "please check your internet", Toast.LENGTH_SHORT ).show();
                                Log.e( "EDDDDDDDD", error.toString() );


                            }

                        } );


            }


            private String namefail(String url) {
                String filename;
                if (url.endsWith( ".mp4" )) {
                    filename = viewHolder.txtdate.getText() + ".mp4";


                } else if (url.endsWith( ".mew" )) {
                    filename = viewHolder.txtdate.getText() + "kidvideo.mp4";
                } else {
                    filename = viewHolder.txtdate.getText() + ".jpg";
                }
                return filename;
            }


        } );
        if (removeList.contains( addresss )) {
            viewHolder.imgcheck.setVisibility( View.VISIBLE );
        } else {
            viewHolder.imgcheck.setVisibility( View.GONE );
        }
//        viewHolder.img.setOnLongClickListener( new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//
//                if (!removeList.contains( addresss )) {
//                    removeList.add( addresss );
//
//                    viewHolder.imgcheck.setVisibility( View.VISIBLE );
//                } else {
//                    removeList.remove( addresss );
//
//                    viewHolder.imgcheck.setVisibility( View.GONE );
//
//                }
//
//                return true;
//            }
//        } );
        viewHolder.img.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vidodate.onImageClick(addresss);
//                if (roomdb.mainDao().checkdown(addresss) == 1) {
//                    Intent intent = new Intent(context  ,ShowVidActivity.class);
////                    intent.putExtra("status","yes");
//                    intent.putExtra("path",addresss);
//                    context.startActivity(intent);
//                } else {
//
//
//                    if (removeList.size() == 0) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//                            Intent intent = new Intent(context, vidGaleryActivity.class);
//                            intent.putExtra("path", addresss);
////                            intent.putExtra("status","no");
//                            context.startActivity(intent);
//
//                        } else {
//                            goToUrl(addresss, context);
//                        }
//                    } else {
//                        if (!removeList.contains(imageUrls.get(i))) {
//                            removeList.add(imageUrls.get(i));
//
//                            viewHolder.imgcheck.setVisibility(View.VISIBLE);
//                        } else {
//                            removeList.remove(imageUrls.get(i));
//
//                            viewHolder.imgcheck.setVisibility(View.GONE);
//
//                        }
//                        if (removeList.size() == 0) {
//                            removefab.setVisibility(View.GONE);
//                        }
//                        if (removeList.size() == 1) {
//                            removefab.setVisibility(View.VISIBLE);
//                        }
//
//
//                    }
//                }
            }
//
        } );
    }

    private void goToUrl(String url, Context co) {
        Uri uriUrl = Uri.parse( url );
        Intent launchBrowser = new Intent( Intent.ACTION_VIEW, uriUrl );
        co.startActivity( launchBrowser );
    }

    private void ShowTry() {
        dialog1.setContentView( R.layout.try_alert );
        close = (ImageView) dialog1.findViewById( R.id.close_try );
        accept = (Button) dialog1.findViewById( R.id.bt_try );
        messageTv = (TextView) dialog1.findViewById( R.id.messaage_try );
        messageTv.setText( "please check the connection" );
        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        } );

        dialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog1.show();
    }


    @Override
    public int getItemCount() {
        return all.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, imgcheck;
        ImageButton like, download, delete;
        TextView txtdate,txttime;
        ProgressBar progress;
        LinearLayout linearLayout, linew;
        private final View parent_view;

        public ViewHolder(@NonNull View view) {
            super( view );
            linearLayout = view.findViewById( R.id.liner );
            parent_view = view.findViewById( android.R.id.content );
            progress = view.findViewById( R.id.progress_bar );
            like = view.findViewById( R.id.Likee );
            download = view.findViewById( R.id.download );
            delete = view.findViewById( R.id.delete );
            img = view.findViewById( R.id.imageView );
            imgcheck = view.findViewById( R.id.imgcheck );
            txtdate = view.findViewById( R.id.txtdate );
            txttime = view.findViewById( R.id.txttime );
            linew = view.findViewById( R.id.linew );
        }
    }
}
