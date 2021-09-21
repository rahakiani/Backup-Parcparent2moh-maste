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
import pro.kidss.database.Maindataa;
import pro.kidss.database.Roomdb;

public class CallsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecCall recCall;
    TextView name,numberr;
    List<Maindataa> all;
    List<String>dire;
    Roomdb roomdb;
    String date,time;
    Maindataa mainData;
    ImageButton back;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calls );
        roomdb = Roomdb.getInstance(this);
        name = findViewById( R.id.name );
        back = findViewById( R.id.lyt_back );
        numberr =findViewById( R.id.numberrr );
        Intent intent = getIntent();
        String namee = intent.getStringExtra( "NAME" );
        Log.e( "NAMEME",namee );
        String number = intent.getStringExtra( "Number" );
        name.setText( namee);
        numberr.setText( number);
        all = roomdb.mainDaooo().getcall( number );
        int i =0;
        while (i<all.size()){
            mainData=all.get( i );
            date = mainData.getDate();
            time = mainData.getTime();
            i++;
        }
        dire =roomdb.mainDaooo().getdirect( number );



        recyclerView = findViewById( R.id.recyclerView );
        gridLayoutManager = new GridLayoutManager( getApplicationContext(), 1 );
        recyclerView.setLayoutManager( gridLayoutManager );
        recCall=new RecCall(getApplicationContext(),all,dire);
        recyclerView.setAdapter( recCall );
    }

    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(),ExplainItemActivity.class);
        intent.putExtra( "IntentName","Call Data" );
        startActivity( intent );
    }
}