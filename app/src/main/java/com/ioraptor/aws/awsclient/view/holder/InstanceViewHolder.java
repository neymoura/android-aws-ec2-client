package com.ioraptor.aws.awsclient.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.services.ec2.model.Instance;
import com.ioraptor.aws.awsclient.R;
import com.ioraptor.aws.awsclient.event.InstancesAdapterEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by neymoura on 10/09/17.
 */

public class InstanceViewHolder extends RecyclerView.ViewHolder {

    private View container;
    private TextView name;
    private TextView status;

    public InstanceViewHolder(View itemView) {

        super(itemView);

        container = itemView.findViewById(R.id.container);
        name = itemView.findViewById(R.id.name);
        status = itemView.findViewById(R.id.status);

    }

    public void bind(final Instance instance){

        name.setText(instance.getInstanceId());
        status.setText(instance.getState().getName());

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InstancesAdapterEvent event = new InstancesAdapterEvent(InstancesAdapterEvent.Type.INSTANCE_CLIKED, instance);
                EventBus.getDefault().post(event);

            }
        });

        container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                InstancesAdapterEvent event = new InstancesAdapterEvent(InstancesAdapterEvent.Type.INSTANCE_LONG_CLIKED, instance);
                EventBus.getDefault().post(event);

                return true;

            }
        });

    }

}
