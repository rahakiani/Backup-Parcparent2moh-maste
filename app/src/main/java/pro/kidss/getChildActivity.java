package pro.kidss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pro.kidss.csc.Recyclercalldate;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.database.Roomdb;
import pro.kidss.wlcome.WelcomeActivity;

import static android.content.ContentValues.TAG;

public class getChildActivity extends AppCompatActivity {
    ProgressDialog dialog = null;
    String activity = "1";
    private View parent_view;
    private View back_drop;
//    ProgressBarGIFDialog.Builder progressBarGIFDialog;
    private boolean rotate = false;
    private View lyt_add;
    Roomdb roomdb;

    String a = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_get_child );
        back_drop = findViewById( R.id.back_drop );
        roomdb =Roomdb.getInstance( this );
        parent_view = findViewById( android.R.id.content );
        back_drop = findViewById( R.id.back_drop );
        final FloatingActionButton fab_add = (FloatingActionButton) findViewById( R.id.fab_add );
        final FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        getchild( getChildActivity.this );
        lyt_add = findViewById( R.id.lyt_add );
        ViewAnimation.initShowOut( lyt_add );
        back_drop.setVisibility( View.GONE );

        roomdb.mainContact().deletcontact();
        Log.e( "TAG",roomdb.mainDao().getallvideo().toString() );
        roomdb.mainDaooo().deletcall();

        roomdb.mainDao().deletvideo();

        roomdb.mainDaoo().deletsms();



        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabMode( view );


            }


        } );
        back_drop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode( fab );
            }

            private void toggleFabMode(View v) {
                rotate = ViewAnimation.rotateFab( v, !rotate );
                if (rotate) {
                    ViewAnimation.showIn( lyt_add );

                    back_drop.setVisibility( View.VISIBLE );
                } else {
                    ViewAnimation.showOut( lyt_add );
                    back_drop.setVisibility( View.GONE );
                }
            }
        } );
        fab_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (intent.getStringExtra( "activity" ).isEmpty()) {
                } else {
                    if (intent.getStringExtra( "activity" ).equals( "welcome" )) {
                        a = "2";
                    } else {
                    }
                }
                Intent b9 = new Intent( getChildActivity.this, AddChildActivity.class );
                b9.putExtra( "activity", "child" + a );
                startActivity( b9 );
            }
        } );
//        progressBarGIFDialog= new ProgressBarGIFDialog.Builder(this);
//
//        progressBarGIFDialog.setCancelable(false)
//
//                .setTitleColor(R.color.colorPrimary) // Set Title Color (int only)
//
//                .setLoadingGif(R.drawable.loading) // Set Loading Gif
//
//                .setDoneGif(R.drawable.done) // Set Done Gif
//
//                .setDoneTitle("Done") // Set Done Title
//
//                .setLoadingTitle("Please wait...") // Set Loading Title
//
//                .build();

        dialog = ProgressDialog.show( getChildActivity.this, "please wait", "connecting to server...", true );

    }


    private void toggleFabMode(View view) {
        rotate = ViewAnimation.rotateFab( view, !rotate );
        if (rotate) {
            ViewAnimation.showIn( lyt_add );

            back_drop.setVisibility( View.VISIBLE );
        } else {
            ViewAnimation.showOut( lyt_add );
            back_drop.setVisibility( View.GONE );
        }
    }


    public void getchild(final Context context) {

        String url = "https://apisender.online/api/all-kids/";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e( "dsrrfdfs", response );

                        dialog.dismiss();
                        try {
                            JSONObject jsonchilldcondition = new JSONObject( response );
                            String status = jsonchilldcondition.getString( "status" );
                            if ("ok".equals( status )) {
                                JSONArray childnamearay = jsonchilldcondition.getJSONArray( "kidsName" );
                                JSONArray childtokenaray = jsonchilldcondition.getJSONArray( "kidsToken" );
                                final ArrayList<String> childtoken = new ArrayList<String>();
                                ArrayList<String> childname = new ArrayList<String>();
                                ArrayList<Boolean> activestatus = new ArrayList<Boolean>();

                                CtokenDataBaseManager ctokenDataBaseManager = new CtokenDataBaseManager( context );
                                String ctokenn = ctokenDataBaseManager.getctoken();
                                int i = 0;
                                while (i < childnamearay.length()) {
                                    if (childtokenaray.getString( i ).equals( "" )) {
//                                        roomdb.userDao().insert();

                                    } else {
                                        childname.add( childnamearay.getString( i ) );
                                        childtoken.add( childtokenaray.getString( i ) );
                                        activestatus.add( false );
                                    }
                                    i++;
                                }
                                int a;
                                if (ctokenn.equals( "12" )) {
                                    a = 0;
                                } else {
                                    a = 1;
                                    activestatus.set( childtoken.indexOf( ctokenn ), true );
                                }
                                RecyclerView recyclerViewgetChild = (RecyclerView) findViewById( R.id.recyclerViewGetChild );
                                RecyclerViewGetchildAdapter adapter = new RecyclerViewGetchildAdapter( childname, activestatus, childtoken, context, a );
                                recyclerViewgetChild.setAdapter( adapter );

                                LayoutAnimationController animation =
                                        AnimationUtils.loadLayoutAnimation( context, R.anim.layout_animation_fall_down );
                                recyclerViewgetChild.setLayoutAnimation( animation );
                                LinearLayoutManager layoutManager = new LinearLayoutManager( context );
                                recyclerViewgetChild.setLayoutManager( layoutManager );
                            } else {//                                    Alert.shows(context,"","please try again","ok","");
                                String message = jsonchilldcondition.getString( "message" );
                                SendEror.sender( getChildActivity.this, message );
                            }

                        } catch (JSONException e) {
                             dialog.dismiss();
                            Toast.makeText( context, e.toString(), Toast.LENGTH_SHORT ).show();
                            e.printStackTrace();
                            SendEror.sender( getChildActivity.this, e.toString() );
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 dialog.dismiss();
                Alert.shows( context, "", "please check the connection", "ok", "" );
                SendEror.sender( getChildActivity.this, error.toString() );

            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "parentToken", getToken( context ) );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( context );
        requestQueue.add( stringRequest );
    }

    public String getToken(Context context) {
        OwnerDataBaseManager own = new OwnerDataBaseManager( context );
        return own.getowner();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        //Toast.makeText(this, intent.getStringExtra("activity"), Toast.LENGTH_LONG).show();
        if (intent.getStringExtra( "activity" ).isEmpty()) {
            allert();
        } else {
            if (intent.getStringExtra( "activity" ).equals( "welcome" )) {
                startActivity( new Intent( getChildActivity.this, WelcomeActivity.class ) );
            } else {
                allert();
            }
        }
    }

    public void allert() {
        AlertDialog.Builder alertClose = new AlertDialog.Builder( getChildActivity.this );
        alertClose.setTitle( R.string.titleExitConfirm ).
                setMessage( R.string.bodyExitConfirm ).
                setPositiveButton( R.string.acceptExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent( getChildActivity.this, VoroodActivity.class );
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intent.putExtra( "EXIT", true );
                        startActivity( intent );
                    }
                } ).
                setNegativeButton( R.string.declineExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } ).show();
    }
}
