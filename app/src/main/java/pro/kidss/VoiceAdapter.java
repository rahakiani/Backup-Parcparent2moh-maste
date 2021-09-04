package pro.kidss;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoiceAdapter extends RecyclerView.Adapter<VoiceAdapter.ViewHolder> {
    private ArrayList<String> voiceUrl;
    private ArrayList<String> voiceName;
    private Context context;

    public VoiceAdapter(ArrayList<String> voiceUrl, ArrayList<String> voiceName, Context context) {
        this.voiceUrl = voiceUrl;
        this.voiceName = voiceName;
        this.context = context;
    }

    @NonNull
    @Override
    public VoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from( context ).inflate( R.layout.card_view_voi, parent, false );
        return new VoiceAdapter.ViewHolder( cv );
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceAdapter.ViewHolder holder, int position) {
        holder.download.setVisibility( View.VISIBLE );
        holder.txtmus.setText( voiceName.get( position ) );
        holder.txtmus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, Player_Voice.class );
                intent.putExtra( "Voice_name", voiceName );
                intent.putExtra( "Voice_address", voiceUrl );
                intent.putExtra( "Voice_position", position );
                Log.e( "VOICENAME", voiceName.toString() );
                Log.e( "VOICENAME", voiceUrl.toString() );
//                intent.putExtra( "Voice_url",voiceUrl.get( position ) );
                context.startActivity( intent );
            }
        } );
        holder.imgmus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, Player_Voice.class );
                intent.putExtra( "Voice_name", voiceName.get( position ) );
                intent.putExtra( "Voice_url", voiceUrl.get( position ) );
                context.startActivity( intent );

            }
        } );
        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertClose = new AlertDialog.Builder( context );
                alertClose.setMessage( "Do you want to delete the videos?" )
                        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //send
                                StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://im.kidsguard.ml/api/delete-voice/",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText( context, "Successfully removed", Toast.LENGTH_SHORT ).show();
//                                                loadvideo();


                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                        progressDialog.dismiss();
                                        Alert.shows( context, "", "please check the connection", "ok", "" );
                                        SendEror.sender( context, error.toString() );
                                    }

                                } ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put( "data", voiceUrl.get( position ) );
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue( context );
                                requestQueue.add( stringRequest );


                            }
                        } ).setNegativeButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                       geturls(RecordVoiceActivity.this);

                    }
                } ).show();
            }

        } );
        holder.download.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission( context, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {


                } else {
                    ActivityCompat.requestPermissions( (Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100 );
                }
                PRDownloader.initialize( context );
                PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                        .setDatabaseEnabled( true )
                        .build();
                PRDownloader.initialize( context, config );


                config = PRDownloaderConfig.newBuilder()
                        .setReadTimeout( 30_000 )
                        .setConnectTimeout( 30_000 )
                        .build();
                PRDownloader.initialize( context, config );
                int downloadId = PRDownloader.download( voiceUrl.get( position ), Environment.getExternalStorageDirectory() + "/" + "parent" + "/", namefail( voiceUrl.get( position ) ) )
                        .build()
                        .setOnStartOrResumeListener( new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {

                            }
                        } )
                        .setOnPauseListener( new OnPauseListener() {
                            @Override
                            public void onPause() {

                            }
                        } )
                        .setOnCancelListener( new OnCancelListener() {
                            @Override
                            public void onCancel() {

                            }
                        } )
                        .setOnProgressListener( new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                holder.download.setVisibility( View.GONE );
                                holder.progress.setVisibility( View.VISIBLE );


                                Log.e( "GGGGGGGG", "AAAAAAAAAA" );
                            }
                        } )
                        .start( new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
//                                roomdb.mainDao().adddown(vidaddress.get( i )  );
                                holder.progress.setVisibility( View.GONE );
                                holder.download.setImageResource( R.drawable.ic_done );
                                holder.download.setVisibility( View.VISIBLE );
                                holder.download.setClickable( false );

                                Toast.makeText( context, "The file you want was saved in the prent folder ", Toast.LENGTH_SHORT ).show();

                                Log.e( "ABBBBA", "AAAAAAAAAA" );


                            }


                            @Override
                            public void onError(Error error) {
                                holder.progress.setVisibility( View.GONE );

                                Toast.makeText( context, "please check your internet", Toast.LENGTH_SHORT ).show();
                                Log.e( "EDDDDDDDD", error.toString() );


                            }

                        } );


            }


            private String namefail(String url) {
                String filename = "";
                if (url.endsWith( ".mp3" )) {
                    filename = holder.txtmus.getText() + "KIDMUSIC" + ".mp3";
                }
                return filename;
            }


        } );


    }

    @Override
    public int getItemCount() {
        return voiceName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgmus;
        TextView txtmus;
        ImageButton delete, download;
        ProgressBar progress;


        public ViewHolder(@NonNull View view) {
            super( view );
            imgmus = view.findViewById( R.id.imageviewmus );
            txtmus = view.findViewById( R.id.voicename );
            delete = view.findViewById( R.id.delete_voice );
            download = view.findViewById( R.id.download );
            progress = view.findViewById( R.id.progress_bar );
        }
    }
}
