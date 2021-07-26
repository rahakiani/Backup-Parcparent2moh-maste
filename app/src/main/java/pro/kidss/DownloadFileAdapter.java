package pro.kidss;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pro.kidss.R;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadFileAdapter extends RecyclerView.Adapter<DownloadFileAdapter.ViewHolder> {
    Context context;
    private ArrayList<String> FileName;
    private ArrayList<String> url;
    DownloadManager downloadManager;
    public DownloadFileAdapter(Context context, ArrayList<String> fileName, ArrayList<String> url) {
        this.context = context;
        FileName = fileName;
        this.url = url;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;

        public ViewHolder(CardView v) {
            super( v );
            cardViieeww = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from( context ).inflate( R.layout.file_card_vieww, parent, false );
        return new ViewHolder( cv );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        TextView txtFilename=(TextView)cardView.findViewById(R.id.txtFilename);
        Button btndownload=(Button)cardView.findViewById(R.id.btndownload);
        txtFilename.setText(FileName.get(position));
        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse( url.get(position) );
                downloadManager = (DownloadManager) context.getSystemService( DOWNLOAD_SERVICE );
                DownloadManager.Request request = new DownloadManager.Request( uri );
                request.setTitle( "downloading" );
                request.setDescription( "wait" );
                request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, FileName.get(position) );
                long donid = downloadManager.enqueue( request );
              //  Toast.makeText( this, "please wait one minute", Toast.LENGTH_SHORT ).show();
                BroadcastReceiver time = new BroadcastReceiver() {
                    @RequiresApi(api = 29)
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();

                    }
                };
                IntentFilter intentFilter = new IntentFilter( DownloadManager.ACTION_DOWNLOAD_COMPLETE );
                context.registerReceiver( time, intentFilter );
            }
        });

    }

    @Override
    public int getItemCount() {
        return FileName.size();
    }


}


