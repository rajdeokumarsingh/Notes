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

    public static final int DOC_AUTH_ENCRYPTION = 51801;

    public static final int DOC_AUTH_SANDBOX = 51802;

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
    private String Auth;

    /**
     * UUID of the document
     */
    private String DocumentUUID;

    /**
     * Just use for PEKALL MDM
     */
    private String Version;

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

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
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

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandInstallDocument)) return false;
        if (!super.equals(o)) return false;

        CommandInstallDocument that = (CommandInstallDocument) o;

        if (Auth != null ? !Auth.equals(that.Auth) : that.Auth != null) return false;
        if (DocumentDescription != null ? !DocumentDescription.equals(that.DocumentDescription) : that.DocumentDescription != null)
            return false;
        if (DocumentFormat != null ? !DocumentFormat.equals(that.DocumentFormat) : that.DocumentFormat != null)
            return false;
        if (DocumentName != null ? !DocumentName.equals(that.DocumentName) : that.DocumentName != null) return false;
        if (DocumentSize != null ? !DocumentSize.equals(that.DocumentSize) : that.DocumentSize != null) return false;
        if (DocumentUUID != null ? !DocumentUUID.equals(that.DocumentUUID) : that.DocumentUUID != null) return false;
        if (DownloadURL != null ? !DownloadURL.equals(that.DownloadURL) : that.DownloadURL != null) return false;
        if (Version != null ? !Version.equals(that.Version) : that.Version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (DocumentName != null ? DocumentName.hashCode() : 0);
        result = 31 * result + (DocumentSize != null ? DocumentSize.hashCode() : 0);
        result = 31 * result + (DocumentFormat != null ? DocumentFormat.hashCode() : 0);
        result = 31 * result + (DownloadURL != null ? DownloadURL.hashCode() : 0);
        result = 31 * result + (Auth != null ? Auth.hashCode() : 0);
        result = 31 * result + (DocumentUUID != null ? DocumentUUID.hashCode() : 0);
        result = 31 * result + (Version != null ? Version.hashCode() : 0);
        result = 31 * result + (DocumentDescription != null ? DocumentDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandInstallDocument{" +
                "Auth='" + Auth + '\'' +
                ", DocumentName='" + DocumentName + '\'' +
                ", DocumentSize=" + DocumentSize +
                ", DocumentFormat='" + DocumentFormat + '\'' +
                ", DownloadURL='" + DownloadURL + '\'' +
                ", DocumentUUID='" + DocumentUUID + '\'' +
                ", Version='" + Version + '\'' +
                ", DocumentDescription='" + DocumentDescription + '\'' +
                "} " + super.toString();
    }
}
