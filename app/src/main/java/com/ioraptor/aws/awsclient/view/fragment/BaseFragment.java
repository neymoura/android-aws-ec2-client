package com.ioraptor.aws.awsclient.view.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.ioraptor.aws.awsclient.event.BaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by neymoura on 10/09/17.
 */

public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFrg";

    @Override
    public void onStart() {
        super.onStart();
        try{
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }    
        }catch (EventBusException e){
            Log.e(TAG, "onStart: ", e);
        }
        
    }

    @Override
    public void onStop() {
        super.onStop();
        try{
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        }catch (EventBusException e) {
            Log.e(TAG, "onStart: ", e);
        }
    }

    @Subscribe
    public void onEvent(BaseEvent event){

    }

}
