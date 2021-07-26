package pro.kidss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import pro.kidss.R;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_child );
    }

    public void btDownloadApk(View view) {
        goToUrl( "https://play.google.com/store/apps/details?id=pro.kds.forkids.kidsguard" );
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse( url );
        Intent launchBrowser = new Intent( Intent.ACTION_VIEW, uriUrl );
        startActivity( launchBrowser );
    }
}
