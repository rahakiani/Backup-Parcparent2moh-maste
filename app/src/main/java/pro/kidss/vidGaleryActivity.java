package pro.kidss;

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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class vidGaleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> dating = new ArrayList<String>();
    ArrayList<String> Type = new ArrayList<String>();
    ProgressDialog progressDialog;
    ScrollView scrollView;
    Dialog dialog1;
    Button accept;
    TextView messageTv, titleTv, timer;
    ImageView close;
    FloatingActionButton fabremove, up, down;
    RecyclerviewVIDGAL dataAdapter;
    Intent intent3;
    String type = "";
    String datess = "";
    Roomdb roomdb;
    List<String> vidaddress;
    private SwipeRefreshLayout swpref;
    ArrayList<MsinData> dataList = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vid_galery );
        roomdb = Roomdb.getInstance( this );
        dataList.addAll( roomdb.mainDao().getall() );
        dialog1 = new Dialog( this );
        intent3 = getIntent();

        if (intent3 != null) {
            type = intent3.getStringExtra( "Type" );
//            datess = intent3.getStringExtra( "Date" ).split( ",,::" )[0];
            datess = intent3.getStringExtra( "Date" );
        }
        fabremove = (FloatingActionButton) findViewById( R.id.fab );
        swpref = (SwipeRefreshLayout) findViewById( R.id.swpref );
        swpref.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity( getIntent() );
                swpref.setRefreshing( false );
            }
        } );
        vidaddress = roomdb.mainDao().getaddressss( type, datess );
        Log.e( "Vidadress", vidaddress.toString() );
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
        recyclerView.setLayoutManager( gridLayoutManager );
        Log.e( "Typer", type );
        dataAdapter = new RecyclerviewVIDGAL( vidGaleryActivity.this, vidaddress, datess );
        recyclerView.setAdapter( dataAdapter );

//        loadvideo();
//        fabremove.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                JSONObject jsonObject = new JSONObject();
//                JSONArray jsonArray = new JSONArray();
//                int ii = 0;
////                while (ii < dataAdapter.getremovelist().size()) {
////                    jsonArray.put( "/static/" + dataAdapter.getremovelist().get( ii ).split( "/static/" )[1] );
////                    ii++;
////                }
//                CtokenDataBaseManager ctokenDataBaseManager = new CtokenDataBaseManager( vidGaleryActivity.this );
//                try {
//                    jsonObject.put( "token", ctokenDataBaseManager.getctoken() );
//                    jsonObject.put( "videoUrl", jsonArray );
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                //send to server
//                Log.e( "fuuuuuuuuuuuuuuukit", jsonObject.toString() );
//                AlertDialog.Builder alertClose = new AlertDialog.Builder( vidGaleryActivity.this );
//                alertClose.setMessage( "Do you want to delete the videos?" )
//                        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //send
//                                StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/delete-video/",
//                                        new Response.Listener<String>() {
//                                            @Override
//                                            public void onResponse(String response) {
//                                                Toast.makeText( vidGaleryActivity.this, "Successfully removed", Toast.LENGTH_SHORT ).show();
////                                                loadvideo();
//                                                finish();
//                                                startActivity( getIntent() );
//
//
//                                            }
//                                        }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        ShowTry();
//                                        progressDialog.dismiss();
//                                        Alert.shows( vidGaleryActivity.this, "", "please check the connection", "ok", "" );
//                                        SendEror.sender( vidGaleryActivity.this, error.toString() );
//                                    }
//
//                                } ) {
//                                    @Override
//                                    protected Map<String, String> getParams() {
//                                        Map<String, String> params = new HashMap<String, String>();
//                                        params.put( "data", jsonObject.toString() );
//                                        return params;
//                                    }
//                                };
//                                RequestQueue requestQueue = Volley.newRequestQueue( vidGaleryActivity.this );
//                                requestQueue.add( stringRequest );
//
//
//                            }
//                        } ).setNegativeButton( "No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
////                        loadvideo();
//
//                    }
//                } ).show();
//
//            }
//        } );


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

    public void loadvideo() {
        ShowDialog();
        //progressDialog = ProgressDialog.show(vidGaleryActivity.this, "please wait", "connecting to server...", true);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/video-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e( "Ex32123", response );
                        try {
                            dialog1.dismiss();
                            //progressDialog.dismiss();
                            JSONObject vidobject = new JSONObject( response );
                            if (vidobject.has( "token" )) {
                                JSONArray viduri = vidobject.getJSONArray( "VideoAddress" );
                                JSONArray Typeaaray = vidobject.getJSONArray( "Type" );
                                JSONArray datearray = vidobject.getJSONArray( "Date" );
                                int i = 0;
                                while (i < viduri.length()) {
                                    Log.e( "GGGG", Typeaaray.get( i ).toString() );
                                    Log.e( "TTTTT", type );
                                    if (Typeaaray.get( i ).toString().equals( type )) {


                                        String[] address = viduri.getString( i ).split( "A" );
//                                                MsinData data = new MsinData( Arrays.toString( address ), 0, 0 );
//                                                roomdb.mainDao().insert( data );
                                        Type.add( Typeaaray.getString( i ) );


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

                                        DateConverter converter = new DateConverter();
                                        converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
                                        String thisdate = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() );
                                        if (thisdate.equals( datess )) {
                                            dating.add( String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() + "\n" + callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() ) );
                                            imageUrlList.add( "https://im.kidsguard.ml" + viduri.getString( i ) );
                                            //dating.add("");
                                            ids.add( "" );
                                        }
                                    }
                                    i++;
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog1.dismiss();
                            ShowTry();
                            //progressDialog.dismiss();
                            //Alert.shows(vidGaleryActivity.this,"","please check the connection","ok","");
                            SendEror.sender( vidGaleryActivity.this, e.toString() );
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                dialog1.dismiss();
                ShowTry();
                //Alert.shows(vidGaleryActivity.this,"","please check the connection","ok","");
                SendEror.sender( vidGaleryActivity.this, error.toString() );
            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "token", getctoken( vidGaleryActivity.this ) );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( vidGaleryActivity.this );
        requestQueue.add( stringRequest );
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
