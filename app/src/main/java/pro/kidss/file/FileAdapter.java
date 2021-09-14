package pro.kidss.file;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pro.kidss.R;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private ArrayList<String> json,filename;
    private ArrayList<String> path;
    private Context context;
    ArrayList<String> selectfiles;

    public FileAdapter(ArrayList<String> json, ArrayList<String> filename, ArrayList<String> path, Context context) {
        this.json = json;
        this.filename = filename;
        this.path = path;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;

        public ViewHolder(CardView v) {
            super( v );
            cardViieeww = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from( context ).inflate( R.layout.cardviewfile, parent, false );
        return new ViewHolder( cv );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        selectfiles=new ArrayList<String>();
        CheckBox checkBox=(CheckBox)cardView.findViewById(R.id.checkboxfile);
        Log.e("patinga", filename.get(position) );
        checkBox.setText(filename.get(position));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectfiles.add(json.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return filename.size();
    }

    public ArrayList<String> getfile(){
        return selectfiles;
    }


}