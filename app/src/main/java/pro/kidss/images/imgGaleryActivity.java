package pro.kidss.images;

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

import pro.kidss.Alert;
import pro.kidss.DateConverter;
import pro.kidss.R;
import pro.kidss.SendEror;
import pro.kidss.SpacingItemDecoration;
import pro.kidss.Tools;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.database.MsinData;

public class imgGaleryActivity extends AppCompatActivity {
    ArrayList<String> img = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> dating = new ArrayList<String>();
    ArrayList<String> Type = new ArrayList<String>();
    ArrayList<String> timing = new ArrayList<String>();
    RecyclerView recyclerView;
    Dialog dialog1;
    Button accept;
    TextView messageTv, titleTv, timer;
    ImageView close;
    GridLayoutManager gridLayoutManager;
    ProgressDialog progressDialog;
    FloatingActionButton removefab;
    RecyclerviewImage dataAdapter;
    private SwipeRefreshLayout swpref;
    ArrayList<MsinData> dataList = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_img_galery );
        removefab = (FloatingActionButton) findViewById( R.id.fab );
        swpref = (SwipeRefreshLayout) findViewById( R.id.swpref );
        dialog1 = new Dialog(this);
        swpref.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swpref.setRefreshing(false);
            }
        });
        ShowDialog();
        showgalery();

    }

    private void ShowDialog() {
        dialog1.setContentView(R.layout.alert_wait);
        close = (ImageView) dialog1.findViewById(R.id.close_accept);
        accept = (Button) dialog1.findViewById(R.id.btnAccept);
        timer = (TextView) dialog1.findViewById(R.id.text_timer);
        titleTv = (TextView) dialog1.findViewById(R.id.title_go);
        messageTv = (TextView) dialog1.findViewById(R.id.messaage_acceot);
        titleTv.setText("Please Wait");
        messageTv.setText("Connecting To Server...");
        long duration = TimeUnit.SECONDS.toMillis(1);
        new CountDownTimer(duration, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format( Locale.ENGLISH, "%02d:%02d"
                        , TimeUnit.MINUTES.toSeconds(0)
                        , TimeUnit.SECONDS.toSeconds(59) -
                                TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toSeconds(1)));
                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                timer.setVisibility(View.GONE);
                accept.setVisibility(View.VISIBLE);


            }
        }.start();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));
        dialog1.show();
    }


    public void showgalery(){
        Log.e("rererrrret", getctoken(imgGaleryActivity.this) );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://im.kidsguard.ml/api/picture-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e("32122", response );
                        JSONObject alljs = null;
                        try {
                            alljs = new JSONObject(response);
                            if (alljs.has("token")){

//
                                    JSONArray imgaray=alljs.getJSONArray("Picture");
                                JSONArray idaray=alljs.getJSONArray("Id");
                                    int i=0;
                                    while (i<imgaray.length()){
                                        img.add(imgaray.getString(i));
                                        ids.add(idaray.getString(i));
                                        i++;
                                    }
                                JSONArray datearray=alljs.getJSONArray("Date");
                                int b=0;
                                while (b<datearray.length()){
                                    String[] all=datearray.getString(b).split("T");
                                    String[] date=all[0].split("-");
                                    int year= Integer.parseInt(date[0]);
                                    int mounth= Integer.parseInt(date[1]);
                                    int day= Integer.parseInt(date[2]);
                                    String[] time=all[1].split(":");
                                    int hour= Integer.parseInt(time[0]);
                                    int min= Integer.parseInt(time[1]);
//                                    Calendar mCalendar = new GregorianCalendar();
//                                    mCalendar.set(year,mounth,day,hour,min,00);
//                                    Calendar.Builder calendar=new Calendar.Builder();
//                                    calendar.setDate(year,mounth-1,day);
//                                    calendar.setTimeOfDay(hour,min,0);
//                                    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//                                    dating.add(String.valueOf(calendar.build().getTime()));
                                    Calendar callForDate = Calendar.getInstance();
                                    callForDate.set( year, mounth, day, hour, min, 00 );
                                    callForDate.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
                                    java.text.SimpleDateFormat currentDate = new java.text.SimpleDateFormat( "dd-MMMM-yyyy" );

                                    DateConverter converter = new DateConverter();
                                    converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
                                    String thisdate = String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() );
//                                    dating.addAll( Arrays.asList(datearray.getString(i).split("T")));

//                                    if (!Type.contains( Typearray.getString( i ) )) {
                                    dating.add( String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay()));
                                    timing.add(String.valueOf( callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() ) );
                                    Log.e( "DATEING", dating.toString() );

                                    b++;
                                }
                                dialog1.dismiss();
//                                Log.e("onResponse", img.get(i));
                                    recyclerView = (RecyclerView) findViewById(R.id.imgrecyclerView);
                                    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(imgGaleryActivity.this, 3), true));
                                recyclerView.setLayoutManager( gridLayoutManager );
                                recyclerView.setHasFixedSize(true);
                                dataAdapter = new RecyclerviewImage( img, imgGaleryActivity.this, "img", removefab, ids, dating, dataList ,timing );
                                recyclerView.setAdapter( dataAdapter );
                        }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(imgGaleryActivity.this, e.toString());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(imgGaleryActivity.this,"","please check the connection","ok","");
                SendEror.sender(imgGaleryActivity.this, error.toString());
                Showtry();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", getctoken(imgGaleryActivity.this));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(imgGaleryActivity.this);
        requestQueue.add(stringRequest);
    }

    private void Showtry() {
        dialog1.setContentView(R.layout.try_alert);
        close = (ImageView) dialog1.findViewById(R.id.close_try);
        accept = (Button) dialog1.findViewById(R.id.bt_try);
        messageTv = (TextView) dialog1.findViewById(R.id.messaage_try);
        messageTv.setText("Please check the connection");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();
    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
}
