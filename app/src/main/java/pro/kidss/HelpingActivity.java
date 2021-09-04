package pro.kidss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.concurrent.ScheduledExecutorService;

public class HelpingActivity extends AppCompatActivity {
    ScheduledExecutorService scheduledExecutorService;
    private ViewPager viewPager;
    private static final int NUM_PAGES = 4;
    private int dotscount;
    private ImageView[] dots;
    private static final int MAX_STEP = 4;


    private MyViewPagerAdapter myViewPagerAdapter;
    private Button btnNext;
    private String about_title_array[] = {
            "Kids or Parent?",
            "Add Kids",
            "Account management",
            "Items"
    };
    private String about_description_array[] = {
            "You are able to use both kids and family versions",
            "You can add your children to the account",
            "This application allows you to manage your children's accounts.",
            "You can see all the features and items in the menu page. Also, all the items are described at the top of the page."
    };
    private int about_images_array[] = {
            R.drawable.ic_familuu,
            R.drawable.ic_addkids,
            R.drawable.ic_managechild,
            R.drawable.ic_item
    };

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_helping );
//        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//        startActivity(intent);

        initComponent();


    }

    private void initComponent() {
        viewPager = (ViewPager) findViewById( R.id.view_pager );
        btnNext = (Button) findViewById( R.id.btn_next );

        // adding bottom dots
        bottomProgressDots( 0 );

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter( myViewPagerAdapter );
        viewPager.addOnPageChangeListener( viewPagerPageChangeListener );
        btnNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    viewPager.setCurrentItem( current );
                } else {
                    Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                    startActivity( intent );
                }
            }
        } );

        ((ImageButton) findViewById( R.id.bt_close )).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                startActivity( intent );

            }
        } );

    }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById( R.id.layoutDots );
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView( this );
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( new ViewGroup.LayoutParams( width_height, width_height ) );
            params.setMargins( 10, 10, 10, 10 );
            dots[i].setLayoutParams( params );
            dots[i].setImageResource( R.drawable.shape_circle );
            dots[i].setColorFilter( getResources().getColor( R.color.grey_20 ), PorterDuff.Mode.SRC_IN );
            dotsLayout.addView( dots[i] );
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource( R.drawable.shape_circle );
            dots[current_index].setColorFilter( getResources().getColor( R.color.colorPrimary ), PorterDuff.Mode.SRC_IN );
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots( position );

            if (position == about_title_array.length - 1) {
                btnNext.setText( getString( R.string.GOT_IT ) );
                btnNext.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
                btnNext.setTextColor( Color.WHITE );
                btnNext.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( getApplicationContext(), Main2Activity.class );
                        startActivity( intent );
                    }
                } );

            } else {
                btnNext.setText( getString( R.string.NEXT ) );
                btnNext.setBackgroundColor( getResources().getColor( R.color.grey_10 ) );
                btnNext.setTextColor( getResources().getColor( R.color.grey_90 ) );
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

            View view = layoutInflater.inflate( R.layout.item_stepper_wizard, container, false );
            ((TextView) view.findViewById( R.id.title )).setText( about_title_array[position] );
            ((TextView) view.findViewById( R.id.description )).setText( about_description_array[position] );
            ((ImageView) view.findViewById( R.id.image )).setImageResource( about_images_array[position] );
            container.addView( view );

            return view;
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView( view );
        }
    }
}
