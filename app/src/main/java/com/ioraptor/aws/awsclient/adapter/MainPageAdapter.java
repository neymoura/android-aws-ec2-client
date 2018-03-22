package com.ioraptor.aws.awsclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ioraptor.aws.awsclient.view.fragment.InstancesFragment;
import com.ioraptor.aws.awsclient.view.fragment.StartInstanceFragment;

/**
 * Created by neymoura on 10/09/17.
 */

public class MainPageAdapter extends FragmentStatePagerAdapter {

    public static final int INSTANCES = 0;
    public static final int START_INSTANCES = 1;

    private InstancesFragment instancesFragment;
    private StartInstanceFragment startInstanceFragment;

    public MainPageAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case INSTANCES:

                if (instancesFragment == null){
                    instancesFragment = new InstancesFragment();
                }

                return instancesFragment;

            case START_INSTANCES:

                if (startInstanceFragment == null){
                    startInstanceFragment = new StartInstanceFragment();
                }

                return startInstanceFragment;

            default:

                return null;

        }

    }

    @Override
    public int getCount() {
        return 2;
    }

}
