package pro.kidss;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Albums extends AppCompatActivity {
    private RecyclerView view_pager;
    private TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_albums );
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_menu );
        toolbar.getNavigationIcon().setColorFilter( getResources().getColor( R.color.grey_60 ), PorterDuff.Mode.SRC_ATOP );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Gallery" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

    }

    private void initComponent() {
        view_pager = (RecyclerView) findViewById( R.id.recycler_gallery );
//        setupViewPager(view_pager);

        tab_layout = (TabLayout) findViewById( R.id.tab_layout );
//        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter( getSupportFragmentManager() );
//        adapter.addFragment( FragmentTabsGallery.newInstance(), "ALL" );
//        adapter.addFragment( FragmentTabsGallery.newInstance(), "CAMERA" );
//        adapter.addFragment( FragmentTabsGallery.newInstance(), "RECENT" );
        viewPager.setAdapter( adapter );
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super( manager );
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get( position );
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add( fragment );
            mFragmentTitleList.add( title );
        }
    }
}