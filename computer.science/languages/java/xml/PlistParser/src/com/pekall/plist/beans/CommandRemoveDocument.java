package com.pekall.plist.beans;

public class CommandRemoveDocument extends CommandObject {
    /**
     * UUID of the document
     */
    private String DocumentUUID;

    public CommandRemoveDocument() {
        super(CommandObject.REQ_TYPE_RM_DOC);
    }

    public String getDocumentUUID() {
        return DocumentUUID;
    }

    public void setDocumentUUID(String documentUUID) {
        DocumentUUID = documentUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandRemoveDocument)) return false;
        if (!super.equals(o)) return false;

        CommandRemoveDocument that = (CommandRemoveDocument) o;

        if (DocumentUUID != null ? !DocumentUUID.equals(that.DocumentUUID) : that.DocumentUUID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (DocumentUUID != null ? DocumentUUID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandRemoveDocument{" +
                "super='" + super.toString() + '\'' +
                "DocumentUUID='" + DocumentUUID + '\'' +
                '}';
    }
}
