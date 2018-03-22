package com.ioraptor.aws.awsclient.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ioraptor.aws.awsclient.R;
import com.ioraptor.aws.awsclient.adapter.MainPageAdapter;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActv";

    private ViewPager viewPager;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_instances:
                viewPager.setCurrentItem(MainPageAdapter.INSTANCES);
                return true;

            case R.id.navigation_run:
                viewPager.setCurrentItem(MainPageAdapter.START_INSTANCES);
                return true;

        }

        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){

            case MainPageAdapter.INSTANCES:
                navigation.setSelectedItemId(R.id.navigation_instances);
                break;

            case MainPageAdapter.START_INSTANCES:
                navigation.setSelectedItemId(R.id.navigation_run);
                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
