package pro.kidss;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerviewGallery extends RecyclerView.Adapter<RecyclerviewGallery.ViewHolder> {
    ArrayList<MsinData> dataList;
    ArrayList<String> dataaddres;
    ArrayList<String> datadate;
    Context context;
    Roomdb roomdb;
    Button accept;
    TextView messageTv, titleTv, timer;
    ImageView close, imageViewaccept;
    Dialog dialog1;
    LinearLayout layout;
    ArrayList<String> removeList = new ArrayList<String>();
    MsinData msinData;
    FloatingActionButton removefab;
    ArrayList<String> addresss;
    CoordinatorLayout coordinatorLayout;

    public RecyclerviewGallery(ArrayList<MsinData> dataList, Context context, FloatingActionButton removefab, ArrayList<String> dataaddres, CoordinatorLayout coordinatorLayout, ArrayList<String> datadate) {
        this.context = context;
        this.dataaddres = dataaddres;
        this.dataList = dataList;
        this.removefab = removefab;
        this.coordinatorLayout = coordinatorLayout;
        this.datadate = datadate;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerviewGallery.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.image_card_view, parent, false );
        return new RecyclerviewGallery.ViewHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerviewGallery.ViewHolder holder, int position) {
        roomdb = Roomdb.getInstance( context );


        String addres = dataaddres.get( position );

        holder.txtdate.setText( datadate.get( position ) );

        dialog1 = new Dialog( context );

        Log.e( "ADDRES", addres );
        holder.img.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                if (!removeList.contains( addres )) {
                    removeList.add( addres );

                    holder.imgcheck.setVisibility( View.VISIBLE );
                } else {
                    removeList.remove( addres );

                    holder.imgcheck.setVisibility( View.GONE );

                }
                if (removeList.size() == 0) {
                    removefab.setVisibility( View.GONE );
                }
                if (removeList.size() == 1) {
                    removefab.setVisibility( View.VISIBLE );
                }


                return true;
            }
        } );
        holder.img.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (removeList.size() == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        Intent intent = new Intent( context, ShowVidActivity.class );
                        intent.putExtra( "path", addres );
                        Log.e( "LIST", addres );
                        context.startActivity( intent );

                    } else {
                        goToUrl( addres, context );
                    }
                } else {
                    if (!removeList.contains( addres )) {
                        removeList.add( addres );

                        holder.imgcheck.setVisibility( View.VISIBLE );
                    } else {
                        removeList.remove( addres );

                        holder.imgcheck.setVisibility( View.GONE );

                    }
                    if (removeList.size() == 0) {
                        removefab.setVisibility( View.GONE );
                    }
                    if (removeList.size() == 1) {
                        removefab.setVisibility( View.VISIBLE );
                    }


                }
            }

        } );
        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }


            private void ShowDialog() {
                dialog1.setContentView( R.layout.alert_accept );
                layout = (LinearLayout) dialog1.findViewById( R.id.linew );
                close = (ImageView) dialog1.findViewById( R.id.close_accept );
                messageTv = (TextView) dialog1.findViewById( R.id.messaage_acceot );
                accept = (Button) dialog1.findViewById( R.id.btnAccept );
                imageViewaccept = (ImageView) dialog1.findViewById( R.id.image_accept );

                imageViewaccept.setImageResource( R.drawable.ic_question );
                messageTv.setText( "Do you want to delete the videos?" );
                accept.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject jsonObject = new JSONObject();
                        JSONArray jsonArray = new JSONArray();


                        CtokenDataBaseManager ctokenDataBaseManager = new CtokenDataBaseManager( context );
                        try {
                            jsonObject.put( "token", ctokenDataBaseManager.getctoken() );
                            jsonObject.put( "videoUrl", jsonArray );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e( "fuuuuuuuuuuuuuuukit", jsonObject.toString() );

                        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/delete-video/",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText( context, "Successfully removed", Toast.LENGTH_SHORT ).show();
                                        roomdb.mainDao().deletlike( addres );


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                ShowTry();


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
                } );
                close.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                } );


            }

        } );
        if (roomdb.mainDao().checklike( addres ) == 1) {
            holder.like.setImageResource( R.drawable.ic_saved );
            Log.e( "LIKE", roomdb.mainDao().getall().toString() );


        } else {
            holder.like.setImageResource( R.drawable.ic_savent );
        }
        holder.like.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColoredSnackbar.warning( Snackbar.make( coordinatorLayout, " Unarchived ",
                        Snackbar.LENGTH_SHORT ) )
                        .show();
                roomdb.mainDao().deletlike( addres );
                holder.like.setImageResource( R.drawable.ic_savent );

            }
        } );


        if (roomdb.mainDao().checkdown( addres ) == 1) {
            holder.download.setImageResource( R.drawable.ic_done );
            ColoredSnackbar.info( Snackbar.make( coordinatorLayout, " This file is already saved in the pernt folder ",
                    Snackbar.LENGTH_SHORT ) )
                    .show();
            holder.download.setEnabled( false );

        } else {
            holder.download.setImageResource( R.drawable.ic_download );
        }

        long thumb = position * 1000;
        RequestOptions options = new RequestOptions().frame( thumb );
        Glide.with( context ).load( addres ).apply( options ).into( holder.img );
        holder.download.setOnClickListener( new View.OnClickListener() {
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


                // Setting timeout globally for the download network requests:
                config = PRDownloaderConfig.newBuilder()
                        .setReadTimeout( 30_000 )
                        .setConnectTimeout( 30_000 )
                        .build();
                PRDownloader.initialize( context, config );
                int downloadId = PRDownloader.download( addres, Environment.getExternalStorageDirectory() + "/" + "parent" + "/", namefail( addres ) )
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
                                holder.download.setVisibility( View.GONE );
                                holder.progress.setVisibility( View.VISIBLE );


                                Log.e( "GGGGGGGG", "AAAAAAAAAA" );
                            }
                        } )
                        .start( new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                roomdb.mainDao().adddown( addres );
                                holder.progress.setVisibility( View.GONE );
                                holder.download.setImageResource( R.drawable.ic_done );
                                holder.download.setVisibility( View.VISIBLE );
                                holder.download.setClickable( false );
                                ColoredSnackbar.success( Snackbar.make( coordinatorLayout, " The File You Want Was Downloaded In The Prent Folder ",
                                        Snackbar.LENGTH_SHORT ) )
                                        .show();


                                Log.e( "ABBBBA", "AAAAAAAAAA" );


                            }


                            @Override
                            public void onError(Error error) {
                                holder.progress.setVisibility( View.GONE );
                                ColoredSnackbar.error( Snackbar.make( coordinatorLayout, " Please Check Your Connection Internet ",
                                        Snackbar.LENGTH_SHORT ) )
                                        .show();
//                                Toast.makeText( context, "please check your internet", Toast.LENGTH_SHORT ).show();
                                Log.e( "EDDDDDDDD", error.toString() );


                            }

                        } );


            }


            private String namefail(String url) {
                String filename = "";
                if (url.endsWith( ".mp4" )) {
                    filename = holder.txtdate.getText() + ".mp4";


                } else if (url.endsWith( ".mew" )) {
                    filename = holder.txtdate.getText() + ".mp4";
                } else {
                    filename = holder.txtdate.getText() + ".jpg";
                }
                return filename;
            }


        } );


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
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, imgcheck;
        ImageButton like, download, delete;
        TextView txtdate;
        ProgressBar progress;
        private final View parent_view;

        public ViewHolder(View view) {
            super( view );
            parent_view = view.findViewById( android.R.id.content );
            progress = view.findViewById( R.id.progress_bar );
            like = view.findViewById( R.id.Likee );
            download = view.findViewById( R.id.download );
            delete = view.findViewById( R.id.delete );
            img = view.findViewById( R.id.imageView );
            imgcheck = view.findViewById( R.id.imgcheck );
            txtdate = view.findViewById( R.id.txtdate );
        }

    }

    private void goToUrl(String url, Context co) {
        Uri uriUrl = Uri.parse( url );
        Intent launchBrowser = new Intent( Intent.ACTION_VIEW, uriUrl );
        co.startActivity( launchBrowser );
    }
}
