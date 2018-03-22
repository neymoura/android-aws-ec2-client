package com.ioraptor.aws.awsclient.event;

/**
 * Created by neymoura on 11/09/17.
 */

public class InstancesAdapterEvent {

    public enum Type{
        INSTANCE_CLIKED,
        INSTANCE_LONG_CLIKED
    }

    private InstancesAdapterEvent.Type type;
    private Object data;

    public InstancesAdapterEvent(InstancesAdapterEvent.Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public InstancesAdapterEvent.Type getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

}
