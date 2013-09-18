package com.pekall.plist.beans;

/**
 * Roaming setting
 */
public class PayloadRoamingPolicy extends PayloadBase {
    private boolean voiceRoaming;
    private boolean dataRoaming;

    public boolean isDataRoaming() {
        return dataRoaming;
    }

    public void setDataRoaming(boolean dataRoaming) {
        this.dataRoaming = dataRoaming;
    }

    public boolean isVoiceRoaming() {
        return voiceRoaming;
    }

    public void setVoiceRoaming(boolean voiceRoaming) {
        this.voiceRoaming = voiceRoaming;
    }

    public PayloadRoamingPolicy() {
        setPayloadType(PAYLOAD_TYPE_ROAMING_POLICY);
    }

    public PayloadRoamingPolicy(boolean dataRoaming, boolean voiceRoaming) {
        this.dataRoaming = dataRoaming;
        this.voiceRoaming = voiceRoaming;
        setPayloadType(PAYLOAD_TYPE_ROAMING_POLICY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadRoamingPolicy)) return false;
        if (!super.equals(o)) return false;

        PayloadRoamingPolicy that = (PayloadRoamingPolicy) o;

        if (dataRoaming != that.dataRoaming) return false;
        if (voiceRoaming != that.voiceRoaming) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (voiceRoaming ? 1 : 0);
        result = 31 * result + (dataRoaming ? 1 : 0);
        return result;
    }
}
