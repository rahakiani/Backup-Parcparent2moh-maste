package pro.kidss.wlcome;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import pro.kidss.Alert;
import pro.kidss.csc.ExplainItemActivity;
import pro.kidss.MapsActivity;
import pro.kidss.OwnerDataBaseManager;
import pro.kidss.R;
import pro.kidss.SendEror;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.file.FileManager;
import pro.kidss.images.pictureActivity;
import pro.kidss.videoes.RecordVideoActivity;
import pro.kidss.voice.RecordVoiceActivity;

public class RecyclerViewAdapterWelcome extends RecyclerView.Adapter<RecyclerViewAdapterWelcome.ViewHolder> {
    ProgressDialog dialog = null;
    Intent i;
    private final ArrayList<String> Name;
    private final Context context;
    static final int REQUEST_CODE = 123;

    public RecyclerViewAdapterWelcome(ArrayList<String> name, Context context) {
        Name = name;
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardViieeww;

        public ViewHolder(CardView v) {
            super( v );
            cardViieeww = v;
        }
    }

    @NonNull
    @SuppressLint("ResourceAsColor")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_view_welcome, parent, false );
        return new ViewHolder( cv );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        TextView Namee = (TextView) cardView.findViewById( R.id.txtwelcome );
        Namee.setText( Name.get( position ) );

        ImageView aboveIcon = (ImageView) cardView.findViewById( R.id.ivIconWelcome );
        TextView Onvan = (TextView) cardView.findViewById( R.id.Readonvan );

