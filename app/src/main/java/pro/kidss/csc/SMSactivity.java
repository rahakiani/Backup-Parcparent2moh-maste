package pro.kidss.csc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import pro.kidss.R;
import pro.kidss.database.MainData;
import pro.kidss.database.Roomdb;

public class SMSactivity extends AppCompatActivity {
RecyclerView recyclerView;
Smsdata smsdata;
TextView name,number;
List<MainData>all;
List<String>sts;
Roomdb roomdb;
String body,time;
MainData mainData;
ImageButton back;

    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_s_m_sactivity );
        roomdb = Roomdb.getInstance(this);
        name = findViewById( R.id.name );
        number = findViewById( R.id.numberer );
        back = findViewById( R.id.lyt_back );

        Intent intent = getIntent();
        String namee = intent.getStringExtra( "NAME" );
        Log.e( "NAMEME",namee );
        String numberr = intent.getStringExtra( "Number" );
        name.setText( namee);
        number.setText( numberr);
        all = roomdb.mainDaoo().getsms( numberr );
        int i =0;
        while (i<all.size()){
            mainData=all.get( i );
            body = mainData.getBody();
            i++;
        }
        sts =roomdb.mainDaoo().getstatuss( numberr,body );



        recyclerView = findViewById( R.id.recyclerView );
        gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
        recyclerView.setLayoutManager( gridLayoutManager );
        smsdata=new Smsdata(getApplicationContext(),all,sts);
        recyclerView.setAdapter( smsdata );
    }

    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(),ExplainItemActivity.class);
        intent.putExtra( "IntentName","SMS Data" );
        startActivity( intent );
    }
}