package pro.kidss.csc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pro.kidss.R;

public class Recyclercalldate extends RecyclerView.Adapter<Recyclercalldate.ViewHolder> {
    Context context;
    List<String> distic;
    public Recyclercalldate(Context context, List<String> distincmsindata) {
        this.distic=distincmsindata;
        this.context=context;
    }

    @NonNull
    @Override
    public Recyclercalldate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardsms, parent, false );
        return new Recyclercalldate.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclercalldate.ViewHolder holder, int position) {
        holder.txtdate.setText( distic.get( position ));
        holder.des.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CallsActivity.class);
                intent.putExtra( "NAME",distic.get( position ) );
                intent.setFlags( intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity( intent );

            }
        } );
        holder.txtdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CallsActivity.class);
                intent.putExtra( "NAME",distic.get( position ) );
                intent.setFlags( intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity( intent );
            }
        } );


    }

    @Override
    public int getItemCount() {
        return distic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate,des;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            txtdate = itemView.findViewById( R.id.name );
            des =itemView.findViewById( R.id.description );
        }
    }
}
