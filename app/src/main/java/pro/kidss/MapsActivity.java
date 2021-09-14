package pro.kidss;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pro.kidss.wlcome.WelcomeActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    String x, y, Adress;
    private GoogleMap mMap;
    Dialog dialog;
    Button accept;
    TextView messageTv, titleTv;
    ImageView close;
    ArrayList<String> xx = new ArrayList<String>();
    ArrayList<String> yy = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        dialog = new Dialog( this );
        Showdialog();
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById( android.R.id.content )).getChildAt( 0 );
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra( "BUNDLE" );
        xx = (ArrayList<String>) args.getSerializable( "x" );
        yy = (ArrayList<String>) args.getSerializable( "y" );
        date = (ArrayList<String>) args.getSerializable( "date" );

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
    }

    private void Showdialog() {

        dialog.setContentView( R.layout.alert_wait );
        close = (ImageView) dialog.findViewById( R.id.close_accept );
        accept = (Button) dialog.findViewById( R.id.btnAccept );
        titleTv = (TextView) dialog.findViewById( R.id.title_go );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_acceot );
        titleTv.setText( "Please Wait" );
        messageTv.setText( "Connecting To Server..." );
        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );

        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog.show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int i = 0;
        while (i < xx.size()) {
            double lon = Double.parseDouble( xx.get( i ) );
            double la = Double.parseDouble( yy.get( i ) );
            Log.e( "cordin", "x:" + xx.get( i ) + "y:" + yy.get( i ) );
            LatLng sydney = new LatLng( lon, la );
            mMap.addMarker( new MarkerOptions().position( sydney ).title( date.get( i ) ) );
            if (i == 0) {
                mMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
                pointToPosition( sydney );
            }
            i++;
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( this, WelcomeActivity.class );
        startActivity( intent );
    }

    private void pointToPosition(LatLng position) {
        //Build camera position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target( position )
                .zoom( 17 ).build();
        //Zoom in and animate the camera.
        mMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
    }
}
