package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to managed settings
 */
public class CommandSettings extends CommandObject {
    /**
     * Array of dictionaries. see PhoneSetting
     */
    List<PhoneSetting> Settings;

    public CommandSettings() {
        super(CommandObject.REQ_TYPE_SETTINGS);
    }

    public List<PhoneSetting> getSettings() {
        return Settings;
    }

    public void setSettings(List<PhoneSetting> settings) {
        Settings = settings;
    }

    public void addSetting(PhoneSetting setting) {
        if (Settings == null) {
            Settings = new ArrayList<PhoneSetting>();
        }
        Settings.add(setting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandSettings)) return false;
        if (!super.equals(o)) return false;

        CommandSettings that = (CommandSettings) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if (Settings == null) {
            return result;
        }
        for (PhoneSetting setting : Settings) {
            if(setting == null) continue;

            result += setting.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (PhoneSetting setting : Settings) {
            sb.append(setting.toString() + ",");
        }
        sb.append("}");
        return "CommandSettings{" +
                "super=" + super.toString()+
                "Settings=" + sb.toString()+
                '}';
    }
}
