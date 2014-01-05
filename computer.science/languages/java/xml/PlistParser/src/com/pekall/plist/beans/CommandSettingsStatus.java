package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Status message for command Settings
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandSettingsStatus extends CommandStatusMsg {
    /**
     * Settings array contains a result dictionary that corresponds
     * with each command that appeared in the original Settings array (in the request)
     */
    private List<SettingResult> Settings;


    public CommandSettingsStatus() {
    }

    public CommandSettingsStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public List<SettingResult> getSettings() {
        return Settings;
    }

    public void setSettings(List<SettingResult> settings) {
        Settings = settings;
    }

    public void addSettingResult(SettingResult result) {
        if (Settings == null) {
            Settings = new ArrayList<SettingResult>();
        }
        Settings.add(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandSettingsStatus)) return false;
        if (!super.equals(o)) return false;

        CommandSettingsStatus that = (CommandSettingsStatus) o;

        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if (Settings == null) {
            return result;
        }
        for (SettingResult setting : Settings) {
            result += setting.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (SettingResult setting : Settings) {
            sb.append(setting.toString()).append(", ");
        }
        sb.append("}");
        return "CommandSettingsStatus{" +
                "supper=" + super.toString() +
                "Settings=" + sb.toString() +
                '}';
    }
}
