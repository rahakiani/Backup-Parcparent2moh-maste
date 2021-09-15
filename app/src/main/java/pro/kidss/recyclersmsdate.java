package pro.kidss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pro.kidss.database.MainData;
import pro.kidss.database.Roomdb;
import pro.kidss.database.Roomdbb;
import pro.kidss.videoes.RecyclerviewVidcat;

public class recyclersmsdate extends RecyclerView.Adapter<recyclersmsdate.ViewHolder> {
    List<String>distinc;
    private int animation_type = 0;
    Context context;
    Roomdbb roomdb;
    String bodyy;
    public recyclersmsdate(Context context, List<String> distincmsindata,String bod) {
        this.context=context;
        this.distinc = distincmsindata;
        this.bodyy = bod;


    }

    @NonNull
    @Override
    public recyclersmsdate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardsms, parent, false );
        return new recyclersmsdate.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


//        holder.des.setText( bodyy);
        holder.des.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SMSactivity.class);
                intent.putExtra( "NAME",distinc.get( position ) );
                intent.setFlags( intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity( intent );
            }
        } );
        holder.txtdate.setText( distinc.get( position ) );
        holder.txtdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SMSactivity.class);
                intent.putExtra( "NAME",distinc.get( position ) );
                intent.setFlags( intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity( intent );

            }
        } );
//        setAnimation( ((ViewHolder) holder).itemView, position);


    }

    @Override
    public int getItemCount() {
        return distinc.size();
    }
//    private int lastPosition = -1;
//    private boolean on_attach = true;
//
//    private void setAnimation(View view, int position) {
//        if (position > lastPosition) {
//            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
//            lastPosition = position;
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtdate,des;


        private final View parent_view;

        public ViewHolder(@NonNull View view) {
            super( view );

            parent_view = view.findViewById( android.R.id.content );


            des =view.findViewById( R.id.description );
            txtdate = view.findViewById( R.id.name );
        }
    }

}
