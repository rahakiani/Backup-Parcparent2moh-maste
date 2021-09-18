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
import pro.kidss.database.Maindataa;
import pro.kidss.database.Romdb;
import pro.kidss.database.Roomdbb;
import pro.kidss.wlcome.WelcomeActivity;

public class CallsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecCall recCall;
    TextView name;
    List<Maindataa> all;
    List<String>dire;
    Romdb roomdb;
    String date,time;
    Maindataa mainData;
    ImageButton back;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calls );
        roomdb = Romdb.getInstance(this);
        name = findViewById( R.id.name );
        back = findViewById( R.id.lyt_back );

        Intent intent = getIntent();
        String namee = intent.getStringExtra( "NAME" );
        name.setText( namee);
        all = roomdb.mainDao().getcall( namee );
        int i =0;
        while (i<all.size()){
            mainData=all.get( i );
            date = mainData.getDate();
            time = mainData.getTime();
            i++;
        }
        dire =roomdb.mainDao().getdirect( namee );



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