package pro.kidss.voice;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mmstq.progressbargifdialog.ProgressBarGIFDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import pro.kidss.Alert;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.DateConverter;
import pro.kidss.OwnerDataBaseManager;
import pro.kidss.R;
import pro.kidss.SendEror;
import pro.kidss.wlcome.WelcomeActivity;

public class RecordVoiceActivity extends AppCompatActivity {


    ImageView player;
    //    private SwipeRefreshLayout swpref;
    private DateConverter converter;
    FloatingActionButton fabremove, fab2;
    //    private VoiceAdapterRecy adapter ;
    DownloadManager downloadManager;

    TimePicker timePicker;
    TextInputLayout editText;
    EditText editTextt;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_record_voice );

        player = findViewById( R.id.btnplayervoice );
        fabremove = (FloatingActionButton) findViewById( R.id.fab );
        fab2 = (FloatingActionButton) findViewById( R.id.fab2 );
        timePicker = findViewById( R.id.timePicker1 );
        editText = findViewById( R.id.inputTextPhoneReg );
        editTextt = findViewById( R.id.edtduration );
        bt = findViewById( R.id.btnrec );




    }
    public void btnreqaudio(View view){
        request(RecordVoiceActivity.this, "voice");
    }
    public void request(final Context context, final String type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://apisender.online/api/request-media/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject alljs = null;
                        try {
                            alljs = new JSONObject(response);
                            String status = alljs.getString("status");

                            switch (status) {
                                case "ok":
                                    Alert.shows(RecordVoiceActivity.this,"","Please wait for 5 minutes, then click on the 'Play voice of your kid device' button and listen the voice.","ok","");
                                    break;
                                default:
                                    String message = alljs.getString("message");
                                    SendEror.sender(context, message);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(RecordVoiceActivity.this, e.toString());

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(context,"","please check the connection","ok","");
                SendEror.sender(RecordVoiceActivity.this, error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("media", type);
                params.put("parentToken", getowner(context));
                params.put("kidToken", getctoken(context));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }


    //    public void geturls(Context context){
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://apisender.online/api/voice-detail/",
//                new Response.Listener<String>() {
//                    @RequiresApi(api = Build.VERSION_CODES.O)
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("onResponse", response );
//                        JSONObject alljs = null;
//                        try {
//                            alljs = new JSONObject(response);
//                            if (alljs.has("token")){
//                                    JSONArray jsonArray=alljs.getJSONArray("VideoAddress");
//                                    int i=0;
//                                    while (i<jsonArray.length()){
//                                        voiceurl.add("https://apisender.online"+jsonArray.getString(i));
//                                        i++;
//                                    }
//                                JSONArray datearray=alljs.getJSONArray("Date");
//                                int b=0;
//                                while (b<datearray.length()){
//                                    String[] all = datearray.getString( b ).split( "T" );
//                                    String[] date = all[0].split( "-" );
//                                    int year = Integer.parseInt( date[0] );
//                                    int mounth = Integer.parseInt( date[1] );
//                                    int day = Integer.parseInt( date[2] );
//                                    String[] time = all[1].split( ":" );
//                                    int hour = Integer.parseInt( time[0] );
//                                    int min = Integer.parseInt( time[1] );
//                                    Calendar callForDate = Calendar.getInstance();
//                                    callForDate.set( year, mounth, day, hour, min, 00 );
//                                    callForDate.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
//                                    SimpleDateFormat currentDate = new SimpleDateFormat( "dd-MMMM-yyyy" );
//
////                                        final String saveCurrentDate = currentDate.format(callForDate.getTime());
////                                        Calendar mCalendar = new GregorianCalendar();
////                                        mCalendar.set(year,mounth,day,hour,min,00);
////                                        Calendar.Builder calendar=new Calendar.Builder();
////                                        calendar.setDate(year,mounth-1,day);
////                                        calendar.setTimeOfDay(hour,min,0);
////                                        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//                                    DateConverter converter = new DateConverter();
//                                    converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
//                                    String categorydate = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() );
//                                    String datee = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() + "  " + callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() );
//                                    voiceNmae.add(datee);
//                                    b++;
//                                }
//
//
////                                setRecycler()
//                                ;}
//
//                        } catch (JSONException e) {
//                            Log.e("onResponse error", e.toString());
//                            SendEror.sender(RecordVoiceActivity.this, e.toString());
//
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Alert.shows(context,"","please check the connection","ok","");
//                SendEror.sender(RecordVoiceActivity.this, error.toString());
//            }
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("token", getctoken(context));
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }
//    public void setRecycler(){
//        try {
//
//            recyclerViewgetvoice=(RecyclerView)findViewById(R.id.voiceRecyclerView);
//
//            adapter = new VoiceAdapterRecycler(voiceurl,voiceNmae, RecordVoiceActivity.this,fabremove,fab2);
//            recyclerViewgetvoice.setAdapter(adapter);
//            LayoutAnimationController animation =
//                    AnimationUtils.loadLayoutAnimation(RecordVoiceActivity.this, R.anim.layout_animation_fall_down);
//            recyclerViewgetvoice.setLayoutAnimation(animation);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(RecordVoiceActivity.this);
//            recyclerViewgetvoice.setLayoutManager(layoutManager);
//        }catch (Exception e){
//            Log.e("catch", e.toString() );
//        }
//
//    }
    public void btnspicvid(View view) {
        //TODO
        editTextt.setVisibility( View.VISIBLE );
        editText.setVisibility( View.VISIBLE );
        bt.setVisibility( View.VISIBLE );
        timePicker.setVisibility( View.VISIBLE );
        PersianDatePickerDialog picker2 = new PersianDatePickerDialog( this )
                .setPositiveButtonString( "????????" )
                .setNegativeButton( "????????????" )
                //.setTypeFace(new Typeface())
                .setActionTextColor( Color.GRAY )
                .setListener( new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        converter = new DateConverter();
                        converter.persianToGregorian( persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay() );
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
    public String getowner(Context context) {
        OwnerDataBaseManager owne = new OwnerDataBaseManager(context);
        return owne.getowner();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
    public String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnsenddate(View view){
        EditText edtduration=(EditText)findViewById(R.id.edtduration);
        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
       // String type2=converter.getYear()+","+converter.getMonth()+","+converter.getDay();
        String dur= String.valueOf(Integer.parseInt(edtduration.getText().toString())*60000);
        String type="voice,"+converter.getYear()+","+converter.getMonth()+","+converter.getDay()+","+timePicker1.getHour()+","+timePicker1.getMinute()+","+dur;
        Log.e("fdfdfgg", type);
//        Log.e("fdfdfgg", type );
        request(RecordVoiceActivity.this,type);
    }


    public void btnplayer(View view) {
        Intent intent = new Intent( RecordVoiceActivity.this, Player.class );
        startActivity( intent );

    }
}







