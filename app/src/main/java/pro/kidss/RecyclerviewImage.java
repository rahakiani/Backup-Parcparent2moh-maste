package pro.kidss;

import android.Manifest.permission;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
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

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerviewImage extends RecyclerView.Adapter<RecyclerviewImage.ViewHolder> {
    ArrayList<String> imageUrls;
    ArrayList<String> imageUrlss;
    Context context;

    DownloadManager downloadManager;
    String fileparent;
    int downloadIdOne;
    ArrayList<String> removeList = new ArrayList<String>();
    ArrayList<String> Viddate = new ArrayList<String>();
    FloatingActionButton removefab;
    ArrayList<String> ids, dating;
    ArrayList<MsinData> dataList;

    Button accept;
    String typee;
    TextView messageTv, titleTv, timer;
    ImageView close, imageViewaccept;
    Dialog dialog1;
    LinearLayout layout;
    MsinData msinData;

    Roomdb roomdb;


    public RecyclerviewImage(ArrayList<String> imgurl, Context context, String typee, FloatingActionButton removefab, ArrayList<String> ids, ArrayList<String> dating, ArrayList<MsinData> dataList) {

        this.context = context;
        this.ids = ids;

        this.dating = dating;
        this.dataList = dataList;
        imageUrls = imgurl;
        this.context = context;
        this.typee = typee;
        this.removefab = removefab;
        this.ids = ids;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */
    int x = 0;
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        dialog1 = new Dialog( context );
        roomdb = Roomdb.getInstance( context );
        Log.e( "II", String.valueOf( i ) );
        msinData = dataList.get( i );
        List<String> distincmsindata = roomdb.mainDao().gettyper();
        Log.e( "Distinct", distincmsindata.toString() );
        Log.e( "Distincttttt", distincmsindata.get( x ) );
        String datee = msinData.getDate();
        viewHolder.txtdate.setText( datee );
        String typer = msinData.getType();

        String addres = msinData.getAddress();

        Log.e( "XX", String.valueOf( i ) );


        Log.e( "TTYY", typee );
        if (typee.equals( "Vid" )) {

            Log.e( "MSSINDATA", msinData.toString() );
            Log.e( "aaaa", dataList.toString() );
            Log.e( "adresas", addres );

            viewHolder.delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray();

//                while (ii < dataAdapter.getremovelist().size()) {
//                    jsonArray.put( "/static/" + dataAdapter.getremovelist().get( ii ).split( "/static/" )[1] );
//                    ii++;
//                }
                    CtokenDataBaseManager ctokenDataBaseManager = new CtokenDataBaseManager( context );
                    try {
                        jsonObject.put( "token", ctokenDataBaseManager.getctoken() );
                        jsonObject.put( "videoUrl", imageUrls.get( i ) );
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
//                                                    ColoredSnackbar.success( Snackbar.make(coordinatorLayout , " Successfully removed",
//                                                            Snackbar.LENGTH_SHORT ) )
//                                                            .show();
                                                    Toast.makeText( context, "Successfully removed", Toast.LENGTH_SHORT ).show();
//                                                loadvideo();


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
//
                }


            } );
            viewHolder.like.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    roomdb.mainDao().like( addres );
                    Log.e( "ADDLIKE", roomdb.mainDao().getall().toString() );


                    viewHolder.like.setImageResource( R.drawable.ic_saved );


//                    sqLiteV.Insertjl(Boolean.valueOf( "1" ));
//                    Log.e( "REEE",sqLiteV.toString() );

                }
            } );
            if (roomdb.mainDao().checklike( addres ) == 1) {
                viewHolder.like.setImageResource( R.drawable.ic_saved );
                Log.e( "LIKE", roomdb.mainDao().getall().toString() );


            } else {
                viewHolder.like.setImageResource( R.drawable.ic_savent );
            }
            if (roomdb.mainDao().checkdown( addres ) == 1) {
                viewHolder.download.setImageResource( R.drawable.ic_done );
                viewHolder.download.setEnabled( false );

            } else {
                viewHolder.download.setImageResource( R.drawable.ic_download );
            }
            long thumb = i * 1000;
            RequestOptions options = new RequestOptions().frame( thumb );
            Glide.with( context ).load( addres ).apply( options ).into( viewHolder.img );
            viewHolder.download.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    sqLiteV.Insertdl( Boolean.valueOf( "1" )  );
