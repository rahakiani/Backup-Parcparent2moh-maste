package pro.kidss;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewViddate extends RecyclerView.Adapter<RecyclerviewViddate.ViewHolder> {

    Context context;
    String typee;
    List<String> distincmsindate;

    public RecyclerviewViddate(Context context, List<String> distincmsindate, String type) {

        this.context = context;
        this.typee = type;


        this.distincmsindate = distincmsindate;


    }

    @NonNull
    @Override
    public RecyclerviewViddate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.carddate, parent, false );
        return new ViewHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewViddate.ViewHolder viewHolder, int position) {

        Log.e( "DADA", distincmsindate.get( position ) );
        viewHolder.txtdate.setText( distincmsindate.get( position ) );
        viewHolder.txtdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e( "DATEES", distincmsindate.get( position ) );
                Intent intent = new Intent( context, vidGaleryActivity.class );
                Log.e( "Typeeeclick", typee );
                intent.putExtra( "Date", distincmsindate.get( position ) );
                intent.putExtra( "Type", typee );


                context.startActivity( intent );
            }
        } );


    }

    @Override
    public int getItemCount() {
        return distincmsindate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate;

        private final View parent_view;

        public ViewHolder(@NonNull View view) {
            super( view );
            parent_view = view.findViewById( android.R.id.content );
            txtdate = view.findViewById( R.id.txtdate );

        }
    }
}
