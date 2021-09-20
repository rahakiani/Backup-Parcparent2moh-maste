package pro.kidss.csc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pro.kidss.R;
import pro.kidss.database.Maindataa;
import pro.kidss.database.Romdb;

public class RecCall extends RecyclerView.Adapter<RecCall.ViewHolder> {
    Context context;
     List<Maindataa> all;
      List<String> dire;
      String time;
    Maindataa mainData;
    Romdb roomdb;
      String date,name;
    public RecCall(Context context, List<Maindataa> all, List<String> dire) {
        this.context=context;
        this.all=all;
        this.dire = dire;
        this.time=time;
        this.date=date;

    }

    @NonNull
    @Override
    public RecCall.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardcalls, parent, false );
        return new RecCall.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RecCall.ViewHolder holder, int position) {
        roomdb = Romdb.getInstance(context);
        mainData=all.get( position );
        date = mainData.getDate();
        time = mainData.getTime();
        name = mainData.getNumber();
        holder.namee.setText( name );

        holder.datee.setText( date );
        if (time.equals( "0" )) {
            holder.timee.setVisibility( View.GONE );
        }
        int in = Integer.valueOf(time);
        int saat = in/60;
        int minutes = in % 60;
        holder.timee.setText( saat+":"+minutes );


        if (dire.get( position ).equals( "MISSED" )){
            holder.img.setImageResource( R.drawable.ic_misscall );



        }else if (dire.get( position ).equals( "INCOMING" )){
            holder.img.setImageResource( R.drawable.ic_incoming );

        }else {
            holder.img.setImageResource( R.drawable.ic_outing );
        }

    }

    @Override
    public int getItemCount() {
        return all.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView namee,datee,timee;
        public ViewHolder(@NonNull View v) {
            super( v );
            img=v.findViewById( R.id.direc );
            namee=v.findViewById( R.id.name );
            datee=v.findViewById( R.id.description );
            timee=v.findViewById( R.id.time );
        }
    }
}
