package com.pekall.plist.beans;

/**
 * Install document command for Android platform
 */
public class CommandInstallDocument extends CommandObject {

    public static final String KEY_DOCUMENT_NAME = "DocumentName";
    public static final String KEY_DOCUMENT_SIZE = "DocumentSize";
    public static final String KEY_DOCUMENT_FORMAT = "DocumentFormat";
    public static final String KEY_DOWNLOAD_URL = "DownloadURL";
    public static final String KEY_NEED_ENCRYPTION = "NeedEncryption";
    public static final String KEY_DOCUMENT_UUID = "DocumentUUID";
    public static final String KEY_DOCUMENT_DESCRIPTION = "DocumentDescription";

    /**
     * Name of the document
     */
    private String DocumentName;

    /**
     * Size of the document in byte
     */
    private Long DocumentSize;

    /**
     * Format of the document
     */
    private String DocumentFormat;

    /**
     * Download Url of the document
     */
    private String DownloadURL;

    /**
     * Need encryption
     */
    private Boolean NeedEncryption;

    /**
     * UUID of the document
     */
    private String DocumentUUID;

    /**
     * Document description
     */
    private String DocumentDescription;

    public CommandInstallDocument() {
        super(CommandObject.REQ_TYPE_INST_DOC);
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public Long getDocumentSize() {
        return DocumentSize;
    }

    public void setDocumentSize(Long documentSize) {
        DocumentSize = documentSize;
    }

    public String getDownloadURL() {
        return DownloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        DownloadURL = downloadURL;
    }

    public Boolean getNeedEncryption() {
        return NeedEncryption;
    }

    public void setNeedEncryption(Boolean needEncryption) {
        NeedEncryption = needEncryption;
    }

    public String getDocumentUUID() {
        return DocumentUUID;
    }

    public void setDocumentUUID(String documentUUID) {
        DocumentUUID = documentUUID;
    }

    public String getDocumentDescription() {
        return DocumentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        DocumentDescription = documentDescription;
    }

    public String getDocumentFormat() {
        return DocumentFormat;
    }

    public void setDocumentFormat(String documentFormat) {
        DocumentFormat = documentFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandInstallDocument)) return false;
        if (!super.equals(o)) return false;

        CommandInstallDocument that = (CommandInstallDocument) o;

        if (DocumentDescription != null ? !DocumentDescription.equals(that.DocumentDescription) : that.DocumentDescription != null)
            return false;
        if (DocumentFormat != null ? !DocumentFormat.equals(that.DocumentFormat) : that.DocumentFormat != null)
            return false;
        if (DocumentName != null ? !DocumentName.equals(that.DocumentName) : that.DocumentName != null) return false;
        if (DocumentSize != null ? !DocumentSize.equals(that.DocumentSize) : that.DocumentSize != null) return false;
        if (DocumentUUID != null ? !DocumentUUID.equals(that.DocumentUUID) : that.DocumentUUID != null) return false;
        if (DownloadURL != null ? !DownloadURL.equals(that.DownloadURL) : that.DownloadURL != null) return false;
        if (NeedEncryption != null ? !NeedEncryption.equals(that.NeedEncryption) : that.NeedEncryption != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (DocumentName != null ? DocumentName.hashCode() : 0);
        result = 31 * result + (DocumentSize != null ? DocumentSize.hashCode() : 0);
        result = 31 * result + (DocumentFormat != null ? DocumentFormat.hashCode() : 0);
        result = 31 * result + (DownloadURL != null ? DownloadURL.hashCode() : 0);
        result = 31 * result + (NeedEncryption != null ? NeedEncryption.hashCode() : 0);
        result = 31 * result + (DocumentUUID != null ? DocumentUUID.hashCode() : 0);
        result = 31 * result + (DocumentDescription != null ? DocumentDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandInstallDocument{" +
                "super='" + super.toString() + '\'' +
                "DocumentName='" + DocumentName + '\'' +
                ", DocumentSize=" + DocumentSize +
                ", DocumentFormat='" + DocumentFormat + '\'' +
                ", DownloadURL='" + DownloadURL + '\'' +
                ", NeedEncryption=" + NeedEncryption +
                ", DocumentUUID='" + DocumentUUID + '\'' +
                ", DocumentDescription='" + DocumentDescription + '\'' +
                '}';
    }
}
