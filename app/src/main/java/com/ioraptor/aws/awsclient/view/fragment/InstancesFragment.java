package com.ioraptor.aws.awsclient.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.amazonaws.services.ec2.model.Instance;
import com.ioraptor.aws.awsclient.R;
import com.ioraptor.aws.awsclient.Utils.InstanceUtils;
import com.ioraptor.aws.awsclient.adapter.InstancesAdapter;
import com.ioraptor.aws.awsclient.event.EC2ServiceEvent;
import com.ioraptor.aws.awsclient.event.InstancesAdapterEvent;
import com.ioraptor.aws.awsclient.service.EC2Service;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by neymoura on 10/09/17.
 */

public class InstancesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private InstancesAdapter instancesAdapter;
    private View emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instancesAdapter = new InstancesAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instances, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        recyclerView = view.findViewById(R.id.recyclew_view);
        emptyView = view.findViewById(R.id.empty_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        instancesAdapter.setEmptyView(emptyView);
        recyclerView.setAdapter(instancesAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        EC2Service.requestInstances();
    }

    private void showManageInstanceDialog(final Instance instance){

        final ArrayAdapter<String> lifecycleOptions = InstanceUtils.getLifecycleOptions(getActivity(), instance);

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setTitle(getString(R.string.instance_options));

        builderSingle.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(lifecycleOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String strName = lifecycleOptions.getItem(which);

                switch (strName) {

                    case "Reboot":
                        EC2Service.requestReboot(instance.getInstanceId());
                        break;

                    case "Terminate":
                        EC2Service.requestTerminate(instance.getInstanceId());
                        break;

                    case "Stop":
                        EC2Service.requestStop(instance.getInstanceId());
                        break;

                    case "Start":
                        EC2Service.requestStart(instance.getInstanceId());
                        break;

                }

            }
        });

        builderSingle.show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EC2ServiceEvent event){

        switch (event.getType()) {

            case REQUEST_INSTANCES:

                @SuppressWarnings("unchecked") List<Instance> instances = (List<Instance>) event.getData();
                instancesAdapter.setItems(instances);
                swipeRefreshLayout.setRefreshing(false);

                break;

            default:

                swipeRefreshLayout.setRefreshing(true);
                EC2Service.requestInstances();
                break;

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InstancesAdapterEvent event){

        switch (event.getType()) {

            case INSTANCE_CLIKED:

                showManageInstanceDialog((Instance) event.getData());

                break;

            case INSTANCE_LONG_CLIKED:

                break;

        }

    }

}
