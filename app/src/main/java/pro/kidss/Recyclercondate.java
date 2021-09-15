package pro.kidss;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recyclercondate extends RecyclerView.Adapter<Recyclercondate.ViewHolder> {

    Context context;
    ArrayList<String>namee;
    ArrayList<String>numberr;
    public Recyclercondate(Context context, ArrayList<String>namee,ArrayList<String>number) {
        this.context=context;
        this.namee=namee;
        this.numberr = number;


    }

    @NonNull
    @Override
    public Recyclercondate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardsms, parent, false );
        return new Recyclercondate.ViewHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull Recyclercondate.ViewHolder holder, int position) {
        holder.txtdate.setText( namee.get( position ));
        holder.des.setText( numberr.get( position ));
//        holder.des.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClipboardManager myClickboard = (ClipboardManager) context.getSystemService( Context.CLIPBOARD_SERVICE );
//                ClipData myClip = ClipData.newPlainText( "text", holder.txtdate.getText().toString()+holder.des.getText().toString() );
//                myClickboard.setPrimaryClip( myClip );
//                Toast.makeText( context, "Copy complated", Toast.LENGTH_SHORT ).show();
//            }
//        } );
//        holder.txtdate.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClipboardManager myClickboard = (ClipboardManager) context.getSystemService( Context.CLIPBOARD_SERVICE );
//                ClipData myClip = ClipData.newPlainText( "text", holder.txtdate.getText().toString()+holder.des.getText().toString() );
//                myClickboard.setPrimaryClip( myClip );
//                Toast.makeText( context, "copy complated", Toast.LENGTH_SHORT ).show();
//            }
//        } );
    }

    @Override
    public int getItemCount() {
        return namee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate,des;
        public ViewHolder(@NonNull View view) {
            super( view );
            des =view.findViewById( R.id.description );
            txtdate = view.findViewById( R.id.name );
        }
    }
}