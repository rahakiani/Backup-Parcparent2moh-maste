package pro.kidss.csc;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.kidss.Alert;
import pro.kidss.OwnerDataBaseManager;
import pro.kidss.R;
import pro.kidss.SendEror;
import pro.kidss.database.ContactData;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.database.MainData;
import pro.kidss.database.Maindataa;
import pro.kidss.database.Roomdb;
import pro.kidss.database.Roomdb;
import pro.kidss.wlcome.WelcomeActivity;


public class ExplainItemActivity extends AppCompatActivity {

    ArrayList<String> aray = new ArrayList<String>();

    StringRequest stringRequest;
    RequestQueue requestQueue;
    RecyclerView recyclerViewDetail;
    String dir;
    String callDuration;
    Date callDayTime;
    ProgressDialog dialog = null;
    ArrayList<String> namee = new ArrayList<>();
    ArrayList<String> res=new ArrayList<String>();
    ArrayList<String> numberr=new ArrayList<>();
    EditText edtphonesearch;
    Thread thread;
    int num;
    private ImageButton imgleftdra;
    String phNumber;
    private ArrayList<String> text;
    int idd;
    SwipeRefreshLayout swpref;
    Roomdb roomdb;
    boolean isrunnong;
    TextView textt;
    recyclersmsdate dataAdapter;
    Recyclercalldate dataAdap;
    Recyclercondate dataAdapterr;
    List<String> bodyy;
    int id;
    List<String> distincmsindata;
    List<String> distinccontactdata;
    List<String> distinccontacttdata;
    List<String> distincmaindata;
    String bod,name,number;
    GridLayoutManager gridLayoutManager;
    ArrayList<MainData> dataList = new ArrayList<>();
    ArrayList<ContactData> dataLisst = new ArrayList<>();
    ArrayList<Maindataa> dataListt = new ArrayList<Maindataa>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_explainitem);
        roomdb = Roomdb.getInstance( this );
        dialog = ProgressDialog.show(ExplainItemActivity.this, "Please wait", "Connecting to server...", true);
        isrunnong = false;
        thread = null;
        textt = findViewById( R.id.text_know );
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
                                Log.e("smsress", response );
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
                                            while (i<idarray.length()){
                                                String name=numberaray.getString(i);
                                                String number=bodyaray.getString(i);
                                               String dir=diraray.getString(i);
                                                 id=idarray.getInt(i);

                                                String[] send = diraray.getString( i ).split( "\\n");

                                                if (roomdb.mainDaoo().checkid( id ) == 0) {
                                                    MainData data = new MainData( id, name, number, send[1], send[0] );

                                                    roomdb.mainDaoo().insert( data );
                                                    dataList.add( data );
//


                                                } else {
//                                                    dialog.dismiss();
                                                    Log.e( "TEKRAR", String.valueOf( id ) );

                                                }
                                                i++;

                                            }













                                            dialog.dismiss();
                                           recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
                                            distincmsindata = roomdb.mainDaoo().getnumber();
                                            gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
                                            recyclerViewDetail.setLayoutManager( gridLayoutManager );
                                            dataAdapter=new recyclersmsdate(getApplicationContext(),distincmsindata,bod);
                                            recyclerViewDetail.setAdapter( dataAdapter );



                                            break;
                                        default:
                                            textt.setText( "There is no SMS" );
                                            textt.setVisibility(View.VISIBLE );
                                            String message=jsonsms.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());


                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

                                Log.e("smsres", response );
                                try {
                                    JSONObject jsoncontact=new JSONObject(response);
                                    String status=jsoncontact.getString("status");

                                    switch (status){
                                        case "ok":


                                                    JSONArray contactname= null;

                                                        contactname = jsoncontact.getJSONArray("name");

                                                    JSONArray contactnum=jsoncontact.getJSONArray("tell");
                                            JSONArray idarray=jsoncontact.getJSONArray("id");
                                            int i=0;
                                            while (i<contactname.length()){
                                                id=idarray.getInt(i);
                                                String nameeee=contactname.getString( i );
                                                String nuuum=contactnum.getString( i );
                                                namee.add( nameeee);
//                                               Log.e( "CHIIIII",namee.toString() );

                                                numberr.add( nuuum);
//                                                Log.e( "CHII52572I",numberr.toString() );
                                                if (roomdb.mainContact().checknumber( id )==0){
                                                    ContactData contactData = new ContactData(id,nuuum,nameeee);
                                                    roomdb.mainContact().insert( contactData );
//                                                    dialog.dismiss();
                                                }else {
                                                    Log.e( "Tekrarr","AFF" );
                                                }

                                                i++;

                                            }
                                            dialog.dismiss();
                                            distinccontactdata =roomdb.mainContact().getnamedis();
                                            distinccontacttdata =roomdb.mainContact().getnumberdic();



                                            gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
                                            recyclerViewDetail.setLayoutManager( gridLayoutManager );
                                            dataAdapterr=new Recyclercondate(getApplicationContext(),distinccontactdata,distinccontacttdata);
                                            recyclerViewDetail.setAdapter( dataAdapterr );

                                            break;
                                        default:
                                            textt.setText( "There is no Contact" );
                                            textt.setVisibility(View.VISIBLE );
//                                            dialog.dismiss();
                                            String message=jsoncontact.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
//                                    dialog.dismiss();
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());
                                }
                            }


                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        dialog.dismiss();
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
                                                 phNumber=phnumberaray.getString(i);
                                                String callDate=calldatearay.getString(i);
                                                idd=idarray.getInt( i );
                                                 callDayTime = new Date(Long.valueOf(callDate));
                                                 callDuration=calldurationaray.getString(i);
                                                 dir=diraray.getString(i);



                                                if (roomdb.mainDaooo().checkid( id )==0) {


                                                    Maindataa dataa = new Maindataa( idd, phNumber, dir, callDayTime.toString(), callDuration );

                                                    roomdb.mainDaooo().insert( dataa );
//                                                    Log.e( "LKLKKL", roomdb.mainDaooo().getallcall().toString() );
                                                    dataListt.add( dataa );
//                                                    dialog.dismiss();
                                                }else {
                                                    Log.e( "AFASDF", String.valueOf( id ) );

                                                }
                                                i++;
                                            }
                                            dialog.dismiss();












                                    recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
                                            distincmaindata = roomdb.mainDaooo().getnumber();
