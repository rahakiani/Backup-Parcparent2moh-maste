package pro.kidss.file;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pro.kidss.R;
import pro.kidss.model.WhatsappModel;

public class FileManagerAdaptor extends RecyclerView.Adapter<FileManagerAdaptor.ViewHolder> {
    ArrayList<WhatsappModel> arrayList;
    ArrayList<WhatsappModel> selected;
    ArrayList<String> json = new ArrayList<String>();


    public FileManagerAdaptor(ArrayList<WhatsappModel> arrayListy) {
        this.selected = new ArrayList<>();
        this.arrayList = arrayListy;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardviewfile, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WhatsappModel whatsappModel = arrayList.get( position );
        holder.checkBox.setText( whatsappModel.getFileName() );

        holder.checkBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked = true) {

                    holder.checkBox.setTextColor( Color.WHITE );
                    json.add( String.valueOf( arrayList.get( position ) ) );
                } else {
                    selected.remove( whatsappModel );
                    holder.checkBox.setTextColor( Color.BLACK );
                    json.add( String.valueOf( arrayList.get( position ) ) );
                }

            }
        } );
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {

            super( itemView );
            textView = itemView.findViewById( R.id.Filename );
            checkBox = itemView.findViewById( R.id.checkboxfile );

        }
    }

    public ArrayList<String> getSelected() {
//        Log.e( "array name" ,selected.get( 0 ).getFileName() );
        return json;
    }
}