//                    Log.e( "BEE",sqLiteV.toString() );
//                    Viddate.addAll( sqLiteV.getiv() );
//                    if (!viewHolder.txtdate.toString().equals( Viddate )) {
//                        sqLiteV.Insertiv( viewHolder.txtdate.toString() );
//                        Log.e( "SQLITE", sqLiteV.getiv().toString() );
                    if (ActivityCompat.checkSelfPermission( context, permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {


                    } else {
                        ActivityCompat.requestPermissions( (Activity) context, new String[]{permission.WRITE_EXTERNAL_STORAGE}, 100 );
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
                                    viewHolder.download.setVisibility( View.GONE );
                                    viewHolder.progress.setVisibility( View.VISIBLE );


                                    Log.e( "GGGGGGGG", "AAAAAAAAAA" );
                                }
                            } )
                            .start( new OnDownloadListener() {
                                @Override
                                public void onDownloadComplete() {
                                    roomdb.mainDao().adddown( addres );
                                    viewHolder.progress.setVisibility( View.GONE );
                                    viewHolder.download.setImageResource( R.drawable.ic_done );
                                    viewHolder.download.setVisibility( View.VISIBLE );
                                    viewHolder.download.setClickable( false );
//                                        ColoredSnackbar.success( Snackbar.make(coordinatorLayout , " The File You Want Was Downloaded In The Prent Folder",
//                                                Snackbar.LENGTH_SHORT ) )
//                                                .show();
                                    Toast.makeText( context, "The file you want was saved in the prent folder ", Toast.LENGTH_SHORT ).show();

                                    Log.e( "ABBBBA", "AAAAAAAAAA" );


                                }


//                                private void snackBarIconSuccess() {
//                                    final Snackbar snackbar = Snackbar.make(viewHolder.parent_view, "", Snackbar.LENGTH_SHORT);
//                                    //inflate view
//                                    @SuppressLint("ResourceType") View custom_view = viewHolder.itemView.findViewById(R.layout.snackbar_icon_text);
//
//                                    snackbar.getView().setBackgroundColor( Color.TRANSPARENT);
//                                    Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
//                                    snackBarView.setPadding(0, 0, 0, 0);
//
//                                    ((TextView) custom_view.findViewById(R.id.message)).setText("Your file is downloaded !");
//                                    ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
//                                    (custom_view.findViewById(R.id.parent_view)).setBackgroundColor( context.getResources().getColor(R.color.green_500));
//                                    snackBarView.addView(custom_view, 0);
//                                    snackbar.show();
//                                }


                                @Override
                                public void onError(Error error) {
                                    viewHolder.progress.setVisibility( View.GONE );
//                                        ColoredSnackbar.error( Snackbar.make(coordinatorLayout , " Please Check Your Connection Internet",
//                                                Snackbar.LENGTH_SHORT ) )
//                                                .show();
                                    Toast.makeText( context, "please check your internet", Toast.LENGTH_SHORT ).show();
                                    Log.e( "EDDDDDDDD", error.toString() );


                                }

                            } );


                }


                private String namefail(String url) {
                    String filename = "";
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
            if (removeList.contains( addres )) {
                viewHolder.imgcheck.setVisibility( View.VISIBLE );
            } else {
                viewHolder.imgcheck.setVisibility( View.GONE );
            }
            viewHolder.img.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    //  viewHolder.img.setBackground(ContextCompat.getDrawable(context,R.drawable.backgradiant));
                    if (!removeList.contains( addres )) {
                        removeList.add( addres );
                        //viewHolder.img.setImageResource(R.drawable.backgradiant);
                        viewHolder.imgcheck.setVisibility( View.VISIBLE );
                    } else {
                        removeList.remove( addres );
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(address).apply(options).into(viewHolder.img);
                        viewHolder.imgcheck.setVisibility( View.GONE );

                    }
//                if (removeList.size()==0){
//                    removefab.setVisibility(View.GONE);
//                }
//                if (removeList.size()==1){
//                    removefab.setVisibility(View.VISIBLE);
//                }
                    return true;
                }
        });
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (removeList.size()==0){
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
                    Intent intent=new Intent(context, vidGaleryActivity.class);
                    intent.putExtra( "path", addres );
                    context.startActivity( intent );
//                    RxPermissions rxPermissions=new RxPermissions((FragmentActivity) context);
//                    rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            .subscribe(granted ->{
//                                if (granted){
//
//                                }else {
//                                    Toast.makeText(context, "Sorry, we need permission to do this", Toast.LENGTH_LONG).show();
//                                }
//                            });
                } else {
                    goToUrl( imageUrls.get( i ), context );
                }
                } else {
                    if (!removeList.contains( imageUrls.get( i ) )) {
                        removeList.add( imageUrls.get( i ) );
                        //viewHolder.img.setImageResource(R.drawable.backgradiant);
                        viewHolder.imgcheck.setVisibility( View.VISIBLE );
                    } else {
                        removeList.remove( imageUrls.get( i ) );
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(address).apply(options).into(viewHolder.img);
                        viewHolder.imgcheck.setVisibility( View.GONE );

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
        } else if (typee.equals( "vidcate" ) && (distincmsindata.size() - 1) > x) {

//            viewHolder.linearLayout.setPadding( 10,10,10,10 );


            long thumb = i * 1000;
            RequestOptions options = new RequestOptions().frame( thumb );
            viewHolder.download.setVisibility( View.GONE );
            viewHolder.delete.setVisibility( View.GONE );
            viewHolder.like.setVisibility( View.GONE );

//            viewHolder.linew.setVisibility( View.GONE );
            Log.e( "RE", typer );

            // Glide.with(context).load(address).apply(options).into(viewHolder.img);
//            switch (typer){

//                case "camera":
            if (distincmsindata.get( x ).equals( "camera" )) {
                viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.cam ) );
                viewHolder.txtdate.setText( "Camera" );
            } else if (distincmsindata.get( x ).equals( "com.whatsapp" )) {
                viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.whatsapp ) );
                viewHolder.txtdate.setText( "Whatsapp" );
            } else if (distincmsindata.get( x ).equals( "com.instagram.android" )) {
                viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.instagram ) );
                viewHolder.txtdate.setText( "instagram" );
            } else if (distincmsindata.get( x ).equals( "org.telegram.messenger" )) {
                viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.telegram ) );
                viewHolder.txtdate.setText( "Telegram" );
            } else if (distincmsindata.get( x ).equals( "com.facebook.katana" )) {
                viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_facebook__2_ ) );
                viewHolder.txtdate.setText( "Facebook" );
            } else if (distincmsindata.get( x ).equals( "ir.alibaba" )) {
                viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.alibaba ) );
                viewHolder.txtdate.setText( "علی بابا" );

            } else {
                Glide.with( context ).load( imageUrls.get( i ) ).apply( options ).into( viewHolder.img );
                viewHolder.txtdate.setText( typer );
            }

            viewHolder.img.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( context, VideoDateActivity.class );
                    Log.e( "TYPER", typer );
                    intent.putExtra( "Type", typer );
                    context.startActivity( intent );

                }
            });

        }else if (typee.equals("viddate")) {
            viewHolder.download.setVisibility( View.GONE );
            viewHolder.delete.setVisibility( View.GONE );
            viewHolder.like.setVisibility( View.GONE );
            viewHolder.txtdate.setText( datee.split( ",,::" )[0] );
            long thumb = i * 1000;
            RequestOptions options = new RequestOptions().frame( thumb );
            viewHolder.download.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with( context ).load( imageUrls.get( i ) ).diskCacheStrategy( DiskCacheStrategy.ALL ).apply( options ).into( viewHolder.img );
//                    Toast.makeText( context, "MMMMori", Toast.LENGTH_SHORT ).show();

                }
            } );
            viewHolder.img.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e( "DATEES", datee.split( ",,::" )[0] );
                    Intent intent = new Intent( context, vidGaleryActivity.class );

                    intent.putExtra( "Date", datee );
                    intent.putExtra( "Type", typer );
                    Log.e( "Typeeeclick", typee );

                    context.startActivity( intent );

                }
            });
        }else {
            viewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    if (!removeList.contains(ids.get(i))){
                        removeList.add(ids.get(i));
                        //viewHolder.img.setImageResource(R.drawable.backgradiant);
                        viewHolder.imgcheck.setVisibility(View.VISIBLE);
                    }else {removeList.remove(ids.get(i));
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(address).apply(options).into(viewHolder.img);
                        viewHolder.imgcheck.setVisibility(View.GONE);

                    }
                    if (removeList.size()==0){
                        removefab.setVisibility(View.GONE);
                    }
                    if (removeList.size()==1){
                        removefab.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });
            String img2=imageUrls.get(i).replace("\\n","\n");
            Bitmap bitmap = Utils2.getBitmap(img2);
            viewHolder.img.setImageBitmap(bitmap);

            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (removeList.size()==0){
                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
                            Intent intent=new Intent(context, imageActivity.class);
                            intent.putExtra("image",img2);
                            context.startActivity(intent);
                        }else {
                            goToUrl(ids.get(i),context);}}else {
                        if (!removeList.contains(ids.get(i))){
                            removeList.add(ids.get(i));
                            //viewHolder.img.setImageResource(R.drawable.backgradiant);
                            viewHolder.imgcheck.setVisibility( View.VISIBLE );
                        } else {
                            removeList.remove( ids.get( i ) );
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(address).apply(options).into(viewHolder.img);
                            viewHolder.imgcheck.setVisibility( View.GONE );

                        }


                    }

                }
            } );

        }
        if ((distincmsindata.size() - 1) > x) {
            x++;
        }


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


    private void creatfile() {
        File file = new File( Environment.getExternalStorageDirectory(), fileparent );
        if (file.exists()) {
            Toast.makeText( context, "Directory is already exist", Toast.LENGTH_SHORT ).show();
        } else {
            file.mkdirs();
            if (file.isDirectory()) {
                Toast.makeText( context, "file is creat", Toast.LENGTH_SHORT ).show();

            }
        }
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
        LinearLayout linearLayout, linew;
        private final View parent_view;

        public ViewHolder(View view) {
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
            linew = view.findViewById( R.id.linew );
        }

    }
    public static Bitmap retriveVideoFrameFromVideo(String videoPath)throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)"+ e.getMessage());
        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
    private void goToUrl (String url,Context co) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        co.startActivity(launchBrowser);
    }
    public ArrayList<String> getremovelist(){
        return removeList;
    }
}
