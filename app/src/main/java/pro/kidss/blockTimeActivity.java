package pro.kidss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import pro.kidss.R;

public class blockTimeActivity extends AppCompatActivity {
    EditText edtftime, edtstime;
    String appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_block_time );
        edtftime = (EditText) findViewById( R.id.edtftime );
        edtstime = (EditText) findViewById( R.id.edtstime );
        Intent intent = getIntent();
        appname = intent.getStringExtra( "appname" );

    }

    public void btnsendblock(View view) {
        String ftime = edtftime.getText().toString();
        String stime = edtstime.getText().toString();
        blockAppdb blockAppdb = new blockAppdb( blockTimeActivity.this );
        blockAppdb.Insertjs( appname + ":" + "1" + ":" + ftime + ":" + stime );


    }
}
