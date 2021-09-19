package pro.kidss.wlcome;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mmstq.progressbargifdialog.ProgressBarGIFDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import pro.kidss.AddChildActivity;
import pro.kidss.Alert;
import pro.kidss.BTSActivity;
import pro.kidss.database.CtokenDataBaseManager;
import pro.kidss.DateConverter;
import pro.kidss.ExplainItemActivity;
import pro.kidss.file.FileManager;
import pro.kidss.LoginActivity;
import pro.kidss.MapsActivity;
import pro.kidss.OwnerDataBaseManager;
import pro.kidss.R;
import pro.kidss.videoes.RecordVideoActivity;
import pro.kidss.voice.RecordVoiceActivity;
import pro.kidss.SendEror;
import pro.kidss.VoroodActivity;
import pro.kidss.getChildActivity;
import pro.kidss.images.pictureActivity;


public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    private DrawerLayout drawer;

    Dialog dialog1;
    ProgressBarGIFDialog.Builder progressBarGIFDialog;
    JSONArray jsonArray;
    View parent_view;
    FloatingActionButton contacts, sms, calls, voice, photo, video, file, location, albums, bts;

    ScheduledExecutorService scheduledExecutorService;
    private ViewPager viewPagerr;
    private static final int NUM_PAGES = 7;
    private int dotscountt;
    private ImageView[] dotts;


    private ViewPager mPager;
    private int current_position = 0;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_welcome );
        dialog1 = new Dialog( this );
        progressBarGIFDialog= new ProgressBarGIFDialog.Builder(this);


        contacts = findViewById( R.id.contacts_activ );
        file = findViewById( R.id.file_activ );
        calls = findViewById( R.id.calls_activ );
        voice = findViewById( R.id.voice_activ );
        parent_view = findViewById( android.R.id.content );
        video = findViewById( R.id.video_activ );
        photo = findViewById( R.id.photo_activ );
        sms = findViewById( R.id.sms_activ );
        location = findViewById( R.id.location_activ );
        albums = findViewById( R.id.albums_activ );
        bts = findViewById( R.id.bts_activ );


        contacts.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), ExplainItemActivity.class );
                intent.putExtra( "IntentName", "Contact Data" );
                startActivity( intent );

            }
        } );
        calls.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), ExplainItemActivity.class );
                intent.putExtra( "IntentName", "Call Data" );
                startActivity( intent );
            }
        } );
        sms.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), ExplainItemActivity.class );
                intent.putExtra( "IntentName", "SMS Data" );
                startActivity( intent );
            }
        } );
        photo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), pictureActivity.class );
                startActivity( intent );
            }
        } );
        location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbarsucess();
//                jsoparse();


            }

            private void jsoparse() {
                showpro();
                StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://apisender.online/api/coordinate-list/",
                        new Response.Listener<String>() {
                            @TargetApi(Build.VERSION_CODES.O)
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(String response) {

                                progressBarGIFDialog.clear();
                                try {
                                    JSONObject jsoncorr = new JSONObject( response );
                                    String status = jsoncorr.getString( "status" );

                                    if ("ok".equals( status )) {

                                        JSONArray xaray = jsoncorr.getJSONArray( "y" );
                                        JSONArray yaray = jsoncorr.getJSONArray( "x" );
                                        JSONArray datearray = jsoncorr.getJSONArray( "date" );
                                        ArrayList<String> x = new ArrayList<String>();
                                        ArrayList<String> y = new ArrayList<String>();
                                        ArrayList<String> date1 = new ArrayList<String>();
                                        int i = 0;
                                        while (i < xaray.length()) {
                                            Log.e( "iron", xaray.getString( i ) );
                                            x.add( xaray.getString( i ) );
                                            y.add( yaray.getString( i ) );
                                            String[] all = datearray.getString( i ).split( "T" );
                                            String[] date = all[0].split( "-" );
                                            int year = Integer.parseInt( date[0] );
                                            int mounth = Integer.parseInt( date[1] );
                                            int day = Integer.parseInt( date[2] );
                                            String[] time = all[1].split( ":" );
                                            int hour = Integer.parseInt( time[0] );
                                            int min = Integer.parseInt( time[1] );
                                            Calendar mCalendar = new GregorianCalendar();
                                            mCalendar.set( year, mounth, day, hour, min, 00 );
                                            Calendar.Builder calendar = new Calendar.Builder();
                                            calendar.setDate( year, mounth - 1, day );
                                            calendar.setTimeOfDay( hour, min, 0 );
                                            calendar.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
                                            date1.add( String.valueOf( calendar.build().getTime() ) );
                                            i++;
                                        }

                                        Intent intent = new Intent( context, MapsActivity.class );
                                        Bundle args = new Bundle();
                                        args.putSerializable( "x", (Serializable) x );
                                        args.putSerializable( "y", (Serializable) y );
                                        args.putSerializable( "date", (Serializable) date1 );
                                        intent.putExtra( "BUNDLE", args );
                                        context.startActivity( intent );
                                    } else {
                                        String message = jsoncorr.getString( "message" );
                                        SendEror.sender( context, message );
                                    }

                                } catch (JSONException e) {
                                    progressBarGIFDialog.clear();
                                    e.printStackTrace();
                                    SendEror.sender( context, e.toString() );
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarGIFDialog.clear();
                        Alert.shows( context, "", context.getString( R.string.please_check_the_connetion ), "ok", "" );
                        SendEror.sender( context, error.toString() );


                    }

                } ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        // params.put("parentToken",getowner(context));
                        params.put( "token", getctoken( context ) );
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue( context );
                requestQueue.add( stringRequest );
            }
        } );
        bts.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), BTSActivity.class );
                startActivity( intent );
            }
        } );
        video.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), RecordVideoActivity.class );
                startActivity( intent );

            }
        } );
        voice.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), RecordVoiceActivity.class );
                startActivity( intent );
            }
        } );
        file.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), FileManager.class );
                startActivity( intent );
            }
        } );
