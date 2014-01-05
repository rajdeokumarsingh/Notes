package com.pekall.plist.beans;

/**
 * The RemoveApplication command is used to remove managed apps and their data from a device.
 * Applications not installed by the server cannot be removed with this command.
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandRemoveApp extends CommandObject {
    /**
     * The application’s identifier.
     */
    private String Identifier;

    public CommandRemoveApp() {
        super(CommandObject.REQ_TYPE_RM_APP);
    }

    public CommandRemoveApp(String identifier) {
        super(CommandObject.REQ_TYPE_RM_APP);
        this.Identifier = identifier;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandRemoveApp)) return false;
        if (!super.equals(o)) return false;

        CommandRemoveApp that = (CommandRemoveApp) o;

        return !(Identifier != null ? !Identifier.equals(that.Identifier) : that.Identifier != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (Identifier != null ? Identifier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandRemoveApp{" +
                "Identifier='" + Identifier + '\'' +
                '}';
    }
}
