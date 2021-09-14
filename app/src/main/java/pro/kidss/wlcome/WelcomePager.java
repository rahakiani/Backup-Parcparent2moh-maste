package pro.kidss.wlcome;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import pro.kidss.wlcome.BlankFragmentwel1;
import pro.kidss.wlcome.BlankFragmentwel2;
import pro.kidss.wlcome.BlankFragmentwel3;
import pro.kidss.wlcome.BlankFragmentwel4;
import pro.kidss.wlcome.BlankFragmentwel5;
import pro.kidss.wlcome.BlankFragmentwel6;
import pro.kidss.wlcome.BlankFragmentwel7;
import pro.kidss.wlcome.BlankFragmentwel8;
import pro.kidss.wlcome.BlankFragmentwrl9;

public class WelcomePager extends FragmentStatePagerAdapter {


    int tabCount;

    public WelcomePager(FragmentManager fm, int tabCount) {
        super( fm );
        //Initializing tab count
        this.tabCount = tabCount;
    }


    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BlankFragmentwel1();
            case 1:
                return new BlankFragmentwel2();
            case 2:

                return new BlankFragmentwel3();
            case 3:
                return new BlankFragmentwel4();
            case 4:
                return new BlankFragmentwel5();
            case 5:
                return new BlankFragmentwel6();
            case 6:
                return new BlankFragmentwel7();
            case 7:
                return new BlankFragmentwel8();
            case 8:
                return new BlankFragmentwrl9();


            default:
                return null;
        }
    }
}