//        albums.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),GalleryActivity.class);
//                startActivity( intent );
//            }
//        } );


        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbarNavBar );
        setSupportActionBar( toolbar );
        drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( (NavigationView.OnNavigationItemSelectedListener) this );

        /**
         * The pager widget, which handles animation and allows swiping horizontally to access previous
         * and next wizard steps.
         */
        mPager = (ViewPager) findViewById( R.id.viewpage );
        WelcomePager sc = new WelcomePager( getSupportFragmentManager(), 9 );
        mPager.setAdapter( sc );
        dotscountt = sc.getCount();
        dotts = new ImageView[dotscountt];
        creatSlideshow();
        for (int i = 0; i < dotscountt; i++) {

            dotts[i] = new ImageView( this );
            dotts[i].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), R.drawable.none ) );

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );

            params.setMargins( 8, 0, 8, 0 );

//            sliderDotspanel.addView( dotts[i], params );

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

//        ArrayList<String> name = new ArrayList<String>();
//        name.add( getString( R.string.sms ) );
//        name.add( getString( R.string.contacts ) );
//        name.add( getString( R.string.call ) );
////        name.add("All Apps");
////        name.add("Lock phone");
////        name.add("Block apps");
//        name.add( getString( R.string.takepic ) );
//        name.add( getString( R.string.location ) );
//        name.add( getString( R.string.takevid ) );
//        name.add( getString( R.string.takevoice ) );
//        name.add( getString( R.string.filemanage ) );
//        RecyclerView recyclerViewwelcome = (RecyclerView) findViewById( R.id.recyclerViewwelcone );
//        RecyclerViewAdapterWelcome adapter = new RecyclerViewAdapterWelcome( name, WelcomeActivity.this );
//        recyclerViewwelcome.setAdapter( adapter );
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation( WelcomeActivity.this, R.anim.layout_animation_fall_down );
//        recyclerViewwelcome.setLayoutAnimation( animation );
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager( 4, StaggeredGridLayoutManager.VERTICAL );
//        recyclerViewwelcome.setLayoutManager( layoutManager );
    }

    private void showpro() {
        progressBarGIFDialog.setCancelable(false)

                .setTitleColor(R.color.colorPrimary) // Set Title Color (int only)

                .setLoadingGif(R.drawable.loading) // Set Loading Gif

                .setDoneGif(R.drawable.done) // Set Done Gif

                .setDoneTitle("Done") // Set Done Title

                .setLoadingTitle("Please wait...") // Set Loading Title

                .build();
    }

    private void Snackbarsucess() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please use the original version of the application" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.blue_grey_400 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }


    private void ShowAlert() {
        dialog1.setContentView( R.layout.alert_accept );
        ImageView close = (ImageView) dialog1.findViewById( R.id.close_accept );
        Button accept = (Button) dialog1.findViewById( R.id.btnAccept );
        TextView timer = (TextView) dialog1.findViewById( R.id.text_timer );
        TextView titleTv = (TextView) dialog1.findViewById( R.id.title_go );
        TextView messageTv = (TextView) dialog1.findViewById( R.id.messaage_acceot );

        titleTv.setText( "coming soon" );
        messageTv.setText( "This section will be placed in the next updates of the application" );
        timer.setVisibility( View.GONE );

        close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        } );

        dialog1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog1.show();
    }


