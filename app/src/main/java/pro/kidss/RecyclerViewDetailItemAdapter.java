package pro.kidss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pro.kidss.R;

public class RecyclerViewDetailItemAdapter extends RecyclerView.Adapter<RecyclerViewDetailItemAdapter.ViewHolder> {

    private ArrayList<String> itemName;
    private Context context;

    public RecyclerViewDetailItemAdapter(Context context, ArrayList<String> itemNames) {
        this.itemName = itemNames;
        this.context = context;
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
        CardView cv = (CardView) LayoutInflater.from( parent.getContext() ).inflate( R.layout.cardview_explainitem, parent, false );
        return new ViewHolder( cv );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardVieeww;

        TextView iittemName = (TextView) cardView.findViewById( R.id.txtDetailItem );
        iittemName.setText( itemName.get( position ) );

    }

    @Override
    public int getItemCount() {
        return itemName.size();
    }


}
