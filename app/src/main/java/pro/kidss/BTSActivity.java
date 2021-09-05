package pro.kidss;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import java.util.HashMap;
import java.util.Map;

public class BTSActivity extends AppCompatActivity {
    RecyclerView ryclrbts;
    ProgressDialog dialog = null;
    SwipeRefreshLayout swpref;
    ArrayList<String> lac,cell,mnc,mcc,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btsactivity);
        lac=new ArrayList<String>();
        cell=new ArrayList<String>();
        mnc=new ArrayList<String>();
        mcc=new ArrayList<String>();
        date=new ArrayList<String>();
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

                            JSONArray jsonArray=new JSONArray(response);
                            int i=0;
                            while (i<jsonArray.length()){
                                lac.add(new JSONObject(jsonArray.getJSONObject(i).getString("bts")).getString("lac"));
                                cell.add(new JSONObject(jsonArray.getJSONObject(i).getString("bts")).getString("cid"));
                                mnc.add(new JSONObject(jsonArray.getJSONObject(i).getString("bts")).getString("mnc"));
                                mcc.add(new JSONObject(jsonArray.getJSONObject(i).getString("bts")).getString("mcc"));
                                date.add(jsonArray.getJSONObject(i).getString("date"));
                                i++;
                            }
                            ryclrbts = (RecyclerView)findViewById(R.id.rcyclrbts);
                            CardViewBTS adapter = new CardViewBTS(BTSActivity.this,lac,cell,mcc,mnc,date);
                            ryclrbts.setAdapter(adapter);

                            LayoutAnimationController animation =
                                    AnimationUtils.loadLayoutAnimation(BTSActivity.this, R.anim.layout_animation_slide_from_left);
                            ryclrbts.setLayoutAnimation(animation);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(BTSActivity.this);
                            ryclrbts.setLayoutManager(layoutManager);




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
    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
}