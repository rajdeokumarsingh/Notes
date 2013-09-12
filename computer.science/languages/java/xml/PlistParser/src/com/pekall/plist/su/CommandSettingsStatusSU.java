package com.pekall.plist.su;

import com.pekall.plist.beans.CommandStatusMsg;

import java.util.Arrays;

/**
 * Status message for command Settings, just for SU
 */
public class CommandSettingsStatusSU extends CommandStatusMsg {

    private byte[] settingsSU;

    private String name;

    /**
     * 0 for android, 1 for ios
     */
    private Integer osType;

    private String description;

    public CommandSettingsStatusSU() {
    }

    public CommandSettingsStatusSU(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public byte[] getSettingsSU() {
        return settingsSU;
    }

    public void setSettingsSU(byte[] settingsSU) {
        this.settingsSU = settingsSU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandSettingsStatusSU)) return false;
        if (!super.equals(o)) return false;

        CommandSettingsStatusSU that = (CommandSettingsStatusSU) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (osType != null ? !osType.equals(that.osType) : that.osType != null) return false;
        if (!Arrays.equals(settingsSU, that.settingsSU)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (settingsSU != null ? Arrays.hashCode(settingsSU) : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (osType != null ? osType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandSettingsStatusSU{" +
                "settingsSU=" + Arrays.toString(settingsSU) +
                ", name='" + name + '\'' +
                ", osType=" + osType +
                ", description='" + description + '\'' +
                '}';
    }
}
