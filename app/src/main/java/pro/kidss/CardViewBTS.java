package pro.kidss;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class CardViewBTS extends RecyclerView.Adapter<CardViewBTS.ViewHolder> {
    private final Context context;
    private final ArrayList<String> lac,cell,mcc,mnc,date;
    int i = 0;
    public CardViewBTS(Context context, ArrayList<String> lac, ArrayList<String> cell, ArrayList<String> mcc, ArrayList<String> mnc, ArrayList<String> date) {
        this.context = context;
        this.lac = lac;
        this.cell = cell;
        this.mcc = mcc;
        this.mnc = mnc;
        this.date=date;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardVieeww;

        public ViewHolder(CardView v) {
            super( v );
            cardVieeww = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardview, parent, false );
        return new ViewHolder( cv );
    }

    @Override
    public void onBindViewHolder(CardViewBTS.ViewHolder holder, final int position) {
        CardView cardView = holder.cardVieeww;
        TextView txtlac=cardView.findViewById(R.id.txtlac);
        TextView txtcell=cardView.findViewById(R.id.txtcell);
        TextView txtmnc=cardView.findViewById(R.id.txtmnc);
        TextView txtmcc=cardView.findViewById(R.id.txtmcc);
        TextView txtdate=cardView.findViewById(R.id.txtdate);
        ImageView img = cardView.findViewById( R.id.down_down );
        LinearLayout lin = cardView.findViewById( R.id.lindate );
        img.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        if (i==1){
                            lin.setVisibility( View.VISIBLE );
                            img.setImageResource( R.drawable.ic_up_24 );

                            i = 1;
                            Log.e( "II",String.valueOf( i ) );


                        }else if(i==2){
                            lin.setVisibility( View.GONE );
                            img.setImageResource( R.drawable.ic_baseline_keyboard_arrow_down_24 );

                            i=0;
                            Log.e( "III",String.valueOf( i ) );
                        }
//                        i = 1 ;

                    }
                },50 );

            }
        } );
        txtlac.setText(lac.get(position));
        txtcell.setText(cell.get(position));
        txtmnc.setText(mnc.get(position));
        txtmcc.setText(mcc.get(position));
        txtdate.setText(date.get(position));


    }

    @Override
    public int getItemCount() {
        return lac.size();
    }


}
