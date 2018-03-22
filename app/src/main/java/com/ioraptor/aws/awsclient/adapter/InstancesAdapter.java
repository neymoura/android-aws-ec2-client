package com.ioraptor.aws.awsclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.services.ec2.model.Instance;
import com.ioraptor.aws.awsclient.R;
import com.ioraptor.aws.awsclient.view.holder.InstanceViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neymoura on 10/09/17.
 */

public class InstancesAdapter extends RecyclerView.Adapter<InstanceViewHolder> {

    private List<Instance> items = new ArrayList<>();

    private View emptyView;

    @Override
    public InstanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instance, parent, false);
        return new InstanceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InstanceViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Instance> getItems() {
        return items;
    }

    public void setItems(@NonNull List<Instance> items) {

        this.items = items;

        notifyDataSetChanged();

        if (emptyView != null) {
            if (items.isEmpty()){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                emptyView.setVisibility(View.GONE);
            }
        }

    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

}
