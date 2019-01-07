package com.developer.ankit.quizzer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    public TabsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 : EngineeringFragment  engineeringFragment = new EngineeringFragment();
                        return engineeringFragment;

            case 1 : MedicalFragment medicalFragment = new MedicalFragment();
                        return medicalFragment;

            case 2 : FoundationFragment foundationFragment = new FoundationFragment();
                        return foundationFragment;
        }
        return null;
    }

    public CharSequence getPageTitle (int position ){

        switch (position){

            case 0 : return "Enginerring";
            case 1 : return "Medical";
            case 2 : return "Foundation";
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
