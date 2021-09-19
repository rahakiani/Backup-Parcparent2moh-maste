package pro.kidss;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.mmstq.progressbargifdialog.ProgressBarGIFDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.database.MainData;
import pro.kidss.database.Maindataa;
import pro.kidss.database.Romdb;
import pro.kidss.database.Roomdbb;
import pro.kidss.wlcome.WelcomeActivity;


public class ExplainItemActivity extends AppCompatActivity {

    ArrayList<String> aray = new ArrayList<String>();

    StringRequest stringRequest;
    RequestQueue requestQueue;
    RecyclerView recyclerViewDetail;
    ProgressDialog dialog = null;
    ArrayList<String> namee = new ArrayList<>();
    ArrayList<String> res=new ArrayList<String>();
    ArrayList<String> numberr=new ArrayList<>();
    EditText edtphonesearch;
    private ImageButton imgleftdra;
    private ArrayList<String> text;
    SwipeRefreshLayout swpref;
    Roomdbb roomdb;
    ProgressBarGIFDialog.Builder progressBarGIFDialog;
    Romdb roomdbb;
    recyclersmsdate dataAdapter;
    Recyclercalldate dataAdap;
    Recyclercondate dataAdapterr;
    List<String> bodyy;
    int id;
    List<String> distincmsindata;
    List<String> distincmaindata;
String bod,name,number;
    GridLayoutManager gridLayoutManager;
    ArrayList<MainData> dataList = new ArrayList<>();
    ArrayList<Maindataa> dataListt = new ArrayList<Maindataa>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explainitem);
        roomdb = Roomdbb.getInstance( this );
        roomdbb = Romdb.getInstance( this );
        progressBarGIFDialog= new ProgressBarGIFDialog.Builder(this);

        progressBarGIFDialog.setCancelable(false)

                .setTitleColor(R.color.colorPrimary) // Set Title Color (int only)

                .setLoadingGif(R.drawable.loading) // Set Loading Gif

                .setDoneGif(R.drawable.done) // Set Done Gif

                .setDoneTitle("Done") // Set Done Title

                .setLoadingTitle("Please wait...") // Set Loading Title

                .build();

