package pro.kidss.file;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.DownloadFileAdapter;
import pro.kidss.R;

public class FileManagerNamayesh extends AppCompatActivity {
    Dialog dialog;
    Button accept;
    TextView messageTv, titleTv, timer;
    ImageView close;
    ImageView imageView;
    TextView textView;
    RecyclerView recyclerViewnama;
    View cardView;
    ArrayList<String> file,fileurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_file_manager_namayesh );
        dialog = new Dialog( this );
        ShowDialog();
        recyclerViewnama = findViewById( R.id.recyclerView_Files );
        file=new ArrayList<String>();
        fileurl=new ArrayList<String>();
      //  CardView cardView = findViewById( R.id.cardview_namayesh );
        String url = "https://im.kidsguard.ml/api/get-selectedFiles/";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //   Toast.makeText(FileManagerNamayesh.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            dialog.dismiss();
                            Log.e("dsrrfdfs", response );
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("FileAddress");
                            int i=0;
                            while (i<jsonArray.length()){
                                String files=jsonObject.getJSONArray("FileAddress").getString(i);
                                file.add(files.split("/")[files.split("/").length-1]);
                                fileurl.add("https://im.kidsguard.ml"+files);
                                i++;
                            }
                            DownloadFileAdapter adapter = new DownloadFileAdapter( FileManagerNamayesh.this,file,fileurl );
                            recyclerViewnama.setAdapter( adapter );

                            LayoutAnimationController animation =
                                    AnimationUtils.loadLayoutAnimation( FileManagerNamayesh.this, R.anim.layout_animation_slide_from_left );
                            recyclerViewnama.setLayoutAnimation( animation );

                            LinearLayoutManager layoutManager = new LinearLayoutManager( FileManagerNamayesh.this );
                            recyclerViewnama.setLayoutManager( layoutManager );


                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(FileManagerNamayesh.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("dsrrfdfs", error.toString() );
                dialog.dismiss();

            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               // params.put( "error", message );
                params.put( "kidToken", getctoken( FileManagerNamayesh.this ) );
              //  params.put( "typeToken", "parent" );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( FileManagerNamayesh.this );
        requestQueue.add( stringRequest );



    }
    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }
    private void ShowDialog() {
        dialog.setContentView( R.layout.alert_wait );
        close = (ImageView) dialog.findViewById( R.id.close_accept );
        accept = (Button) dialog.findViewById( R.id.btnAccept );
        timer = (TextView) dialog.findViewById( R.id.text_timer );
        titleTv = (TextView) dialog.findViewById( R.id.title_go );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_acceot );
        titleTv.setText( "Please Wait" );
        messageTv.setText( "Connecting To Server..." );
        long duration = TimeUnit.SECONDS.toMillis( 1 );
        new CountDownTimer( duration, 100 ) {
            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format( Locale.ENGLISH, "%02d:%02d"
                        , TimeUnit.MINUTES.toSeconds( 0 )
                        , TimeUnit.SECONDS.toSeconds( 59 ) -
                                TimeUnit.SECONDS.toSeconds( TimeUnit.SECONDS.toSeconds( 1 ) ) );
                timer.setText( sDuration );
            }

            @Override
            public void onFinish() {
                timer.setVisibility( View.GONE );
                accept.setVisibility( View.VISIBLE );


            }
        }.start();
        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );

        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog.show();
    }
}