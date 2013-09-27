package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

public class PayloadAPN extends PayloadBase {

    private List<APNDataArray> PayloadContent;

    public List<APNDataArray> getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(List<APNDataArray> payloadContent) {
        PayloadContent = payloadContent;
    }


    public void addPayloadContent(APNDataArray payloadContent) {
      if(PayloadContent == null)
          PayloadContent = new ArrayList<APNDataArray>();
        PayloadContent.add(payloadContent);
    }

    public PayloadAPN() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_APN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadAPN)) return false;
        if (!super.equals(o)) return false;

        PayloadAPN that = (PayloadAPN) o;

        if (PayloadContent != null ? !PayloadContent.equals(that.PayloadContent) : that.PayloadContent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (PayloadContent != null ? PayloadContent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadAPN{" +
                "PayloadContent=" + PayloadContent +
                '}';
    }
}
