package pro.kidss.voice;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.DateConverter;
import pro.kidss.R;
import pro.kidss.SendEror;

public class Player extends AppCompatActivity {
    RecyclerView recyclerView;
    Button accept;
    TextView messageTv, titleTv, timer;
    ImageView close;
    ArrayList<String> voiceurl = new ArrayList<String>();
    ArrayList<String> voiceNmae = new ArrayList<String>();
    private VoiceAdapter adapter;
    Dialog dialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.player_activity );
        dialog1 = new Dialog( this );
        recyclerView = findViewById( R.id.player_music );
        jsonparse();


    }

    private void jsonparse() {
        ShowDialog();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/voice-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e( "onResponse", response );
                        dialog1.dismiss();
                        JSONObject alljs = null;
                        try {
                            alljs = new JSONObject( response );
                            if (alljs.has( "token" )) {
                                JSONArray jsonArray = alljs.getJSONArray( "VideoAddress" );
                                int i = 0;
                                while (i < jsonArray.length()) {
                                    voiceurl.add( "https://im.kidsguard.ml" + jsonArray.getString( i ) );
                                    i++;
                                }
                                JSONArray datearray = alljs.getJSONArray( "Date" );
                                int b = 0;
                                while (b < datearray.length()) {
                                    String[] all = datearray.getString( b ).split( "T" );
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
                                    SimpleDateFormat currentDate = new SimpleDateFormat( "dd-MMMM-yyyy" );


                                    DateConverter converter = new DateConverter();
                                    converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
                                    String categorydate = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() );
                                    String datee = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() + "  " + callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() );
                                    voiceNmae.add( datee );
                                    b++;
                                }


                                setRecycler();
                            }

                        } catch (JSONException e) {
                            Log.e( "onResponse error", e.toString() );
                            SendEror.sender( getApplicationContext(), e.toString() );

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Showtry();
                SendEror.sender( Player.this, error.toString() );
            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "token", getctoken( Player.this ) );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( Player.this );
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
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( Player.this );
        return ctok.getctoken();
    }

    public void setRecycler() {
        try {


            adapter = new VoiceAdapter( voiceurl, voiceNmae, Player.this );
            recyclerView.setAdapter( adapter );
            LayoutAnimationController animation =
                    AnimationUtils.loadLayoutAnimation( Player.this, R.anim.layout_animation_fall_down );
            recyclerView.setLayoutAnimation( animation );
            LinearLayoutManager layoutManager = new LinearLayoutManager( Player.this );
            recyclerView.setLayoutManager( layoutManager );
        } catch (Exception e) {
            Log.e( "catch", e.toString() );
        }

    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_menu );
        toolbar.getNavigationIcon().setColorFilter( getResources().getColor( R.color.colorPrimary ), PorterDuff.Mode.SRC_ATOP );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( null );
    }
}