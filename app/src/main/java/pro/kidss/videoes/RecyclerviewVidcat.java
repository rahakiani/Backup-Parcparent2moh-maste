package pro.kidss.videoes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import pro.kidss.database.MsinData;
import pro.kidss.R;
import pro.kidss.database.Roomdb;

public class RecyclerviewVidcat extends RecyclerView.Adapter<RecyclerviewVidcat.ViewHolder> {
    ArrayList<String> imageUrls;
    Context context;

    FloatingActionButton removefab;
    ArrayList<String> ids;


    List<String> distincmsindata;
    Roomdb roomdb;

    public RecyclerviewVidcat(ArrayList<String> imageUrlList, Context context, FloatingActionButton fabremove, ArrayList<String> ids, ArrayList<String> type, List<String> distincmsindata, ArrayList<MsinData> dataList) {
        this.imageUrls = imageUrlList;
        this.context = context;
        this.removefab = fabremove;
        this.ids = ids;
        this.distincmsindata = distincmsindata;


    }

    @NonNull
    @Override
    public RecyclerviewVidcat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardcat, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewVidcat.ViewHolder viewHolder, int i) {
        long thumb = i * 1000;
        RequestOptions options = new RequestOptions().frame( thumb );


        if (distincmsindata.get( i ).equals( "camera" )) {
            viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.cam ) );
            viewHolder.txtdate.setText( "Camera" );
        } else if (distincmsindata.get( i ).equals( "com.whatsapp" )) {
            viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.whatsapp ) );
            viewHolder.txtdate.setText( "Whatsapp" );
        } else if (distincmsindata.get( i ).equals( "com.instagram.android" )) {
            viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.instagram ) );
            viewHolder.txtdate.setText( "instagram" );
        } else if (distincmsindata.get( i ).equals( "org.telegram.messenger" )) {
            viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.telegram ) );
            viewHolder.txtdate.setText( "Telegram" );
        } else if (distincmsindata.get( i ).equals( "com.facebook.katana" )) {
            viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_facebook__2_ ) );
            viewHolder.txtdate.setText( "Facebook" );
        } else if (distincmsindata.get( i ).equals( "ir.alibaba" )) {
            viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.alibaba ) );
            viewHolder.txtdate.setText( "علی بابا" );
        } else if (distincmsindata.get( i ).contains( "clubhouse" )) {
            viewHolder.img.setBackground( ContextCompat.getDrawable( context, R.drawable.imagess ) );
            viewHolder.txtdate.setText( "Clubhouse" );

        } else if (distincmsindata.get( i ).contains( "whatsapp" )) {
            viewHolder.txtdate.setText( "Whatsapp" );
            Glide.with( context ).load( imageUrls.get( i ) ).apply( options ).into( viewHolder.img );
        } else if (distincmsindata.get( i ).contains( "telegram" )) {
            viewHolder.txtdate.setText( "Telegram" );
            Glide.with( context ).load( imageUrls.get( i ) ).apply( options ).into( viewHolder.img );
        } else {
            Glide.with( context ).load( imageUrls.get( i ) ).apply( options ).into( viewHolder.img );
            viewHolder.txtdate.setText( distincmsindata.get( i ) );
        }
        viewHolder.img.setOnClickListener( new View.OnClickListener() {
            String type = distincmsindata.get( i );

            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, VideoDateActivity.class );
                Log.e( "TYPER", type );
                intent.putExtra( "Type", type );
                context.startActivity( intent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return distincmsindata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, imgcheck;

        TextView txtdate;


        private final View parent_view;

        public ViewHolder(@NonNull View view) {
            super( view );

            parent_view = view.findViewById( android.R.id.content );

            img = view.findViewById( R.id.imageView );
            imgcheck = view.findViewById( R.id.imgcheck );
            txtdate = view.findViewById( R.id.txtdate );


        }
    }
}
