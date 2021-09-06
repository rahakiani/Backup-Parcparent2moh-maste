package pro.kidss;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class BTSActivity extends AppCompatActivity {
    RecyclerView ryclrbts;
    ProgressDialog dialog = null;
    SwipeRefreshLayout swpref;
    ArrayList<String> lac,cell,mnc,mcc,dating;
    Dialog dialog1;
    ImageView close;
    Button accept;
    TextView messageTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btsactivity);
        dialog1 = new Dialog( this );
        lac=new ArrayList<String>();
        cell=new ArrayList<String>();
        mnc=new ArrayList<String>();
        mcc=new ArrayList<String>();
        dating=new ArrayList<String>();
        dialog = ProgressDialog.show(BTSActivity.this, "please wait", "connecting to server...", true);
        ryclrbts = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
        Intent intent = getIntent();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.ml/api/btsList/",
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(BTSActivity.this, response, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        try {

//                            String status = vidobject.getString( "status" );
//
//                            if (status.equals( "error" )){
//                                ShowTry();
//                            }else {


                                JSONArray jsonArray = new JSONArray( response );
                                Log.e( "BTS", response );
//                            JSONArray datearray = vidobject.getJSONArray( "Date" );
                                int i = 0;
                                while (i < jsonArray.length()) {
                                    lac.add( new JSONObject( jsonArray.getJSONObject( i ).getString( "bts" ) ).getString( "lac" ) );
                                    cell.add( new JSONObject( jsonArray.getJSONObject( i ).getString( "bts" ) ).getString( "cid" ) );
                                    mnc.add( new JSONObject( jsonArray.getJSONObject( i ).getString( "bts" ) ).getString( "mnc" ) );
                                    mcc.add( new JSONObject( jsonArray.getJSONObject( i ).getString( "bts" ) ).getString( "mcc" ) );
                                    String datin =  jsonArray.getJSONObject( i ).getString( "date" );
                                    Log.e( "DATING",datin.toString() );
                                    String[] all =  datin.split( "T" );
                                    String[] datee = all[0].split( "-" );
                                    int year = Integer.parseInt( datee[0] );
                                    int mounth = Integer.parseInt( datee[1] );
                                    int day = Integer.parseInt( datee[2] );
                                    String[] time = all[1].split( ":" );
                                    int hour = Integer.parseInt( time[0] );
                                    int min = Integer.parseInt( time[1] );
                                    Calendar callForDate = Calendar.getInstance();
                                    callForDate.set( year, mounth, day, hour, min, 00 );
                                    callForDate.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
                                    java.text.SimpleDateFormat currentDate = new java.text.SimpleDateFormat( "dd-MMMM-yyyy" );
                                    DateConverter converter = new DateConverter();
                                    converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
                                    dating.add( String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() + "\n" + callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() ) );
//
                                    i++;
                                }
                                ryclrbts = (RecyclerView) findViewById( R.id.rcyclrbts );
                                CardViewBTS adapter = new CardViewBTS( BTSActivity.this, lac, cell, mcc, mnc, dating );
                                ryclrbts.setAdapter( adapter );

                                LayoutAnimationController animation =
                                        AnimationUtils.loadLayoutAnimation( BTSActivity.this, R.anim.layout_animation_slide_from_left );
                                ryclrbts.setLayoutAnimation( animation );

                                LinearLayoutManager layoutManager = new LinearLayoutManager( BTSActivity.this );
                                ryclrbts.setLayoutManager( layoutManager );


//                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                            SendEror.sender(BTSActivity.this,e.toString());


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Alert.shows(BTSActivity.this,"","please check the connection","ok","");
                SendEror.sender(BTSActivity.this,error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                // params.put("parentToken",getowner(ExplainItemActivity.this));
                params.put("kidToken",getctoken(BTSActivity.this));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(BTSActivity.this);
        requestQueue.add(stringRequest);
    }

    private void ShowTry() {
        dialog1.setContentView( R.layout.try_alert );
        close = (ImageView) dialog1.findViewById( R.id.close_try );
        accept = (Button) dialog1.findViewById( R.id.bt_try );
        messageTv = (TextView) dialog1.findViewById( R.id.messaage_try );
        messageTv.setText( "Please Check The Connection" );
        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity( intent );
                dialog1.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity( intent );
            }
        } );

        dialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog1.show();
    }

    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
}