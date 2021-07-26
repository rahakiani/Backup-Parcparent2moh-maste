package pro.kidss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import pro.kidss.R;
import pro.kidss.model.DataClass;

public class FileManager extends AppCompatActivity implements DataModel.OnGetResponse {
    CheckBox checkBox;
    Button button;
    Button accept;
    Button updatepage;
    TextView messageTv, titleTv, timer;
    ImageView close;

    ScrollView scrollView;
    private RecyclerView recyclerView;
    private FileManagerAdaptor exampleAdapter;
    View cardView;
    Dialog dialog;
    private FileAdapter fileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_file_manager );
       // Toast.makeText(this, "File manager", Toast.LENGTH_SHORT).show();
        dialog = new Dialog( this );
        updatepage = (Button) findViewById( R.id.updatefile );
        cardView = findViewById( R.id.cardviewfile );
        button = (Button) findViewById( R.id.bt_send );
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView_List );
       // parsDataa( FileManager.this );
       // recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        ShowDialog();
        Log.e( "KIDDd", getctoken( FileManager.this ) );
        parsDataa( this );
        updatepage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showslertt();
            }
        } );

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonparseee();
              //  ShowAret();
              //  createRequestParams();


            }
        } );       // updatepage.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Showslertt();
//
//            }
//        } );


    }

    private void Showslertt() {
        dialog.setContentView( R.layout.alert_wait );
        close = (ImageView) dialog.findViewById( R.id.close_accept );
        accept = (Button) dialog.findViewById( R.id.btnAccept );
        accept.setText( "Accept" );
        accept.setVisibility( View.VISIBLE );
        timer = (TextView) dialog.findViewById( R.id.text_timer );
        titleTv = (TextView) dialog.findViewById( R.id.title_go );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_acceot );
        titleTv.setText( "Please Wait" );
        messageTv.setText( "Connecting To Server..." );
        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonparsee();
            }

        } );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog.show();
    }

    private void jsonparsee() {
        StringRequest stringRequestt = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/request-updateFiles/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(FileManager.this, "Request sent", Toast.LENGTH_SHORT).show();
             //   dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowTry();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "kidToken", getctoken(FileManager.this) );


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequestt );
    }

    private void ShowAret() {
        dialog.setContentView( R.layout.alert_wait );
        close = (ImageView) dialog.findViewById( R.id.close_accept );
        accept = (Button) dialog.findViewById( R.id.btnAccept );
        timer = (TextView) dialog.findViewById( R.id.text_timer );
        titleTv = (TextView) dialog.findViewById( R.id.title_go );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_acceot );
        titleTv.setText( "Are you sure of your choice?" );
        messageTv.setText( "If you are satisfied, click on the accept button, otherwise click on the close button" );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonparseee();
            }
        } );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog.show();

    }

    private void jsonparseee() {
        ArrayList<String> json=new ArrayList<String>();
        json=fileAdapter.getfile();
        JSONArray jsonArray=new JSONArray();
        int i=0;
        while (i<json.size()){
            jsonArray.put(json.get(i));
            i++;
        }
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/request-selectedFiles/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("patinga", response);
                ShowAlert();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowTry();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "kidToken", getctoken(FileManager.this) );
                params.put( "file", jsonArray.toString() );

                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    private void ShowAlert() {
        dialog.setContentView( R.layout.alert_accept );
        close = (ImageView) dialog.findViewById( R.id.close_accept );
        accept = (Button) dialog.findViewById( R.id.btnAccept );
        timer = (TextView) dialog.findViewById( R.id.text_timer );
        titleTv = (TextView) dialog.findViewById( R.id.title_go );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_acceot );
        titleTv.setText( "Your request was successful" );
        messageTv.setText( "The files you requested were placed in your download folder" );
        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
    }

    private void ShowDialog() {
        dialog.setContentView( R.layout.alert_wait );
        close = (ImageView) dialog.findViewById( R.id.close_accept );
        accept = (Button) dialog.findViewById( R.id.btnAccept );
        timer = (TextView) dialog.findViewById( R.id.text_timer );
        titleTv = (TextView) dialog.findViewById( R.id.title_go );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_acceot );
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
                dialog.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );

        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog.show();
    }


    private void createRequestParams() {
        //  ArrayList<WhatsappModel> models = exampleAdapter.getSelected();
//        ArrayList<RequestModel> requestModels = new ArrayList<>();
//        for (WhatsappModel whatsappModel : models){
//            Log.e( "!!!" ,whatsappModel.getFileName() );
//            requestModels.add( new RequestModel(whatsappModel.getFileName()) );
//        }
        jsonparse();

    }

    private void jsonparse() {
        ArrayList<String> name = new ArrayList<String>();
        name = exampleAdapter.getSelected();
        JSONArray jsonArray = new JSONArray();
        int i = 0;
        while (i < name.size()) {
            jsonArray.put( name.get( i ) );
            i++;
        }
        StringRequest request = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/request-selectedFiles/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();


                Log.e( "response", response );

//                Log.e( "!!!",response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                ShowTry();
                Log.e( "!!!", error.getMessage() );
            }
        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "kidToken", getctoken(FileManager.this) );
                params.put( "file", jsonArray.toString() );
                Log.e( "eeee", jsonArray.toString() );

                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( request );
    }

    private void ShowTry() {
        dialog.setContentView( R.layout.try_alert );
        close = (ImageView) dialog.findViewById( R.id.close_try );
        accept = (Button) dialog.findViewById( R.id.bt_try );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_try );
        messageTv.setText( "please check the connection" );
        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), WelcomeActivity.class ) );
            }
        } );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog.show();
    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }


    @Override
    public void onGetResponse(DataClass dataClass) {
        exampleAdapter = new FileManagerAdaptor( dataClass.getArrayList() );
        recyclerView.setAdapter( exampleAdapter );
    }
    public void btnshow(View view){
        startActivity(new Intent(FileManager.this, FileManagerNamayesh.class));
    }



