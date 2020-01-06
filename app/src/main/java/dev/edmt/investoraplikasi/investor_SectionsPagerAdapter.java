package dev.edmt.investoraplikasi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Windows on 2/26/2018.
 */

public class investor_SectionsPagerAdapter extends FragmentPagerAdapter {

    public investor_SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                investor_pageone pagee1 = new investor_pageone();
                return pagee1;

            case 1:
                investor_pagetwo pagee2 = new investor_pagetwo();
                return pagee2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Beranda";
            case 1:
                return "Tentang";
            default:
                return null;
        }
    }

}