//    private void initNavigationMen() {
//        drawer = findViewById(R.id.drawer_layoutt);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//        };
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        // open drawer at start
//        drawer.openDrawer(GravityCompat.START);
//    }


    /**
     *
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.main_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId())
//        {
//            case R.id.search_action:
//                // Here's the code
//                return true;
//            case R.id.report_action:
//                //Hear's the code
//                return true;
//            case R.id.setting_action:
//                //Hear's the code
//                return true;
//                default:
//                    return super.onOptionsItemSelected(item);
//        }
//
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case  R.id.nav_check_online:
                ShowDialog();
                break;
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
            case R.id.Albums:
                Intent b = new Intent( this, GalleryActivity.class );
                startActivity( b );
                break;

            case R.id.nav_aboutApp:
                Intent b4 = new Intent( this, AboutAppActivity.class );
                startActivity( b4 );
                break;
            case R.id.Contact_us:
                showDialogImage();
                break;
            case R.id.nav_Exit:
                OwnerDataBaseManager ownerDataBaseManager = new OwnerDataBaseManager( WelcomeActivity.this );
                ownerDataBaseManager.delall();
                CtokenDataBaseManager ctok = new CtokenDataBaseManager( WelcomeActivity.this );
                ctok.delall();
                Intent b5 = new Intent( this, LoginActivity.class );
                startActivity( b5 );
                break;


        }


        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void ShowDialog() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://apisender.online/api/btsList/",
                new Response.Listener<String>() {
//                    @TargetApi(Build.VERSION_CODES.N)
//                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {

                        try {
                            jsonArray=new JSONArray(response);
                            final Dialog dialog2 = new Dialog( WelcomeActivity.this );
                            dialog2.setContentView( R.layout.contactus );
                            TextView tst = dialog2.findViewById( R.id.testre );
                            TextView em = dialog2.findViewById( R.id.text_email );
                            ImageView im = dialog2.findViewById( R.id.img_email );
                            tst.setText( "Last time device was online is :" );
                            String date = jsonArray.getJSONObject(jsonArray.length()-1).getString("date");
                            String[] all =  date.split( "T" );
                            String[] datee = all[0].split( "-" );
                            int year = Integer.parseInt( datee[0] );
                            int mounth = Integer.parseInt( datee[1] );
                            int day = Integer.parseInt( datee[2] );
                            String[] time = all[1].split( ":" );
                            int hour = Integer.parseInt( time[0] );
                            int min = Integer.parseInt( time[1] );
                            Calendar callForDate = Calendar.getInstance();
                            callForDate.set( year, mounth, day, hour, min, 00 );
                            callForDate.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
                            java.text.SimpleDateFormat currentDate = new java.text.SimpleDateFormat( "dd-MMMM-yyyy" );
                            DateConverter converter = new DateConverter();
                            converter.gregorianToPersian( callForDate.get( Calendar.YEAR ), callForDate.get( Calendar.MONTH ), callForDate.get( Calendar.DAY_OF_MONTH ) );
                            em.setText( String.valueOf( converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay() + " <----> " + callForDate.getTime().getHours() + ":" + callForDate.getTime().getMinutes() + ":" + callForDate.getTime().getSeconds() ) );
//
                            im.setVisibility( View.GONE );
                            dialog2.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
                            dialog2.setCancelable( true );
                            (dialog2.findViewById( R.id.bt_close )).setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog2.dismiss();
                                }
                            } );
                            dialog2.show();


//                            Toast.makeText(WelcomeActivity.this, "last time device was online is "+jsonArray.getJSONObject(jsonArray.length()-1).getString("date"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {

                            e.printStackTrace();
                            SendEror.sender(WelcomeActivity.this,e.toString());


                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarGIFDialog.clear();
                Alert.shows(WelcomeActivity.this,"","please check the connection","ok","");
                SendEror.sender(WelcomeActivity.this,error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("kidToken",getctoken(WelcomeActivity.this));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(WelcomeActivity.this);
        requestQueue.add(stringRequest);

    }

    private void showDialogImage() {
        final Dialog dialog = new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE ); // before
        dialog.setContentView( R.layout.contactus );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        dialog.setCancelable( true );
        (dialog.findViewById( R.id.bt_close )).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
        dialog.show();
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
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                handler.post( runnable );


            }
        }, 5000, 7000 );

    }


    public void AddKid(View view) {
        Intent b1 = new Intent( this, AddChildActivity.class );
        b1.putExtra( "activity", "welcome" );
        startActivity( b1 );

    }

    public void Mychilds(View view) {
        Intent b2 = new Intent( this, getChildActivity.class );
        b2.putExtra( "activity", "welcome" );
        startActivity( b2 );

    }

    public void about(View view) {
        Intent b4 = new Intent( this, AboutAppActivity.class );
        startActivity( b4 );
    }

    public void contaaCT(View view) {
        showDialogImage();
    }

    public void Linear(View view) {
        Intent intent = new Intent( getApplicationContext(), WelcomeActivity.class );
        startActivity( intent );


    }

    /**
     *
     */
    public void btncheck(View view){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://apisender.online/api/btsList/",
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            Toast.makeText(WelcomeActivity.this, "last time device was online is "+jsonArray.getJSONObject(jsonArray.length()-1).getString("date"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            progressBarGIFDialog.clear();
                            e.printStackTrace();
                            SendEror.sender(WelcomeActivity.this,e.toString());


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarGIFDialog.clear();
                Alert.shows(WelcomeActivity.this,"","please check the connection","ok","");
                SendEror.sender(WelcomeActivity.this,error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("kidToken",getctoken(WelcomeActivity.this));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(WelcomeActivity.this);
        requestQueue.add(stringRequest);
    }
}