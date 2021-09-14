package pro.kidss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pro.kidss.database.MainData;
import pro.kidss.database.Roomdbb;

public class Smsdata extends RecyclerView.Adapter<Smsdata.ViewHolder> {
    List<MainData> all;
    Context context;
    String body,time;
    Roomdbb roomdb;
    MainData mainData;
    public Smsdata(Context context, List<MainData> all) {
        this.all=all;
        this.context =context;
    }

    @NonNull
    @Override
    public Smsdata.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_sms_nama, parent, false );
        return new Smsdata.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Smsdata.ViewHolder holder, int position) {
        roomdb = Roomdbb.getInstance( context );
        mainData = all.get( position );
        body = mainData.getBody();
        time = mainData.getDate();
        holder.text_content.setText( body );
        holder.text_time.setText( time );
    }

    @Override
    public int getItemCount() {
        return all.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_content;
        public TextView text_time;
        public View lyt_parent;

        public ViewHolder(@NonNull View v) {
            super( v );
            text_content = v.findViewById( R.id.text_cont );
            text_time = v.findViewById( R.id.text_time );
            lyt_parent = v.findViewById( R.id.lyt_parent );
        }


    }
}
