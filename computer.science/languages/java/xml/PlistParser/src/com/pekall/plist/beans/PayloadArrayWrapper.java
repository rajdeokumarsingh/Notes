package com.pekall.plist.beans;

import com.pekall.plist.PlistDebug;

import java.util.ArrayList;
import java.util.List;

/**
 * IOS payload in which "PayloadContent" contains an payload array.
 */
public class PayloadArrayWrapper extends PayloadBase {

    List<PayloadBase> PayloadContent = new ArrayList<PayloadBase>();

    public PayloadArrayWrapper() {
        super();
    }

    public List<PayloadBase> getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(List<PayloadBase> payloadContent) {
        PayloadContent = payloadContent;
    }

    public void addPayLoadContent(PayloadBase content) {
        PayloadContent.add(content);
    }

    @Override
    public boolean equals(Object o) {
        PlistDebug.logTest("equals 1");
        if (this == o) return true;
        if (!(o instanceof PayloadArrayWrapper)) return false;
        if (!super.equals(o)) return false;

        PayloadArrayWrapper that = (PayloadArrayWrapper) o;

        PlistDebug.logTest("equals 2, this hash code: " + this.hashCode());
        PlistDebug.logTest("equals 2, that hash code: " + that.hashCode());
        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        PlistDebug.logTest("super hash: " + result);
        for (PayloadBase payloadBase : PayloadContent) {
            result += payloadBase.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (PayloadBase payloadBase : PayloadContent) {
            sb.append(payloadBase.toString() + "\n");
        }
        sb.append("}");
        return "PayloadArrayWrapper{" +
                "super=" + super.toString() +
                "PayloadContent=" + sb.toString() +
                '}';
    }
}
