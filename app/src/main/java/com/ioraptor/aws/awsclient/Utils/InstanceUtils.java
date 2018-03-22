package com.ioraptor.aws.awsclient.Utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateName;
import com.ioraptor.aws.awsclient.R;

/**
 * Created by neymoura on 11/09/17.
 */

public class InstanceUtils {

    public static ArrayAdapter<String> getLifecycleOptions(Context ctx, Instance instance){

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_selectable_list_item);

        /**
         * pending 16
         * running 32
         * shutting-down 48
         * terminated 64
         * stopping 80
         * stopped
         */

        InstanceState state = instance.getState();
        InstanceStateName stateName = InstanceStateName.fromValue(state.getName());

        if(stateName == InstanceStateName.Running){

            arrayAdapter.add(ctx.getString(R.string.reboot));
            arrayAdapter.add(ctx.getString(R.string.terminate));
            arrayAdapter.add(ctx.getString(R.string.stop));

        }else if (stateName == InstanceStateName.Stopped){

            arrayAdapter.add(ctx.getString(R.string.start));
            arrayAdapter.add(ctx.getString(R.string.terminate));

        }

        return arrayAdapter;

    }

}
