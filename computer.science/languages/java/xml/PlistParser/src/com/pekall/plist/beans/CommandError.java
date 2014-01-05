package com.pekall.plist.beans;

import com.pekall.plist.Utils;

/**
 * Representing the errors after the client executing a command.
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class CommandError {

    /** Description of the error in the device's localized language */
    private String LocalizedDescription;

    /** Optional. Description of the error in US English */
    private String USEnglishDescription;

    /** The error domain */
    private String ErrorDomain;

    /** The error code */
    private int ErrorCode;

    public CommandError() {
    }

    public CommandError(String localizedDescription, String USEnglishDescription,
                        String errorDomain, int errorCode) {
        LocalizedDescription = localizedDescription;
        this.USEnglishDescription = USEnglishDescription;
        ErrorDomain = errorDomain;
        ErrorCode = errorCode;
    }

    public String getLocalizedDescription() {
        return LocalizedDescription;
    }

    public void setLocalizedDescription(String localizedDescription) {
        LocalizedDescription = localizedDescription;
    }

    public String getUSEnglishDescription() {
        return USEnglishDescription;
    }

    public void setUSEnglishDescription(String USEnglishDescription) {
        this.USEnglishDescription = USEnglishDescription;
    }

    public String getErrorDomain() {
        return ErrorDomain;
    }

    public void setErrorDomain(String errorDomain) {
        ErrorDomain = errorDomain;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandError)) return false;

        CommandError that = (CommandError) o;

        if (ErrorCode != that.ErrorCode) return false;
        if (!Utils.safeString(ErrorDomain)
                .equals(Utils.safeString(that.ErrorDomain))) return false;
        if (!Utils.safeString(LocalizedDescription)
                .equals(Utils.safeString(that.LocalizedDescription))) return false;
        return Utils.safeString(USEnglishDescription)
                .equals(Utils.safeString(that.USEnglishDescription));

    }

    @Override
    public int hashCode() {
        int result = Utils.safeString(LocalizedDescription).hashCode();
        result = 31 * result + Utils.safeString(USEnglishDescription).hashCode();
        result = 31 * result + Utils.safeString(ErrorDomain).hashCode();
        result = 31 * result + ErrorCode;
        return result;
    }

    @Override
    public String toString() {
        return "CommandError{" +
                "LocalizedDescription='" + LocalizedDescription + '\'' +
                ", USEnglishDescription='" + USEnglishDescription + '\'' +
                ", ErrorDomain='" + ErrorDomain + '\'' +
                ", ErrorCode=" + ErrorCode +
                '}';
    }
}
