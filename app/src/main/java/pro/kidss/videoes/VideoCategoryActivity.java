package pro.kidss.videoes;

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
import com.mmstq.progressbargifdialog.ProgressBarGIFDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import pro.kidss.csc.ExplainItemActivity;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.DateConverter;
import pro.kidss.database.MainDao;
import pro.kidss.database.MsinData;
import pro.kidss.R;
import pro.kidss.database.Roomdb;
import pro.kidss.SendEror;

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
    ArrayList<String> dating = new ArrayList<String>();
    ArrayList<String> timing = new ArrayList<String>();
    FloatingActionButton fabremove;
    //RecyclerviewImage dataAdapter;
    RecyclerviewVidcat dataAdapter;
    ProgressDialog dialog = null;
    private SwipeRefreshLayout swpref;

    CoordinatorLayout coordinatorLayout;
    List<String> distincmsindata;
    List<String> addressss;
    Roomdb roomdb;
    MainDao mainDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        dialog1 = new Dialog( this );
        setContentView( R.layout.activity_video_category );
        dialog = ProgressDialog.show( VideoCategoryActivity.this, "Please wait", "Connecting to server...", true);
        roomdb = Roomdb.getInstance( this );
//        coordinatorLayout = (CoordinatorLayout) findViewById( R.id.coordinatorr );
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


    public void loadvideo() {


        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://apisender.online/api/video-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e( "Ex32123", response );
                        try {

                            JSONObject vidobject = new JSONObject( response );
                            if (vidobject.has( "token" )) {

                                JSONArray viduri = vidobject.getJSONArray( "VideoAddress" );
                                JSONArray Typearray = vidobject.getJSONArray( "Type" );
                                JSONArray datearray = vidobject.getJSONArray( "Date" );
                                int i = 0;
                                while (i < viduri.length()) {
                                    String[] address = viduri.getString( i ).split( "A" );
                                    String typee = Typearray.getString( i ).toString();
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
                                    SimpleDateFormat currentDate = new SimpleDateFormat( "dd-MMMM-yyyy" );

                                    DateConverter converter = new DateConverter();
                                    converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
                                    String thisdate = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() );
//                                    dating.addAll( Arrays.asList(datearray.getString(i).split("T")));

//                                    if (!Type.contains( Typearray.getString( i ) )) {
                                    dating.add( String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay()));
                                    timing.add(String.valueOf( callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() ) );
//                                    Log.e( "DATEING", dating.toString() );
//                                        String datee = dating.get( i ).toString();

                                    if (!Type.contains( Typearray.getString( i ) )) {
                                        Type.add( Typearray.getString( i ) );
                                        imageUrlList.add( "https://apisender.online" + viduri.getString( i ) );
                                        ids.add( "" );
                                    }



                                    if (roomdb.mainDao().checkaddress( "https://apisender.online" + viduri.getString( i ) ) == 0) {
                                        MsinData data = new MsinData( "https://apisender.online" + viduri.getString( i ), 0, 0, dating.get( i ), Typearray.getString( i ),"/Download/kidvideo.mp4",timing.get(i) );
                                        roomdb.mainDao().insert( data );


                                        ids.add( "" );
//                                        Log.e( "LKLKKL", roomdb.mainDao().getallvideo().toString() );

                                    } else {
                                        Log.e( "DDDD", "FFFF" );

//                                            dataList.addAll( roomdb.mainDao().getall() );
                                    }

//                                    addressss = roomdb.mainDao().getad(roomdb.mainDao().gettyper().get( i ));
                                    i++;
                                }


                                dialog.dismiss();

                                recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
                                gridLayoutManager = new GridLayoutManager( getApplicationContext(), 2 );
                                recyclerView.setLayoutManager( gridLayoutManager );
                                dataAdapter = new RecyclerviewVidcat( imageUrlList, VideoCategoryActivity.this, fabremove, ids, Type);
                                recyclerView.setAdapter( dataAdapter );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Showtry();
                            //Alert.shows(VideoCategoryActivity.this,"","please check the connection","ok","");
                            SendEror.sender( VideoCategoryActivity.this, e.toString() );
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
        messageTv.setText( "Please check the connection" );
        close.setOnClickListener( new View.OnClickListener() {
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
