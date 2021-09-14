package pro.kidss.wlcome;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pro.kidss.R;

public class AboutAppActivity extends AppCompatActivity {
    TextView txtTeamapp, txtdescapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_aboutapp );
        txtTeamapp = (TextView) findViewById( R.id.txtTeamapp );
        txtdescapp = (TextView) findViewById( R.id.txtdescapp );

        Animation a = AnimationUtils.loadAnimation( AboutAppActivity.this, R.anim.animzoomin );
        a.reset();
        txtTeamapp.clearAnimation();
        txtTeamapp.startAnimation( a );
        txtdescapp.clearAnimation();
        txtdescapp.startAnimation( a );
        txtTeamapp.setText( R.string.aboutt );

    }
}
