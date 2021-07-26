package pro.kidss;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import pro.kidss.R;

public class VideoDateActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button accept;
    TextView messageTv, titleTv, timer;
    ImageView close;
    GridLayoutManager gridLayoutManager;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> dating = new ArrayList<String>();
    ArrayList<String> datecategory = new ArrayList<String>();
    Dialog dialog1;
    ProgressDialog progressDialog;
    ScrollView scrollView;
    FloatingActionButton fabremove, up, down;
    RecyclerviewImage dataAdapter;
    Intent intent3;
    String type = "";
    private SwipeRefreshLayout swpref;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        dialog1 = new Dialog( this );

        setContentView( R.layout.activity_video_date );
        intent3 = getIntent();
//        scrollView = findViewById( R.id.scroll );
//        down = (FloatingActionButton) findViewById( R.id.bt_one );
//        up = (FloatingActionButton) findViewById( R.id.bt_two );
        if (intent3 != null) {

            type = intent3.getStringExtra( "Type" );
        }
        swpref = (SwipeRefreshLayout) findViewById( R.id.swpref );


        swpref.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity( getIntent() );
                swpref.setRefreshing( false );
            }
        } );
//        up.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scrollView.fullScroll( scrollView.FOCUS_UP );
//                up.show();
//            }
//        } );
//        down.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scrollView.fullScroll( scrollView.FOCUS_DOWN );
//                down.show();
//
//            }
//        } );

        loadvideo();

    }

    public void loadvideo() {
        ShowDialog();
//        progressDialog = ProgressDialog.show(VideoDateActivity.this, "please wait", "connecting to server...", true);
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
                                JSONArray Typeaaray = vidobject.getJSONArray( "Type" );
                                JSONArray datearray = vidobject.getJSONArray( "Date" );
                                int i = 0;
                                while (i < viduri.length()) {
                                    if (Typeaaray.get( i ).equals( type )) {
                                        String[] all = datearray.getString( i ).split( "T" );
                                        String[] date = all[0].split( "-" );
                                        int year = Integer.parseInt( date[0] );
                                        int mounth = Integer.parseInt( date[1] );
                                        int day = Integer.parseInt( date[2] );
                                        String[] time = all[1].split( ":" );
                                        int hour = Integer.parseInt( time[0] );
                                        int min = Integer.parseInt( time[1] );
                                        Calendar callForDate = Calendar.getInstance();
                                        callForDate.set( year, mounth, day, hour, min, 00 );
                                        callForDate.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
                                        java.text.SimpleDateFormat currentDate = new java.text.SimpleDateFormat( "dd-MMMM-yyyy" );

//                                        final String saveCurrentDate = currentDate.format(callForDate.getTime());
//                                        Calendar mCalendar = new GregorianCalendar();
//                                        mCalendar.set(year,mounth,day,hour,min,00);
//                                        Calendar.Builder calendar=new Calendar.Builder();
//                                        calendar.setDate(year,mounth-1,day);
//                                        calendar.setTimeOfDay(hour,min,0);
//                                        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                                        DateConverter converter = new DateConverter();
                                        converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
                                        String categorydate = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() );
                                        String datee = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() + "\n" + callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() );

                                        if (!datecategory.contains( categorydate )) {
                                            datecategory.add( categorydate );
                                            imageUrlList.add( "https://im.kidsguard.ml" + viduri.getString( i ) );
                                            //dating.add("");
                                            ids.add( "" );
                                            dating.add( datee + ",,::" + categorydate + ",,::" + type );
                                        }
                                    }
                                    i++;
                                }


                                recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
                                gridLayoutManager = new GridLayoutManager( getApplicationContext(), 2 );
                                recyclerView.setLayoutManager( gridLayoutManager );
                                dataAdapter = new RecyclerviewImage( imageUrlList, VideoDateActivity.this, "viddate", fabremove, ids, dating );
                                recyclerView.setAdapter( dataAdapter );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog1.dismiss();
                            ShowTry();
                            //Alert.shows(VideoDateActivity.this,"","please check the connection","ok","");
                            SendEror.sender( VideoDateActivity.this, e.toString() );
                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                ShowTry();
                //Alert.shows(VideoDateActivity.this,"","please check the connection","ok","");
                SendEror.sender( VideoDateActivity.this, error.toString() );
            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "token", getctoken( VideoDateActivity.this ) );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( VideoDateActivity.this );
        requestQueue.add( stringRequest );
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
