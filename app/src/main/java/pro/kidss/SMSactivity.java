package pro.kidss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
Roomdbb roomdb;
String body,time;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_s_m_sactivity );
        roomdb = Roomdbb.getInstance(this);
        name = findViewById( R.id.name );
        Intent intent = getIntent();
        String namee = intent.getStringExtra( "NAME" );
        name.setText( namee);

        all = roomdb.mainDao().getsms( namee );

        recyclerView = findViewById( R.id.recyclerView );
        gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
        recyclerView.setLayoutManager( gridLayoutManager );
        smsdata=new Smsdata(getApplicationContext(),all);
        recyclerView.setAdapter( smsdata );
    }

}