package com.pekall.plist.beans;

/**
 * ApplyRedemptionCode Commands Install Paid Applications via Redemption Code
 */
public class CommandApplyRedemptionCode extends CommandObject {
    /**
     * The App ID returned by the InstallApplication command
     */
    private String Identifier;

    /**
     * The redemption code that applies to the app being installed.
     */
    private String RedemptionCode;

    public CommandApplyRedemptionCode() {
        super(CommandObject.REQ_TYPE_APPLY_REDEMPTION_CODE);
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getRedemptionCode() {
        return RedemptionCode;
    }

    public void setRedemptionCode(String redemptionCode) {
        RedemptionCode = redemptionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandApplyRedemptionCode)) return false;
        if (!super.equals(o)) return false;

        CommandApplyRedemptionCode that = (CommandApplyRedemptionCode) o;

        if (Identifier != null ? !Identifier.equals(that.Identifier) : that.Identifier != null) return false;
        if (RedemptionCode != null ? !RedemptionCode.equals(that.RedemptionCode) : that.RedemptionCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (Identifier != null ? Identifier.hashCode() : 0);
        result = 31 * result + (RedemptionCode != null ? RedemptionCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandApplyRedemptionCode{" +
                "super='" + super.toString() + '\'' +
                "Identifier='" + Identifier + '\'' +
                ", RedemptionCode='" + RedemptionCode + '\'' +
                '}';
    }
}
