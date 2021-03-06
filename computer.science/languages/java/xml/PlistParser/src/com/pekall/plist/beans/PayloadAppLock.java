package com.pekall.plist.beans;

@SuppressWarnings("UnusedDeclaration")
public class PayloadAppLock extends PayloadBase {

    /**
     * A dictionary containing information about the app.
     */
    private AppDict App;

    public PayloadAppLock() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_APP_LOCK);
    }

    public AppDict getApp() {
        return App;
    }

    public void setApp(AppDict app) {
        App = app;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadAppLock)) return false;
        if (!super.equals(o)) return false;

        PayloadAppLock that = (PayloadAppLock) o;

        return !(App != null ? !App.equals(that.App) : that.App != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (App != null ? App.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadAppLock{" +
                "App=" + App +
                '}';
    }
}
