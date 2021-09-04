package pro.kidss;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Roomdb roomdb;
    ArrayList<MsinData> dataList = new ArrayList<>();
    ArrayList<String> dataaddres = new ArrayList<>();
    ArrayList<String> datadate = new ArrayList<>();
    GridLayoutManager gridLayoutManager;
    RecyclerviewGallery dataAdapter;
    FloatingActionButton fabremove;
    CoordinatorLayout coordinatorLayout;
    MsinData msinData;
    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gallery );
        roomdb = Roomdb.getInstance( this );
        coordinatorLayout = (CoordinatorLayout) findViewById( R.id.coordinatorr );

        int i = 0;

        dialog1 = new Dialog( this );
        ShowAlert();
        dataList.addAll( roomdb.mainDao().getlike() );
        while (i < dataList.size()) {
            msinData = dataList.get( i );
            dataaddres.add( msinData.getAddress() );
            datadate.add( msinData.getDate() );
            Log.e( "DATAADDRESS", dataaddres.toString() );
            i++;

        }
        recyclerView = findViewById( R.id.recycler_galler );
        dialog1.dismiss();


        gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
        recyclerView.setLayoutManager( gridLayoutManager );
        dataAdapter = new RecyclerviewGallery( dataList, GalleryActivity.this, fabremove, dataaddres, coordinatorLayout, datadate );
        recyclerView.setAdapter( dataAdapter );
    }


    private void ShowAlert() {
        dialog1.setContentView( R.layout.alert_accept );
        ImageView close = (ImageView) dialog1.findViewById( R.id.close_accept );
        Button accept = (Button) dialog1.findViewById( R.id.btnAccept );
        TextView timer = (TextView) dialog1.findViewById( R.id.text_timer );
        TextView titleTv = (TextView) dialog1.findViewById( R.id.title_go );
        TextView messageTv = (TextView) dialog1.findViewById( R.id.messaage_acceot );

        titleTv.setText( "coming soon" );
        messageTv.setText( "This section will be placed in the next updates of the application" );
        timer.setVisibility( View.GONE );

        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        } );

        dialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog1.show();
    }
}
