package pro.kidss;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import pro.kidss.R;


public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ScheduledExecutorService scheduledExecutorService;
    private ViewPager viewPagerr;
    private static final int NUM_PAGES = 7;
    private int dotscountt;
    private ImageView[] dotts;


    private ViewPager mPager;
    private Timer timer;
    private int current_position = 0;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_welcome );

        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbarNavBar );
        setSupportActionBar( toolbar );


        drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        LinearLayout sliderDotspanel = (LinearLayout) findViewById( R.id.Sliderddots );
        /**
         * The pager widget, which handles animation and allows swiping horizontally to access previous
         * and next wizard steps.
         */
        mPager = (ViewPager) findViewById( R.id.viewpage );
        WelcomePager sc = new WelcomePager( getSupportFragmentManager(), 8 );
        mPager.setAdapter( sc );
        dotscountt = sc.getCount();
        dotts = new ImageView[dotscountt];
        creatSlideshow();
        for (int i = 0; i < dotscountt; i++) {

            dotts[i] = new ImageView( this );
            dotts[i].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), R.drawable.none ) );

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );

            params.setMargins( 8, 0, 8, 0 );

            sliderDotspanel.addView( dotts[i], params );

        }
        dotts[0].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), R.drawable.nonetwo ) );
        mPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscountt; i++) {
                    dotts[i].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), R.drawable.none ) );
                }

                dotts[position].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), R.drawable.nonetwo ) );

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        ArrayList<String> name = new ArrayList<String>();
        name.add( getString( R.string.sms ) );
        name.add( getString( R.string.contacts ) );
        name.add( getString( R.string.call ) );
//        name.add("All Apps");
//        name.add("Lock phone");
//        name.add("Block apps");
        name.add( getString( R.string.takepic ) );
        name.add( getString( R.string.location ) );
        name.add( getString( R.string.takevid ) );
        name.add( getString( R.string.takevoice ) );
        name.add( getString( R.string.filemanage ) );
        RecyclerView recyclerViewwelcome = (RecyclerView) findViewById( R.id.recyclerViewwelcone );
        RecyclerViewAdapterWelcome adapter = new RecyclerViewAdapterWelcome( name, WelcomeActivity.this );
        recyclerViewwelcome.setAdapter( adapter );
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation( WelcomeActivity.this, R.anim.layout_animation_fall_down );
        recyclerViewwelcome.setLayoutAnimation( animation );
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL );
        recyclerViewwelcome.setLayoutManager( layoutManager );
    }


    /**
     *
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.main_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.search_action:
                // Here's the code
                return true;
            case R.id.report_action:
                //Hear's the code
                return true;
            case R.id.setting_action:
                //Hear's the code
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_addChild:
                Intent b1 = new Intent( this, AddChildActivity.class );
                b1.putExtra( "activity", "welcome" );
                startActivity( b1 );
                break;
            case R.id.nav_otherChild:
                Intent b2 = new Intent( this, getChildActivity.class );
                b2.putExtra( "activity", "welcome" );
                startActivity( b2 );
                break;
            case R.id.nav_aboutApp:
                Intent b4 = new Intent( this, AboutAppActivity.class );
                startActivity( b4 );
                break;
            case R.id.Contact_us:
                Intent b3 = new Intent( this, Contact_us.class );
                startActivity( b3 );
                break;
            case R.id.nav_Exit:
                OwnerDataBaseManager ownerDataBaseManager = new OwnerDataBaseManager( WelcomeActivity.this );
                ownerDataBaseManager.delall();
                CtokenDataBaseManager ctok = new CtokenDataBaseManager( WelcomeActivity.this );
                ctok.delall();
                Intent b5 = new Intent( this, MainActivity.class );
                startActivity( b5 );
                break;

        }


        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @Override
    public void onBackPressed() {

      /*  if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        AlertDialog.Builder alertClose = new AlertDialog.Builder( WelcomeActivity.this );
        alertClose.setTitle( R.string.titleExitConfirm ).
                setMessage( R.string.bodyExitConfirm ).
                setPositiveButton( R.string.acceptExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent( WelcomeActivity.this, VoroodActivity.class );
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intent.putExtra( "EXIT", true );
                        startActivity( intent );
                    }
                } ).
                setNegativeButton( R.string.declineExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } ).show();
        if (viewPagerr.getCurrentItem() == 0) {

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        }


    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager( context );
        return ctok.getctoken();
    }

    public String getowner(Context context) {
        OwnerDataBaseManager owne = new OwnerDataBaseManager( context );
        return owne.getowner();
    }

    private void creatSlideshow() {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == 8)
                    current_position = 0;
                mPager.setCurrentItem( current_position++, true );

            }
        };
        timer = new Timer();
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                handler.post( runnable );


            }
        }, 5000, 7000 );

    }

    /**
     *
     */


}