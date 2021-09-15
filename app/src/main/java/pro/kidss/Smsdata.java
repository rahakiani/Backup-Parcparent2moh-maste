package pro.kidss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pro.kidss.database.MainData;
import pro.kidss.database.Roomdbb;

public class Smsdata extends RecyclerView.Adapter<Smsdata.ViewHolder> {
    List<MainData> all;
    Context context;
    String body,time;
    Roomdbb roomdb;
    private final int CHAT_ME = 100;
    private final int CHAT_YOU = 200;
    MainData mainData;
    List<String>sts;
    TextView text_content,contat;
    TextView text_time,timee;
    public Smsdata(Context context, List<MainData> all,List<String>sts) {
        this.all=all;
        this.context =context;
        this.sts=sts;
    }

    @NonNull
    @Override
    public Smsdata.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Smsdata.ViewHolder vh;
        if (sts.get( viewType ).equals( "INCOMING" )){
            View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_sms_nama, parent, false );
            vh = new ViewHolder( v );
        }else {
            View vv = LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_sms_me, parent, false );
            vh = new ViewHolder( vv );
        }

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull Smsdata.ViewHolder holder, int position) {
        roomdb = Roomdbb.getInstance( context );
        mainData = all.get( position );
        body = mainData.getBody();
        time = mainData.getDate();
if (sts.get( position ).equals( "INCOMING" )){
    text_content.setText( body );
    text_time.setText( time );

}else {
    contat.setText( body );
    timee.setText( time );
}





    }

    @Override
    public int getItemCount() {
        return sts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(@NonNull View v) {
            super( v );

           contat =v.findViewById( R.id.textt_cont );
            timee =v.findViewById( R.id.textt_time );
            text_content = v.findViewById( R.id.text_cont );
            text_time = v.findViewById( R.id.text_time );
//            text_content = v.findViewById( R.id.text_cont );
//            text_time = v.findViewById( R.id.text_time );
//            lyt_parent = v.findViewById( R.id.lyt_parent );
        }


    }

}
