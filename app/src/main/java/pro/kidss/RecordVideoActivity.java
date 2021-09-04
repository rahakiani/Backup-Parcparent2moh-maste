package pro.kidss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class RecordVideoActivity extends AppCompatActivity {
    VideoView v;
    MediaController mediaController;
    ProgressDialog progressDialog;
    private DateConverter converter;
    private View parent_view;
    TimePicker timePicker;
    TextInputLayout eddittext;
    EditText editText;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_record_video );
        parent_view = findViewById( android.R.id.content );
        ImageView btncateapp = (ImageView) findViewById( R.id.btncateapp );
        bt = findViewById( R.id.btnrec );
        editText = findViewById( R.id.edtduration );
        eddittext = findViewById( R.id.inputTextPhoneReg );
        timePicker = findViewById( R.id.timePicker1 );
        btncateapp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( RecordVideoActivity.this, BlockAppActivity.class ) );
            }
        } );
    }

    public void btnShowVideo(View view) {
        ShowVideo();
    }

    public void request(final Context context, final String type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://im.kidsguard.ml/api/request-media/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject alljs = null;
                        try {
                            alljs = new JSONObject(response);
                            String status = alljs.getString("status");

                            switch (status) {
                                case "ok":
                                    snackBarIconSuccess();
//                                    Alert.shows(RecordVideoActivity.this,"","Please wait for 5 minutes, then click on the 'Show captured video' button and see the video.","ok","");
                                    break;
                                default:
                                    String message = alljs.getString( "message" );
                                    SendEror.sender( context, message );
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(RecordVideoActivity.this, e.toString());

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackBarIconErorr();
//                Alert.shows(RecordVideoActivity.this,"","please check the connection","ok","");
                SendEror.sender( RecordVideoActivity.this, error.toString() );
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "media", type );
                params.put( "parentToken", getowner( context ) );
                params.put( "kidToken", getctoken( context ) );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( context );
        requestQueue.add( stringRequest );

    }

    private void snackBarIconErorr() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please Check The Connection" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.red_600 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }

    public void requestFrontVideoCamera(View view) {
        request( RecordVideoActivity.this, "video1" );
    }

    public void requestRearVideoCamera(View view) {
        request( RecordVideoActivity.this, "video2" );
    }

    public void btnreqaudio(View view) {
        request( RecordVideoActivity.this, "voice" );
    }
    public void btnshowaudio(View view){
        String path="https://req.kidsguard.pro/static/"+getctoken(getApplicationContext())+"/voice/latest.mp3";
        goToUrl(path);

    }
    public void btnspicvid(View view) {
        //TODO
        eddittext.setVisibility( View.VISIBLE );
        editText.setVisibility( View.VISIBLE );
        bt.setVisibility( View.VISIBLE );
        timePicker.setVisibility( View.VISIBLE );
        PersianDatePickerDialog picker2 = new PersianDatePickerDialog( this )
                .setPositiveButtonString( "باشه" )

                .setNegativeButton( "بیخیال" )
                //.setTypeFace(new Typeface())
                .setActionTextColor( Color.GRAY )
                .setListener( new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        converter = new DateConverter();
                        converter.persianToGregorian(persianCalendar.getPersianYear(),persianCalendar.getPersianMonth(),persianCalendar.getPersianDay());
                        //  Toast.makeText(this, persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                        // Datato.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());

                    }

                    @Override
                    public void onDisimised() {

                    }

                });

        picker2.show();
        Button btnrec=(Button)findViewById(R.id.btnrec);
        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        EditText edtduration=(EditText)findViewById(R.id.edtduration);
        edtduration.setVisibility(View.VISIBLE);
        btnrec.setAlpha(1f);
        timePicker1.setAlpha(1f);
    }
    public void ShowVideo() {
        startActivity(new Intent(getApplicationContext(), VideoCategoryActivity.class));

    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }

    public String getowner(Context context) {
        OwnerDataBaseManager owne = new OwnerDataBaseManager(context);
        return owne.getowner();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }


    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    public void btnsenddate(View view){
        EditText edtduration=(EditText)findViewById(R.id.edtduration);
        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        // String type2=converter.getYear()+","+converter.getMonth()+","+converter.getDay();
        String dur= String.valueOf(Integer.parseInt(edtduration.getText().toString())*60000);
        AlertDialog.Builder alertClose=new AlertDialog.Builder(RecordVideoActivity.this);
        alertClose.setTitle("").
                setMessage("").
                setPositiveButton("Front camera", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
                        String type="video1,"+converter.getYear()+","+converter.getMonth()+","+converter.getDay()+","+timePicker1.getHour()+","+timePicker1.getMinute()+","+dur;
                        Toast.makeText(RecordVideoActivity.this, type, Toast.LENGTH_SHORT).show();
                        request(RecordVideoActivity.this,type);
                    }
                }).setNegativeButton("Rear camera", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
                String type="video2,"+converter.getYear()+","+converter.getMonth()+","+converter.getDay()+","+timePicker1.getHour()+","+timePicker1.getMinute()+","+dur;
                Toast.makeText(RecordVideoActivity.this, type, Toast.LENGTH_SHORT).show();
                request(RecordVideoActivity.this,type);
            }
        }).show();
    }
    public void btnscreen(View view) {
        snackBarIconSuccesss();
//        Toast.makeText(this, "please wait on this activity for one minute", Toast.LENGTH_SHORT).show();
        InsSub insSub = new InsSub();
        insSub.insLock( RecordVideoActivity.this, "true" );
        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                InsSub insSub = new InsSub();
                insSub.insLock( RecordVideoActivity.this, "false" );
            }
        }, 60000 );
    }

    private void snackBarIconSuccesss() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please Wait On This Activity For One Minute" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_done );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.green_500 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }

    private void snackBarIconSuccess() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please Wait For 5 Minutes, Then Click On The 'Show Captured Video' Button And See The Video." );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_done );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.green_500 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }

    public void RequestFrontCamera(View view) {
        request( RecordVideoActivity.this, "1" );
    }

    public void requestRearCamera(View view) {
        request( RecordVideoActivity.this, "2" );
    }

}

