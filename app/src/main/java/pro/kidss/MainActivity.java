package pro.kidss;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import static com.google.android.material.tabs.TabLayout.*;

public class MainActivity extends AppCompatActivity implements OnTabSelectedListener, TabLayoutMediator.TabConfigurationStrategy {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );


        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById( R.id.tabLayout );

        //Adding the tabs using addTab() method
        tabLayout.addTab( tabLayout.newTab().setText( "Login" ) );
        tabLayout.addTab( tabLayout.newTab().setText( "Register" ) );

        tabLayout.setTabGravity( GRAVITY_FILL );

        //Initializing viewPager
        viewPager = (ViewPager) findViewById( R.id.pager );

        //Creating our pager adapter
        pager adapter = new pager( getSupportFragmentManager(), tabLayout.getTabCount() );

        //Adding adapter to pager
        viewPager.setAdapter( adapter );


        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener( this );
    }


    @Override
    public void onTabSelected(Tab tab) {
        viewPager.setCurrentItem( tab.getPosition() );
    }

    @Override
    public void onTabUnselected(Tab tab) {

    }

    @Override
    public void onTabReselected(Tab tab) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConfigureTab(@NonNull Tab tab, int position) {

    }
}

