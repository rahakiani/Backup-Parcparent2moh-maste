package pro.kidss.images;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pro.kidss.Alert;
import pro.kidss.R;
import pro.kidss.database.Roomdb;
import pro.kidss.SendEror;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.database.MsinData;

public class RecyclerviewImage extends RecyclerView.Adapter<RecyclerviewImage.ViewHolder> {
    ArrayList<String> imageUrls;

    Context context;

    DownloadManager downloadManager;
    String fileparent;
    int downloadIdOne;
    ArrayList<String> removeList = new ArrayList<String>();
    ArrayList<String> Viddate = new ArrayList<String>();
    FloatingActionButton removefab;
    ArrayList<String> ids, dating,timing;
    ArrayList<MsinData> dataList;

    Button accept;
    String typee;
    TextView messageTv, titleTv, timer;
    ImageView close, imageViewaccept;
    Dialog dialog1;
    LinearLayout layout;
    MsinData msinData;

    Roomdb roomdb;


    public RecyclerviewImage(ArrayList<String> imgurl, Context context, String typee, FloatingActionButton removefab, ArrayList<String> ids, ArrayList<String> dating, ArrayList<MsinData> dataList, ArrayList<String>timing) {

        this.context = context;
        this.ids = ids;

        this.dating = dating;
        this.dataList = dataList;
        this.imageUrls = imgurl;
        this.context = context;
        this.typee = typee;
        this.removefab = removefab;
        this.ids = ids;
        this.timing =timing;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.cardimage, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        dialog1 = new Dialog( context );
//        roomdb = Roomdb.getInstance( context );
//        Log.e( "II", String.valueOf( i ) );
//        msinData = dataList.get( i );
//        List<String> distincmsindata = roomdb.mainDao().gettyper();
//
//        String datee = msinData.getDate();
//        viewHolder.txtdate.setText( datee );
//        String typer = msinData.getType();
//
//        String addres = msinData.getAddress();
//
//        Log.e( "XX", String.valueOf( i ) );
//
//
//        Log.e( "TTYY", typee );
        viewHolder.txttime.setText( timing.get( i ) );
        viewHolder.txtdate.setText( dating.get( i ) );
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
                            intent.putExtra("image",imageUrls);
                            intent.putExtra("date",dating);
                            intent.putExtra("time",timing);
                            intent.putExtra("position",i);
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
            viewHolder.delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jsonObject=new JSONObject();
                    JSONArray jsonArray=new JSONArray();

                    CtokenDataBaseManager ctokenDataBaseManager=new CtokenDataBaseManager(context);
                    try {

                        jsonObject.put("picturesId",jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("fuuuuuuuuuuuuuuukit", jsonObject.toString() );
                    //send to server
                    AlertDialog.Builder alertClose=new AlertDialog.Builder(context);
                    alertClose.setMessage("Do you want to delete the Picture?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //send
                                    StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.ml/api/delete-picture/",
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.e("32122", response );
                                                    try {
                                                        JSONObject j2=new JSONObject(response);
                                                        if (j2.getString("status").equals("ok")){
                                                            Toast.makeText(context, "Successfully removed", Toast.LENGTH_SHORT).show();
//                                                        showgalery();

                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }



                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // progressDialog.dismiss();
                                            Alert.shows(context,"","please check the connection","ok","");
                                            SendEror.sender(context,error.toString());
                                        }

                                    })
                                    {
                                        @Override
                                        protected Map<String, String> getParams(){
                                            Map<String,String> params=new HashMap<String, String>();
                                            params.put("data",jsonObject.toString());
                                            return params;
                                        }
                                    };
                                    RequestQueue requestQueue= Volley.newRequestQueue(context);
                                    requestQueue.add(stringRequest);


                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            dialog1.dismiss();

                        }
                    }).show();


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
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img,imgcheck;
        ImageButton delete;
        TextView txtdate,txttime;
//        ProgressBar progress;

        private final View parent_view;

        public ViewHolder(View view) {
            super( view );

            parent_view = view.findViewById( android.R.id.content );
//            progress = view.findViewById( R.id.progress_bar );
//            like = view.findViewById( R.id.Likee );
//            download = view.findViewById( R.id.download );
//            delete = view.findViewById( R.id.delete );
            img = view.findViewById( R.id.image );
            imgcheck = view.findViewById( R.id.imgcheck );
            delete = view.findViewById( R.id.deletee );
            txtdate = view.findViewById( R.id.name );
            txttime = view.findViewById( R.id.time );
//            linew = view.findViewById( R.id.linew );
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
