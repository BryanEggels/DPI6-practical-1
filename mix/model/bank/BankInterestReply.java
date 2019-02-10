package model.bank;

import model.Correlation;

import java.io.Serializable;

/**
 * This class stores information about the bank reply
 *  to a loan request of the specific client
 * 
 */
public class BankInterestReply extends Correlation implements Serializable {

    private double interest; // the loan interest
    private String bankId; // the nunique quote Id
    
    public BankInterestReply(String id) {
        this.interest = 0;
        this.bankId = "";
        correlationID = id;
    }
    
    public BankInterestReply(double interest, String quoteId, String correlationID) {
        this.interest = interest;
        this.bankId = quoteId;
        this.correlationID = correlationID;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getQuoteId() {
        return bankId;
    }

    public void setQuoteId(String quoteId) {
        this.bankId = quoteId;
    }

    public String toString() {
        return "quote=" + this.bankId + " interest=" + this.interest;
    }

    public String getCorrelationID(){
        return correlationID;
    }
}