//        switch (position) {
//            case 0:
//                aboveIcon.setImageResource( R.drawable.chatchat );
//                Onvan.setText( R.string.readsms );
//                break;
//            case 1:
//                aboveIcon.setImageResource( R.drawable.concon );
//                Onvan.setText( R.string.readcontact );
//                break;
//            case 2:
//                aboveIcon.setImageResource( R.drawable.teletele );
//                Onvan.setText( R.string.readphone );
//                break;
//
////            case 3:
////                aboveIcon.setImageResource(pro.kidss.R.drawable.ic_apps_black);
////                break;
////            case 4:
////                aboveIcon.setImageResource(pro.kidss.R.drawable.ic_phone_locked_black);
////                break;
////            case 5:
////                aboveIcon.setImageResource(pro.kidss.R.drawable.ic_block_black);
////                break;
//            case 3:
//                aboveIcon.setImageResource( R.drawable.camcam );
//                Onvan.setText( R.string.readcamera );
//                break;
//            case 4:
//                aboveIcon.setImageResource( R.drawable.mapmap );
//                Onvan.setText( R.string.readmaps );
//                break;
//            case 5:
//                aboveIcon.setImageResource( R.drawable.vidcalvidcal );
//                Onvan.setText( R.string.readvidcal );
//
//                break;
//            case 6:
//                aboveIcon.setImageResource( R.drawable.spospo );
//                Onvan.setText( R.string.readvoice );
//                break;
//            case 7:
//                aboveIcon.setImageResource( R.drawable.filemanager );
//                Onvan.setText( "View FileManager" );
//
//        }

        LinearLayout linearLayout = (LinearLayout) cardView.findViewById( R.id.welcomeLayout );
        linearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation shake = AnimationUtils.loadAnimation( context, R.anim.animzoomout );
                cardView.startAnimation( shake );
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        switch (position) {
                            case 0:
                                i = new Intent( context, ExplainItemActivity.class );
                                i.putExtra( "IntentName", "SMS Data" );
                                context.startActivity( i );
                                break;
                            case 1:
                                i = new Intent( context, ExplainItemActivity.class );
                                i.putExtra( "IntentName", "Contact Data" );
                                context.startActivity( i );
                                break;
                            case 2:
                                i = new Intent( context, ExplainItemActivity.class );
                                i.putExtra( "IntentName", "Call Data" );
                                context.startActivity( i );
                                break;
                          /*  case 3:

                                Intent i2 = new Intent(context,CurrentAppActivity.class);
                                context.startActivity(i2);
                                break;*/
//                            case 3:
//                                i = new Intent(context,ExplainItemActivity.class);
//                                i.putExtra("IntentName","InstalledPackage Data");
//                                context.startActivity(i);
//                                break;
//                            case 4:
//                                Intent i3 = new Intent(context,PhoneLockActivity.class);
//                                context.startActivity(i3);
//                                break;
//                            case 5:
//                                Intent i4 = new Intent(context,BlockAppActivity.class);
//                                context.startActivity(i4);
//                                break;
                            case 3:
                                Intent i5 = new Intent( context, pictureActivity.class );
                                context.startActivity( i5 );
                                break;
                            case 4:
                                dialog = ProgressDialog.show( context, context.getString( R.string.pleas_wait ), context.getString( R.string.connection_to_server ), true );
                                StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://apisender.online/api/coordinate-list/",
                                        new Response.Listener<String>() {
                                            @TargetApi(Build.VERSION_CODES.O)
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onResponse(String response) {

                                                dialog.dismiss();
                                                try {
                                                    JSONObject jsoncorr = new JSONObject( response );
                                                    String status = jsoncorr.getString( "status" );

                                                    if ("ok".equals( status )) {
                                                        Log.e( "orance", getctoken( context ) );
                                                        JSONArray xaray = jsoncorr.getJSONArray( "y" );
                                                        JSONArray yaray = jsoncorr.getJSONArray( "x" );
                                                        JSONArray datearray = jsoncorr.getJSONArray( "date" );
                                                        ArrayList<String> x = new ArrayList<String>();
                                                        ArrayList<String> y = new ArrayList<String>();
                                                        ArrayList<String> date1 = new ArrayList<String>();
                                                        int i = 0;
                                                        while (i < xaray.length()) {
                                                            Log.e( "iron", xaray.getString( i ) );
                                                            x.add( xaray.getString( i ) );
                                                            y.add( yaray.getString( i ) );
                                                            String[] all = datearray.getString( i ).split( "T" );
                                                            String[] date = all[0].split( "-" );
                                                            int year = Integer.parseInt( date[0] );
                                                            int mounth = Integer.parseInt( date[1] );
                                                            int day = Integer.parseInt( date[2] );
                                                            String[] time = all[1].split( ":" );
                                                            int hour = Integer.parseInt( time[0] );
                                                            int min = Integer.parseInt( time[1] );
                                                            Calendar mCalendar = new GregorianCalendar();
                                                            mCalendar.set( year, mounth, day, hour, min, 00 );
                                                            Calendar.Builder calendar = new Calendar.Builder();
                                                            calendar.setDate( year, mounth - 1, day );
                                                            calendar.setTimeOfDay( hour, min, 0 );
                                                            calendar.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
                                                            date1.add( String.valueOf( calendar.build().getTime() ) );
                                                            i++;
                                                        }

                                                        Intent intent = new Intent( context, MapsActivity.class );
                                                        Bundle args = new Bundle();
                                                        args.putSerializable( "x", (Serializable) x );
                                                        args.putSerializable( "y", (Serializable) y );
                                                        args.putSerializable( "date", (Serializable) date1 );
                                                        intent.putExtra( "BUNDLE", args );
                                                        context.startActivity( intent );
                                                    } else {
                                                        String message = jsoncorr.getString( "message" );
                                                        SendEror.sender( context, message );
                                                    }

                                                } catch (JSONException e) {
                                                    dialog.dismiss();
                                                    e.printStackTrace();
                                                    SendEror.sender( context, e.toString() );
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dialog.dismiss();
                                        Alert.shows( context, "", context.getString( R.string.please_check_the_connetion ), "ok", "" );
                                        SendEror.sender( context, error.toString() );


                                    }

                                } ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        // params.put("parentToken",getowner(context));
                                        params.put( "token", getctoken( context ) );
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue( context );
                                requestQueue.add( stringRequest );

                                break;
                            case 5:
                                Intent i6 = new Intent( context, RecordVideoActivity.class );
                                context.startActivity( i6 );
                                break;
                            case 6:
                                Intent i7 = new Intent( context, RecordVoiceActivity.class );

                                context.startActivity( i7 );
                                break;
                            case 7:
                                Intent i8 = new Intent( context, FileManager.class );
                                context.startActivity( i8 );
                            default:
                              //  throw new IllegalStateException( "Unexpected value: " + position );
                        }
                    }

                    //break;
//                            default:
//                                throw new IllegalStateException( "Unexpected value: " + position );
                }, 1000 );

            }
        } );
    }

    @Override
    public int getItemCount() {
        return Name.size();
    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }

    public String getowner(Context context) {
        OwnerDataBaseManager owne = new OwnerDataBaseManager( context );
        return owne.getowner();
    }

    public void allert(Context context, String inf) {
        AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setMessage( inf )
                .setCancelable( false )
                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                } );
        AlertDialog alert = builder.create();
        alert.show();

    }
}

