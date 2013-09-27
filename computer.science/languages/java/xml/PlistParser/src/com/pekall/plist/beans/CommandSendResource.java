package com.pekall.plist.beans;

import java.util.Arrays;

public class CommandSendResource extends CommandObject {

    public static final String RES_TYPE_ADVERTISE = "advertise";
    public static final String ACTION_ADVERTISE = "com.pekall.action.ADVERTISE";

    private String ResourceType;

    private String Intent;

    private byte[] Data;

    public CommandSendResource() {
        super(CommandObject.REQ_TYPE_SEND_RES);
    }

    public String getResourceType() {
        return ResourceType;
    }

    public void setResourceType(String resourceType) {
        ResourceType = resourceType;
    }

    public String getIntent() {
        return Intent;
    }

    public void setIntent(String intent) {
        Intent = intent;
    }

    public byte[] getData() {
        return Data;
    }

    public void setData(byte[] data) {
        Data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandSendResource)) return false;
        if (!super.equals(o)) return false;

        CommandSendResource that = (CommandSendResource) o;

        if (ResourceType != null ? !ResourceType.equals(that.ResourceType) : that.ResourceType != null)
            return false;
        if (!Arrays.equals(Data, that.Data)) return false;
        if (Intent != null ? !Intent.equals(that.Intent) : that.Intent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ResourceType != null ? ResourceType.hashCode() : 0);
        result = 31 * result + (Intent != null ? Intent.hashCode() : 0);
        result = 31 * result + (Data != null ? Arrays.hashCode(Data) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandSendResource{" +
                "ResourceType='" + ResourceType + '\'' +
                ", Intent='" + Intent + '\'' +
                ", Data=" + Arrays.toString(Data) +
                '}';
    }
}
