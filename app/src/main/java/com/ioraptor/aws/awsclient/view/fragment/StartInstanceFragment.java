package com.ioraptor.aws.awsclient.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.ioraptor.aws.awsclient.R;
import com.ioraptor.aws.awsclient.event.EC2ServiceEvent;
import com.ioraptor.aws.awsclient.service.EC2Service;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neymoura on 10/09/17.
 */

public class StartInstanceFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "StrInstFrg";

    private Spinner typesSpinner;
    private Spinner amiSpinner;
    private Spinner keyPairsSpinner;
    private Spinner securitySpinner;
    private Button startInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_instance, container, false);

        typesSpinner = view.findViewById(R.id.types_spinner);
        amiSpinner = view.findViewById(R.id.ami_spinner);
        keyPairsSpinner = view.findViewById(R.id.keypair_spinner);
        securitySpinner = view.findViewById(R.id.security_spinner);
        startInstance = view.findViewById(R.id.start_instance);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Types Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.instance_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(adapter);

       //Start instance
        startInstance.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        EC2Service.requestImages();
        EC2Service.requestKeyPairs();
        EC2Service.requestSecurityGroups();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EC2ServiceEvent event){

        switch (event.getType()) {

            case REQUEST_IMAGES:

                @SuppressWarnings("unchecked") List<Image> images = (List<Image>) event.getData();

                List<String> instanceNames = new ArrayList<>();
                for (Image image : images) {
                    instanceNames.add(image.getImageId());
                }

                buildSpinner(instanceNames, amiSpinner);

                break;

            case REQUEST_KEY_PAIRS:

                @SuppressWarnings("unchecked") List<KeyPairInfo> keyPairs = (List<KeyPairInfo>) event.getData();

                List<String> keyPairsNames = new ArrayList<>();
                for (KeyPairInfo keyPair : keyPairs) {
                    keyPairsNames.add(keyPair.getKeyName());
                }

                buildSpinner(keyPairsNames, keyPairsSpinner);

                break;

            case REQUEST_SECURITY_GROUPS:

                @SuppressWarnings("unchecked") List<SecurityGroup> securityGroups = (List<SecurityGroup>) event.getData();

                List<String> securityGroupNames = new ArrayList<>();
                for (SecurityGroup securityGroup : securityGroups) {
                    securityGroupNames.add(securityGroup.getGroupName());
                }

                buildSpinner(securityGroupNames, securitySpinner);

                break;

            case REQUEST_INSTANCE:

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.jobs_done);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

                break;

        }


    }

    private void buildSpinner(List<String> names, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.start_instance:

                String ami = (String) amiSpinner.getSelectedItem();
                String instanceType = (String) typesSpinner.getSelectedItem();
                String keyPair = (String) keyPairsSpinner.getSelectedItem();
                String securityGroup = (String) securitySpinner.getSelectedItem();

                EC2Service.requestInstance(ami, instanceType, keyPair, securityGroup);

                break;

        }

    }


}