//                                            int g=0;
//                                            boolean fa;
//                                            while (g<distincmaindata.size()){
//                                                String num = roomdb.mainContact().getnamee( distincmaindata.get( g ) );
//
//                                                if (num == null){
//                                                    Log.e( "TRUEE","TRUUUUUUUE" );
//                                                }else {
//                                                    Log.e( "FFFFF","FFFFFFF" );
////                                                    holder.txtdate.setText( num);
////                                                    holder.des.setText( distic.get( g ) );
//                                                }
//                                                g++;
//                                            }

                                            Log.e( "CALL",distincmaindata.toString() );
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
                                            textt.setText( "There is no Call History" );
                                            textt.setVisibility(View.VISIBLE );
//                                            dialog.dismiss();
                                            String message=jsoncall.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
//                                    dialog.dismiss();
                                    Alert.shows(ExplainItemActivity.this,"","please check the connection","ok","");
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        dialog.dismiss();
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
                                               dialog.dismiss();
                                                recyclerViewDetail = (RecyclerView)findViewById(R.id.recyclerViewDetailItem);
                                                recyclerViewAddList(ExplainItemActivity.this,res,recyclerViewDetail);

                                            } catch (JSONException e) {
                                                e.printStackTrace();

                                                // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                                SendEror.sender(ExplainItemActivity.this,e.toString());

                                            }
                                            break;
                                        default:
                                            dialog.dismiss();
                                            textt.setText( "There is no Package" );
                                            textt.setVisibility(View.VISIBLE );
                                            String message=jsonstatus.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                     dialog.dismiss();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         dialog.dismiss();

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
