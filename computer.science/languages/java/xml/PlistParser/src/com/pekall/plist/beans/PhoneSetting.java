package com.pekall.plist.beans;

/**
 * Settings
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PhoneSetting {

    /**
     * VoiceRoaming Modifies the Voice Roaming Setting.
     * The voice roaming setting is only available on certain carriers.
     * Disabling voice roaming also disables data roaming.
     */
    public static final String ITEM_VOICE_ROAMING = "VoiceRoaming";
    /**
     * DataRoaming Modifies the Data Roaming Setting
     */
    public static final String ITEM_DATA_ROAMING = "DataRoaming";

    /**
     * Name of the setting, See ITEM_VOICE_ROAMING, ITEM_DATA_ROAMING
     */
    private String Item;

    /**
     * Whether the setting is enable
     */
    private Boolean Enabled;

    public PhoneSetting() {
    }

    public PhoneSetting(String item, Boolean enabled) {
        Item = item;
        Enabled = enabled;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneSetting)) return false;

        PhoneSetting that = (PhoneSetting) o;

        if (Enabled != null ? !Enabled.equals(that.Enabled) : that.Enabled != null) return false;
        return !(Item != null ? !Item.equals(that.Item) : that.Item != null);

    }

    @Override
    public int hashCode() {
        int result = Item != null ? Item.hashCode() : 0;
        result = 31 * result + (Enabled != null ? Enabled.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneSetting{" +
                "Item='" + Item + '\'' +
                ", Enabled=" + Enabled +
                '}';
    }
}
