package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * A result for Settings command
 */
@SuppressWarnings("UnusedDeclaration")
public class SettingResult {
    /**
     * Status of the command.
     * Only Acknowledged and Error are reported.
     */
    private String Status;

    /**
     * Optional. An array representing the chain of errors that occurred.
     */
    private List<CommandError> ErrorChain;

    public SettingResult() {
    }

    public SettingResult(String status, List<CommandError> errorChain) {
        Status = status;
        ErrorChain = errorChain;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<CommandError> getErrorChain() {
        return ErrorChain;
    }

    public void setErrorChain(List<CommandError> errorChain) {
        ErrorChain = errorChain;
    }

    public void addError(CommandError error) {
        if (ErrorChain == null) {
            ErrorChain = new ArrayList<CommandError>();
        }
        ErrorChain.add(error);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SettingResult)) return false;

        SettingResult that = (SettingResult) o;

        if (Status != null ? !Status.equals(that.Status) : that.Status != null) return false;
        // if (ErrorChain != null ? !ErrorChain.equals(that.ErrorChain) : that.ErrorChain != null) return false;
        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
        int result = Status != null ? Status.hashCode() : 0;
        if (ErrorChain == null) {
            return result;
        }
        for (CommandError error : ErrorChain) {
            result += error.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (ErrorChain != null) {
            sb.append("{");
            for (CommandError error : ErrorChain) {
                sb.append(error.toString()).append(",");
            }
            sb.append("}");
        }

        return "SettingResult{" +
                "Status='" + Status + '\'' +
                ", ErrorChain=" + sb.toString() +
                '}';
    }
}
