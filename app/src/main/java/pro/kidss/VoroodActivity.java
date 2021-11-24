package pro.kidss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.wlcome.WelcomeActivity;

public class VoroodActivity extends AppCompatActivity {
    int activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vorood );
        if (getIntent().getBooleanExtra( "EXIT", false )) {
            VoroodActivity.this.finish();
            System.exit( 0 );
        }
        activity = 1;
        final SharedPreferences shared = getSharedPreferences( "prefs", MODE_PRIVATE );
        final SharedPreferences.Editor editor = shared.edit();
        boolean isFirstRun = shared.getBoolean( "FIRSTRUN", true );
        if (isFirstRun) {
            activity = 2;

        }

        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                if (activity == 1) {
                    CtokenDataBaseManager ctoken = new CtokenDataBaseManager( VoroodActivity.this );
                    if (ctoken.getctoken().equals( "12" )) {
                        Log.e( "KIDDd", getctoken( VoroodActivity.this ) );
                        Intent intent = new Intent( VoroodActivity.this, HelpingActivity.class );
                        startActivity( intent );
                    } else {
                        Log.e( "KIDDd", getctoken( VoroodActivity.this ) );
                        Intent intent = new Intent( VoroodActivity.this, WelcomeActivity.class );
                        startActivity( intent );
                    }
                } else {
                    Log.e( "KIDDd", getctoken( VoroodActivity.this ) );
                    Intent intent = new Intent( VoroodActivity.this, HelpingActivity.class );
                    startActivity( intent );
                }
            }
        }, 3400 );
    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }
}