package model;

import java.io.Serializable;

public class Correlation implements Serializable {
    protected String correlationID;

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }
}
