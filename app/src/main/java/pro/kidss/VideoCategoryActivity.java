package pro.kidss;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VideoCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button accept;
    TextView messageTv, titleTv, timer;
    ImageView close;
    Dialog dialog1;
    GridLayoutManager gridLayoutManager;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> Type = new ArrayList<String>();
    ProgressDialog progressDialog;
    FloatingActionButton fabremove;
    RecyclerviewImage dataAdapter;
    private SwipeRefreshLayout swpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        dialog1 = new Dialog( this );
        setContentView( R.layout.activity_video_category );
        swpref = (SwipeRefreshLayout) findViewById( R.id.swpref );
        swpref.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity( getIntent() );
                swpref.setRefreshing( false );
            }
        } );
        loadvideo();
    }
//    File folder = new File( Environment.getExternalStorageDirectory()+
//            File.separator+"folderparent");
//    boolean success = true;
//    if (!folder.exists()) {
//                success = folder.mkdirs();
//            }
//    if (success) {
//                // Do something on success
//    } else {
//                // Do something else on failure
//            }

    public void loadvideo() {
        ShowDialog();
        //progressDialog = ProgressDialog.show(VideoCategoryActivity.this, "please wait", "connecting to server...", true);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/video-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e( "Ex32123", response );
                        try {
                            dialog1.dismiss();
                            JSONObject vidobject = new JSONObject( response );
                            if (vidobject.has( "token" )) {
                                JSONArray viduri = vidobject.getJSONArray( "VideoAddress" );
                                JSONArray Typearray = vidobject.getJSONArray( "Type" );
                                int i = 0;
                                while (i < viduri.length()) {
                                    if (!Type.contains( Typearray.getString( i ) )) {
                                        Type.add( Typearray.getString( i ) );
                                        imageUrlList.add( "https://im.kidsguard.ml" + viduri.getString( i ) );
                                        //dating.add("");
                                        ids.add( "" );
                                    }
                                    i++;
                                }
//                                JSONArray datearray=vidobject.getJSONArray("Date");
//                                int b=0;
//                                while (b<datearray.length()){
//                                    String[] all=datearray.getString(b).split("T");
//                                    String[] date=all[0].split("-");
//                                    int year= Integer.parseInt(date[0]);
//                                    int mounth= Integer.parseInt(date[1]);
//                                    int day= Integer.parseInt(date[2]);
//                                    String[] time=all[1].split(":");
//                                    int hour= Integer.parseInt(time[0]);
//                                    int min= Integer.parseInt(time[1]);
//                                    Calendar mCalendar = new GregorianCalendar();
//                                    mCalendar.set(year,mounth,day,hour,min,00);
//                                    Calendar.Builder calendar=new Calendar.Builder();
//                                    calendar.setDate(year,mounth-1,day);
//                                    calendar.setTimeOfDay(hour,min,0);
//                                    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//                                    DateConverter converter = new DateConverter();
//                                    converter.gregorianToPersian(calendar.build().get(Calendar.YEAR), calendar.build().get(Calendar.MONTH)+1, calendar.build().get(Calendar.DAY_OF_MONTH));
//                                    Log.e("fuuuuuuuuuuuuuuukitime", String.valueOf(calendar.build().getTime()));
//                                    Log.e("fuuuuuuuuuuuuuuukit",converter.getYear()+"/"+converter.getMonth()+"/"+converter.getDay());
//                                    dating.add(String.valueOf(converter.getYear()+"/"+converter.getMonth()+"/"+converter.getDay()+"\n"+calendar.build().getTime().getHours()+":"+calendar.build().getTime().getMinutes()+":"+calendar.build().getTime().getSeconds()));
//                                    b++;
//                                }
                                recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
                                gridLayoutManager = new GridLayoutManager( getApplicationContext(), 2 );
                                recyclerView.setLayoutManager( gridLayoutManager );
                                dataAdapter = new RecyclerviewImage( imageUrlList, VideoCategoryActivity.this, "vidcate", fabremove, ids, Type );
                                recyclerView.setAdapter( dataAdapter );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog1.dismiss();
                            Showtry();
                            //Alert.shows(VideoCategoryActivity.this,"","please check the connection","ok","");
                            SendEror.sender( VideoCategoryActivity.this, e.toString() );
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Showtry();
                //Alert.shows(VideoCategoryActivity.this,"","please check the connection","ok","");
                SendEror.sender( VideoCategoryActivity.this, error.toString() );
            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "token", getctoken( VideoCategoryActivity.this ) );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( VideoCategoryActivity.this );
        requestQueue.add( stringRequest );
    }

    private void Showtry() {
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

    private void ShowDialog() {

        dialog1.setContentView( R.layout.alert_wait );
        close = (ImageView) dialog1.findViewById( R.id.close_accept );
        accept = (Button) dialog1.findViewById( R.id.btnAccept );
        timer = (TextView) dialog1.findViewById( R.id.text_timer );
        titleTv = (TextView) dialog1.findViewById( R.id.title_go );
        messageTv = (TextView) dialog1.findViewById( R.id.messaage_acceot );
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

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }
}
