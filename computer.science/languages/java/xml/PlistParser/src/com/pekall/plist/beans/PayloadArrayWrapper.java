package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representing an profile to install.
 * IOS payload in which "PayloadContent" contains an payload array.
 */
@SuppressWarnings("UnusedDeclaration")
public class PayloadArrayWrapper extends PayloadBase {

    /**
     * Optional. Set to true if there is a removal passcode.
     */
    private Boolean HasRemovalPasscode;

    /**
     * Optional. Set to true if the profile is encrypted.
     */
    private Boolean IsEncrypted;

    /**
     * Optional. Array of payload dictionaries. Not present if IsEncrypted is true.
     */
    private List<PayloadBase> PayloadContent;

    /**
     * Optional. If present and set to true, the user cannot delete the profile
     * (unless the profile has a removal password and the user provides it).
     */
    private Boolean PayloadRemovalDisallowed;

    /**
     * Determines if the profile should be installed for the system or the user.
     */
    private Boolean PayloadScope;

    /**
     * Optional. The date on which the profile will be automatically removed.
     */
    private Date RemovalDate;

    /**
     * FIXME: seem it should be a Long or Integer
     * Optional. Number of seconds until the profile is automatically removed.
     */
    private Float DurationUntilRemoval;

    // TODO: can not find in document
    // private Dictionary ConsentText;

    public PayloadArrayWrapper() {
        super();
    }

    public void addPayLoadContent(PayloadBase content) {
        if (PayloadContent == null) {
            PayloadContent = new ArrayList<PayloadBase>();
        }
        PayloadContent.add(content);
    }

    public Boolean getHasRemovalPasscode() {
        return HasRemovalPasscode;
    }

    public void setHasRemovalPasscode(Boolean hasRemovalPasscode) {
        HasRemovalPasscode = hasRemovalPasscode;
    }

    public Boolean getIsEncrypted() {
        return IsEncrypted;
    }

    public void setIsEncrypted(Boolean isEncrypted) {
        IsEncrypted = isEncrypted;
    }

    public List<PayloadBase> getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(List<PayloadBase> payloadContent) {
        PayloadContent = payloadContent;
    }

    public Boolean getPayloadRemovalDisallowed() {
        return PayloadRemovalDisallowed;
    }

    public void setPayloadRemovalDisallowed(Boolean payloadRemovalDisallowed) {
        PayloadRemovalDisallowed = payloadRemovalDisallowed;
    }

    public Boolean getPayloadScope() {
        return PayloadScope;
    }

    public void setPayloadScope(Boolean payloadScope) {
        PayloadScope = payloadScope;
    }

    public Date getRemovalDate() {
        return RemovalDate;
    }

    public void setRemovalDate(Date removalDate) {
        RemovalDate = removalDate;
    }

    public Float getDurationUntilRemoval() {
        return DurationUntilRemoval;
    }

    public void setDurationUntilRemoval(Float durationUntilRemoval) {
        DurationUntilRemoval = durationUntilRemoval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadArrayWrapper)) return false;
        if (!super.equals(o)) return false;

        PayloadArrayWrapper that = (PayloadArrayWrapper) o;

        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if (PayloadContent != null) {
            for (PayloadBase base : PayloadContent) {
                result += base.hashCode();
            }
        }
        result = 31 * result + (HasRemovalPasscode != null ? HasRemovalPasscode.hashCode() : 0);
        result = 31 * result + (IsEncrypted != null ? IsEncrypted.hashCode() : 0);
        result = 31 * result + (PayloadRemovalDisallowed != null ? PayloadRemovalDisallowed.hashCode() : 0);
        result = 31 * result + (PayloadScope != null ? PayloadScope.hashCode() : 0);
        result = 31 * result + (RemovalDate != null ? RemovalDate.toString().hashCode() : 0);
        result = 31 * result + (DurationUntilRemoval != null ? DurationUntilRemoval.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (PayloadContent != null) {
            sb.append("{");
            for (PayloadBase payloadBase : PayloadContent) {
                sb.append(payloadBase.toString()).append("\n");
            }
            sb.append("}");
        }
        return "PayloadArrayWrapper{" +
                "super=" + super.toString() +
                "HasRemovalPasscode=" + HasRemovalPasscode +
                ", IsEncrypted=" + IsEncrypted +
                ", PayloadContent=" + sb.toString() +
                ", PayloadRemovalDisallowed=" + PayloadRemovalDisallowed +
                ", PayloadScope=" + PayloadScope +
                ", RemovalDate=" + RemovalDate +
                ", DurationUntilRemoval=" + DurationUntilRemoval +
                '}';
    }
}
