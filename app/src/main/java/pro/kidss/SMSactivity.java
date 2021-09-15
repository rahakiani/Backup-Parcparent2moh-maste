package pro.kidss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import pro.kidss.database.MainData;
import pro.kidss.database.Roomdb;
import pro.kidss.database.Roomdbb;

public class SMSactivity extends AppCompatActivity {
RecyclerView recyclerView;
Smsdata smsdata;
TextView name;
List<MainData>all;
List<String>sts;
Roomdbb roomdb;
String body,time;
MainData mainData;
ImageButton back;

    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_s_m_sactivity );
        roomdb = Roomdbb.getInstance(this);
        name = findViewById( R.id.name );
        back = findViewById( R.id.lyt_back );

        Intent intent = getIntent();
        String namee = intent.getStringExtra( "NAME" );
        name.setText( namee);
        all = roomdb.mainDao().getsms( namee );
        int i =0;
        while (i<all.size()){
            mainData=all.get( i );
            body = mainData.getBody();
            i++;
        }
        sts =roomdb.mainDao().getstatuss( namee,body );



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