//    private void jsonparse() {
//        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/files-list/", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                dataModels = new ArrayList<>();
//                try {
//                    Log.e( "respnse" , response );
//
//                    JSONObject object = new JSONObject(response);
//                    JSONArray array = object.getJSONArray( "files" );
//                    for (int i=0;i<array.length();i++){
//                        JSONObject jsonfil = new JSONObject(array.getString( i ));
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        } ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<String, String>();
//                params.put( "KidToken", getctoken( FileManager.this ) );
//                return params;
//            }
//        };
//    }
//
//}
public void parsDataa(Context context) {
    String url = "https://im.kidsguard.ml/api/files-list/";
    StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
              //      Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    try {
                        ArrayList<String>filename=new ArrayList<String>();
                        ArrayList<String> path=new ArrayList<String>();
                        ArrayList<String> json=new ArrayList<String>();
                        DataClass dataClass = new DataClass();
                        JSONObject jsonObject = new JSONObject( response );
                        Log.e("patinga", jsonObject.toString() );
                        String data = jsonObject.getJSONArray( "files" ).getString( 0 );
                        JSONObject jsondata = new JSONObject( data );
                        JSONArray ArrayWhatsAppAudioPrivate = jsondata.getJSONArray( "WhatsAppAudioPrivate" );
                        int i = 0;
                        if (ArrayWhatsAppAudioPrivate.length()>0){

                        while (i < ArrayWhatsAppAudioPrivate.length()) {

                            String nameWhatsAppAudioPrivate = ArrayWhatsAppAudioPrivate.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppAudioPrivate.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(ArrayWhatsAppAudioPrivate.getJSONObject( i ).toString());
                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppAudio = jsondata.getJSONArray( "WhatsAppAudio" );
                        if (ArrayWhatsAppAudio.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppAudio.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppAudio.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppAudio.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(ArrayWhatsAppAudio.getJSONObject( i ).toString());

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppAudioSent = jsondata.getJSONArray( "WhatsAppAudioSent" );
                        if (ArrayWhatsAppAudioSent.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppAudioSent.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppAudioSent.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppAudioSent.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppAudioSent.getJSONObject( i )));
//                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppDocuments = jsondata.getJSONArray( "WhatsAppDocuments" );
                        if (ArrayWhatsAppDocuments.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppDocuments.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppDocuments.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppDocuments.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppDocuments.getJSONObject( i )));
                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppDocumentsPrivate = jsondata.getJSONArray( "WhatsAppDocumentsPrivate" );
                        if (ArrayWhatsAppDocumentsPrivate.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppDocumentsPrivate.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppDocumentsPrivate.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppDocumentsPrivate.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppDocumentsPrivate.getJSONObject( i )));

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppDocumentsSent = jsondata.getJSONArray( "WhatsAppDocumentsSent" );
                        i = 0;
                        if (ArrayWhatsAppDocumentsSent.length()>0){
                        while (i < ArrayWhatsAppDocumentsSent.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppDocumentsSent.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppDocumentsSent.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppDocumentsSent.getJSONObject( i )));

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppImages = jsondata.getJSONArray( "WhatsAppImages" );
                        if (ArrayWhatsAppImages.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppImages.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppImages.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppImages.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppImages.getJSONObject( i )));

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppImagesPrivate = jsondata.getJSONArray( "WhatsAppImagesPrivate" );
                        if (ArrayWhatsAppImagesPrivate.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppImagesPrivate.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppImagesPrivate.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppImagesPrivate.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppImagesPrivate.getJSONObject( i )));

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppImagesSent = jsondata.getJSONArray( "WhatsAppImagesSent" );
                        if (ArrayWhatsAppImagesSent.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppImagesSent.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppImagesSent.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppImagesSent.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppImagesSent.getJSONObject( i )));
                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppVideo = jsondata.getJSONArray( "WhatsAppVideo" );
                        if (ArrayWhatsAppVideo.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppVideo.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppVideo.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppVideo.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppVideo.getJSONObject( i )));

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppVideoPrivate = jsondata.getJSONArray( "WhatsAppVideoPrivate" );
                        if (ArrayWhatsAppVideoPrivate.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppVideoPrivate.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppVideoPrivate.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppVideoPrivate.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppVideoPrivate.getJSONObject( i )));

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayWhatsAppVideoSent = jsondata.getJSONArray( "WhatsAppVideoSent" );
                        if (ArrayWhatsAppVideoSent.length()>0){
                        i = 0;
                        while (i < ArrayWhatsAppVideoSent.length()) {
                            String nameWhatsAppAudioPrivate = ArrayWhatsAppVideoSent.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayWhatsAppVideoSent.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayWhatsAppVideoSent.getJSONObject( i )));

                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                        JSONArray ArrayCamera = jsondata.getJSONArray( "Camera" );
                        if (ArrayCamera.length()>0){
                        i = 0;
                        while (i < ArrayCamera.length()) {
                            String nameWhatsAppAudioPrivate = ArrayCamera.getJSONObject( i ).getString( "FileName" );
                            String pathWhatsAppAudioPrivate = ArrayCamera.getJSONObject( i ).getString( "path" );
                            filename.add(nameWhatsAppAudioPrivate);
                            path.add(pathWhatsAppAudioPrivate);
                            json.add(String.valueOf(ArrayCamera.getJSONObject( i )));
                            Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                            i++;
                        }}
                      //  RecyclerView filerec=(RecyclerView)findViewById(R.id.recyclerView_List);
                        fileAdapter=new FileAdapter(json,filename,path,FileManager.this);
                        recyclerView.setAdapter(fileAdapter);

                        LayoutAnimationController animation =
                                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_from_left);
                        recyclerView.setLayoutAnimation(animation);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(layoutManager);

                    }  catch (JSONException e) {
                        Log.e("patinga", e.toString() );
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }

    } ) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put( "kidToken", getctoken(context) );
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue( context );
    requestQueue.add( stringRequest );
}

}

