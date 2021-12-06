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
import java.util.Collections;
import java.util.List;

import pro.kidss.database.MsinData;
import pro.kidss.R;
import pro.kidss.database.Roomdb;

public class RecyclerviewVidcat extends RecyclerView.Adapter<RecyclerviewVidcat.ViewHolder> {
    ArrayList<String> imageUrls,type;
    Context context;

    FloatingActionButton removefab;
    ArrayList<String> ids;
    List<String> adreess;
    String imgg;


    Roomdb roomdb;
    MsinData msinData;
    public RecyclerviewVidcat(ArrayList<String> address, Context context, FloatingActionButton fabremove, ArrayList<String> ids, ArrayList<String> type) {
        this.imageUrls = address;
        this.context = context;
        this.removefab = fabremove;
        this.ids = ids;

        this.type=type;


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



        switch (type.get(i)){
            case "camera":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.cam));
                viewHolder.txtdate.setText("Camera");
                break;
            case "com.whatsapp":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.whatsapp));
                viewHolder.txtdate.setText("Whatsapp");
                break;
            case "com.instagram.android":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.instagram));
                viewHolder.txtdate.setText("instagram");
                break;
            case "org.telegram.messenger":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.telegram));
                viewHolder.txtdate.setText("Telegram");
                break;
            case "com.facebook.katana":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_facebook__2_));
                viewHolder.txtdate.setText("Facebook");
                break;
            case "ir.alibaba":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.alibaba));
                viewHolder.txtdate.setText("علی بابا");
                break;
                case "cab.snapp.passenger.play":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.sn));
                viewHolder.txtdate.setText("Snapp");
                break;
                case "com.clubhouse.app":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.imagess));
                viewHolder.txtdate.setText("Clubhouse");
                break;
                case "com.whatsapp.w4b":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.vidd));
                viewHolder.txtdate.setText("WhatsApp Video Call");
                break;
                case "com.google.android.gm":
                viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.gmail));
                viewHolder.txtdate.setText("Gmail");
                break;

            default:
                Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
                viewHolder.txtdate.setText( type.get( i ) );
                break;


        }





        viewHolder.img.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, VideoDateActivity.class );
                Log.e( "TYPER", type.get( i ) );
                intent.putExtra( "Type", type.get( i ) );
                context.startActivity( intent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
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