//        dialog = ProgressDialog.show(ExplainItemActivity.this, "Please wait", "connecting to server...", true);
        recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
        Intent intent = getIntent();
        swpref=(SwipeRefreshLayout)findViewById(R.id.swpref);
        swpref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swpref.setRefreshing(false);
            }
        });

        String msgIntent = intent.getStringExtra("IntentName");

        switch (msgIntent)
        {
            case "SMS Data":
                //Code Here

                StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://apisender.online/api/sms-list/",
                        new Response.Listener<String>() {
                            @TargetApi(Build.VERSION_CODES.N)
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(String response) {

                                try {

                                    JSONObject jsonsms=new JSONObject(response);
                                    String status=jsonsms.getString("status");

                                    switch (status){
                                        case "ok":

                                            JSONArray diraray=jsonsms.getJSONArray("direction");
                                            JSONArray bodyaray=jsonsms.getJSONArray("body");
                                            JSONArray numberaray=jsonsms.getJSONArray("number");
                                            JSONArray idarray=jsonsms.getJSONArray("id");
                                            int i=0;
                                           // ArrayList<String> res=new ArrayList<String>();
                                            while (i<numberaray.length()){
                                                String name=numberaray.getString(i);
                                                String number=bodyaray.getString(i);
                                                String dir=diraray.getString(i);
                                                 id=idarray.getInt(i);

                                                String[] send = diraray.getString( i ).split( "\\n");

//                                                while (i<diraray.length()){
//                                                    String stat = send[0].split("\\n").toString();
                                                    Log.e( "Sendinnn",send[0] );
//                                                }
//                                                String[] sendd = send[1].split( "\n" );
//                                                Log.e( "Sendin",send.toString() );
                                                Log.e( "IID", String.valueOf( id ) );
//                                                res.add(name+":"+"\n"+number+"\n"+dir+"\n"+idd+"\n"+id);
                                                if (roomdb.mainDao().checkid( id )==0){
                                                    MainData data = new MainData(id,name,number,send[1],send[0]);
                                                    Log.e( "LKLKKL", roomdb.mainDao().getall().toString() );
                                                    roomdb.mainDao().insert( data );
                                                    dataList.add( data );
//                                                    dialog.dismiss();
//                                                    progressBar.dismiss
                                                    progressBarGIFDialog.clear();
                                                }else{
                                                 Log.e( "ASDADE","ADADE" );
                                                    progressBarGIFDialog.clear();
                                                }






                                                i++;

                                            }

                                           recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
                                            distincmsindata = roomdb.mainDao().getnumber();
//                                            int b = 0;
//                                            while (b == distincmsindata.size()){
//                                                bodyy = roomdb.mainDao().bodyy( distincmsindata.get( b ) );
////                                                bod = bodyy.get( b );
//                                                Log.e( "DADA",bodyy.get( b ) );
//                                                b++;
//                                            }



                                            gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
                                            recyclerViewDetail.setLayoutManager( gridLayoutManager );
                                            dataAdapter=new recyclersmsdate(getApplicationContext(),distincmsindata,bod);
                                            recyclerViewDetail.setAdapter( dataAdapter );
//                                           recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);


                                            break;
                                        default:
                                            String message=jsonsms.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
                                     progressBarGIFDialog.clear();
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());


                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         progressBarGIFDialog.clear();
                        Alert.shows(ExplainItemActivity.this,"","Please check the connection","ok","");
                        SendEror.sender(ExplainItemActivity.this,error.toString());
                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                       // params.put("parentToken",getowner(ExplainItemActivity.this));
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);

                break;

            case "Contact Data":
                //Code Here

                stringRequest=new StringRequest(Request.Method.POST,"https://apisender.online/api/contacts-list/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBarGIFDialog.clear();
                                Log.e("smsres", response );
                                try {
                                    JSONObject jsoncontact=new JSONObject(response);
                                    String status=jsoncontact.getString("status");

                                    switch (status){
                                        case "ok":

                                            JSONArray contactname=jsoncontact.getJSONArray("name");
                                            JSONArray contactnum=jsoncontact.getJSONArray("tell");

                                            int i=0;
                                            while (i<contactname.length()){
                                                String nameeee=contactname.getString( i );
                                                String nuuum=contactnum.getString( i );
                                                namee.add( nameeee);
                                               Log.e( "CHIIIII",namee.toString() );

                                                numberr.add( nuuum);
                                                Log.e( "CHII52572I",numberr.toString() );

                                                i++;

                                            }


                                            gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
                                            recyclerViewDetail.setLayoutManager( gridLayoutManager );
                                            dataAdapterr=new Recyclercondate(getApplicationContext(),namee,numberr);
                                            recyclerViewDetail.setAdapter( dataAdapterr );


                                            break;
                                        default:
                                            String message=jsoncontact.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
                                     progressBarGIFDialog.clear();
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarGIFDialog.clear();
                        Alert.shows(ExplainItemActivity.this,"","please check the connection","ok","");
                        SendEror.sender(ExplainItemActivity.this,error.toString());

                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("parentToken",getowner(ExplainItemActivity.this));
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);

                break;

            case "Call Data":
                //Code Here

                stringRequest=new StringRequest(Request.Method.POST,"https://apisender.online/api/calls-list/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsoncall=new JSONObject(response);
                                    String status=jsoncall.getString("status");

                                    switch (status){
                                        case "ok":
                                            JSONArray phnumberaray=jsoncall.getJSONArray("phnumber");
                                            JSONArray calldatearay=jsoncall.getJSONArray("calldate");
                                            JSONArray calldurationaray=jsoncall.getJSONArray("callduration");
                                            JSONArray diraray=jsoncall.getJSONArray("dir");
                                            JSONArray idarray=jsoncall.getJSONArray("id");


                                            int i=0;
                                            while (i<phnumberaray.length()){
                                                String phNumber=phnumberaray.getString(i);
                                                String callDate=calldatearay.getString(i);
                                                int idd=idarray.getInt( i );
                                                Date callDayTime = new Date(Long.valueOf(callDate));
                                                String callDuration=calldurationaray.getString(i);
                                                String dir=diraray.getString(i);
                                                res.add("number: "+phNumber+"\n"+"date: "+callDayTime+"\n"+"duration: "+callDuration+"\n"+"direction: "+dir);
                                                if (roomdbb.mainDao().checkid( id )==0) {


                                                    Maindataa dataa = new Maindataa( idd, phNumber, dir, callDayTime.toString(), callDuration );
                                                    Log.e( "LKLKKL", roomdbb.mainDao().getall().toString() );
                                                    roomdbb.mainDao().insert( dataa );
                                                    dataListt.add( dataa );
                                                   progressBarGIFDialog.clear();
                                                }else {
                                                    Log.e( "AFASDF", String.valueOf( id ) );
                                                    progressBarGIFDialog.clear();
                                                }


                                                i++;
                                            }











                                    recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
                                            distincmaindata = roomdbb.mainDao().getnumber();
//                                            int b = 0;
//                                            while (b == distincmsindata.size()){
//                                                bodyy = roomdb.mainDao().bodyy( distincmsindata.get( b ) );
////                                                bod = bodyy.get( b );
//                                                Log.e( "DADA",bodyy.get( b ) );
//                                                b++;
//                                            }



                                    gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
                                    recyclerViewDetail.setLayoutManager( gridLayoutManager );
                                            dataAdap=new Recyclercalldate(getApplicationContext(),distincmaindata);
                                    recyclerViewDetail.setAdapter( dataAdap );


                                            break;
                                        default:
                                            String message=jsoncall.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
                                     progressBarGIFDialog.clear();
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         progressBarGIFDialog.clear();
                        Alert.shows(ExplainItemActivity.this,"","please check the connection","ok","");
                        SendEror.sender(ExplainItemActivity.this,error.toString());


                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        //params.put("parentToken",getowner(ExplainItemActivity.this));
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);

                break;

            case "InstalledPackage Data":
                //Code Here
                stringRequest=new StringRequest(Request.Method.POST,"https://apisender.online/api/packagename-list/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                 progressBarGIFDialog.clear();
                                JSONObject jsonstatus = null;

                                try {
                                    jsonstatus = new JSONObject(response);
                                    String status = jsonstatus.getString("status");
                                    switch (status){
                                        case "ok":
                                            try {
                                                JSONArray appnamearay=jsonstatus.getJSONArray("name");

                                                int i=0;
                                                while(i<appnamearay.length()){
                                                    res.add(appnamearay.getString(i));
                                                    i++;
                                                }

                                                recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
                                                recyclerViewAddList(ExplainItemActivity.this,res,recyclerViewDetail);

                                            } catch (JSONException e) {
                                                e.printStackTrace();

                                                // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                                 progressBarGIFDialog.clear();
                                                SendEror.sender(ExplainItemActivity.this,e.toString());

                                            }
                                            break;
                                        default:
                                            String message=jsonstatus.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                     progressBarGIFDialog.clear();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         progressBarGIFDialog.clear();

                        SendEror.sender(ExplainItemActivity.this,error.toString());



                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);


                break;

        }

//        setress();

    }
    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
    public String getowner(Context context){
        OwnerDataBaseManager owne=new OwnerDataBaseManager(context);
        return owne.getowner();
    }

    public void recyclerViewAddList(Context context , ArrayList<String> arrayList,RecyclerView recyclerView){
        RecyclerViewDetailItemAdapter adapter = new RecyclerViewDetailItemAdapter(context,arrayList);
        recyclerView.setAdapter(adapter);

        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_from_left);
        recyclerView.setLayoutAnimation(animation);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
    //TODO
//    public void setress(){
//        edtphonesearch=(EditText) findViewById(R.id.edtphonesearch);
//        imgleftdra=(ImageButton)findViewById(R.id.imgleftdra);
//        edtphonesearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Drawable img = getResources().getDrawable(R.drawable.ic_close);
//                img.setBounds(0, 0, 60, 60);
//                imgleftdra.setImageDrawable(img);
//                text=editable.toString();
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void run() {
//                        if (editable.toString().equals(text)){
//                            if (editable.toString().equals("")){
//                                Drawable img = getResources().getDrawable(R.drawable.ic_search_black_24dp);
//                                img.setBounds(0, 0, 60, 60);
//                                imgleftdra.setImageDrawable(img);
//                                recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
//                                recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);
//                            }else {
//                                ArrayList<String> resfilter =new ArrayList<>( res.stream()
//                                        .filter(x->x.toLowerCase().contains(editable.toString()))
//                                        .collect(Collectors.toList()));
//                                recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
//                                recyclerViewAddList(getApplicationContext(),resfilter,recyclerViewDetail);
//
//                            }
//                        }
//                    }
//                },300);
//
//            }
//        });
//        imgleftdra.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                edtphonesearch.setText("");
//                recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
//                recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);
//            }
//        });
//            }



}
