package pro.kidss;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class HomeAcitivity extends AppCompatActivity {
    ProgressDialog dialog = null;
    Intent i;
    Context context;
    FloatingActionButton contacts, sms, calls, voice, photo, video, file, location, albums;
    static final int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_acitivity );
        contacts = findViewById( R.id.contacts_activ );
        file = findViewById( R.id.file_activ );
        calls = findViewById( R.id.calls_activ );
        voice = findViewById( R.id.voice_activ );
        video = findViewById( R.id.video_activ );
        photo = findViewById( R.id.photo_activ );
        sms = findViewById( R.id.sms_activ );
        location = findViewById( R.id.location_activ );
        albums = findViewById( R.id.albums_activ );

        contacts.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), ExplainItemActivity.class );
                intent.putExtra( "IntentName", "Contact Data" );
                startActivity( intent );

            }
        } );
        calls.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), ExplainItemActivity.class );
                intent.putExtra( "IntentName", "Call Data" );
                startActivity( intent );
            }
        } );
        sms.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), ExplainItemActivity.class );
                intent.putExtra( "IntentName", "SMS Data" );
                startActivity( intent );
            }
        } );
        photo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), pictureActivity.class );
                startActivity( intent );
            }
        } );
        location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsoparse();


            }

            private void jsoparse() {
                StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/coordinate-list/",
                        new Response.Listener<String>() {
                            @TargetApi(Build.VERSION_CODES.O)
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(String response) {

                                dialog.dismiss();
                                try {
                                    JSONObject jsoncorr = new JSONObject( response );
                                    String status = jsoncorr.getString( "status" );

                                    if ("ok".equals( status )) {

                                        JSONArray xaray = jsoncorr.getJSONArray( "y" );
                                        JSONArray yaray = jsoncorr.getJSONArray( "x" );
                                        JSONArray datearray = jsoncorr.getJSONArray( "date" );
                                        ArrayList<String> x = new ArrayList<String>();
                                        ArrayList<String> y = new ArrayList<String>();
                                        ArrayList<String> date1 = new ArrayList<String>();
                                        int i = 0;
                                        while (i < xaray.length()) {
                                            Log.e( "iron", xaray.getString( i ) );
                                            x.add( xaray.getString( i ) );
                                            y.add( yaray.getString( i ) );
                                            String[] all = datearray.getString( i ).split( "T" );
                                            String[] date = all[0].split( "-" );
                                            int year = Integer.parseInt( date[0] );
                                            int mounth = Integer.parseInt( date[1] );
                                            int day = Integer.parseInt( date[2] );
                                            String[] time = all[1].split( ":" );
                                            int hour = Integer.parseInt( time[0] );
                                            int min = Integer.parseInt( time[1] );
                                            Calendar mCalendar = new GregorianCalendar();
                                            mCalendar.set( year, mounth, day, hour, min, 00 );
                                            Calendar.Builder calendar = new Calendar.Builder();
                                            calendar.setDate( year, mounth - 1, day );
                                            calendar.setTimeOfDay( hour, min, 0 );
                                            calendar.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
                                            date1.add( String.valueOf( calendar.build().getTime() ) );
                                            i++;
                                        }

                                        Intent intent = new Intent( context, MapsActivity.class );
                                        Bundle args = new Bundle();
                                        args.putSerializable( "x", (Serializable) x );
                                        args.putSerializable( "y", (Serializable) y );
                                        args.putSerializable( "date", (Serializable) date1 );
                                        intent.putExtra( "BUNDLE", args );
                                        context.startActivity( intent );
                                    } else {
                                        String message = jsoncorr.getString( "message" );
                                        SendEror.sender( context, message );
                                    }

                                } catch (JSONException e) {
                                    dialog.dismiss();
                                    e.printStackTrace();
                                    SendEror.sender( context, e.toString() );
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Alert.shows( context, "", context.getString( R.string.please_check_the_connetion ), "ok", "" );
                        SendEror.sender( context, error.toString() );


                    }

                } ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        // params.put("parentToken",getowner(context));
                        params.put( "token", getctoken( context ) );
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue( context );
                requestQueue.add( stringRequest );
            }
        } );
        video.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), RecordVideoActivity.class );
                startActivity( intent );

            }
        } );
        voice.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), RecordVoiceActivity.class );
                startActivity( intent );
            }
        } );
        file.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), FileManager.class );
                startActivity( intent );
            }
        } );


    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }
}

