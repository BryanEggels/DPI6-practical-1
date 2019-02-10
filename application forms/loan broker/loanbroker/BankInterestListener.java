package loanbroker;

import JMSConnection.JmsSender;
import messaging.requestreply.RequestReply;
import model.bank.BankInterestReply;
import model.bank.BankInterestRequest;
import model.loan.LoanReply;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BankInterestListener implements MessageListener {

    LoanBrokerFrame frame;

    public BankInterestListener(LoanBrokerFrame frame) {
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {

        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            RequestReply rr = (RequestReply) objectMessage.getObject();
            BankInterestRequest request = (BankInterestRequest) rr.getRequest();
            BankInterestReply reply = (BankInterestReply) rr.getReply();
            System.out.println(((BankInterestReply) rr.getReply()).getCorrelationID());
            frame.add(request,reply);
            System.out.println(reply.toString());
            JmsSender sender = new JmsSender("loanReply");
            LoanReply loanReply = new LoanReply(reply.getInterest(),reply.getQuoteId());
            loanReply.setCorrelationID(reply.getCorrelationID());
            sender.connect();
            sender.sendLoanReply(loanReply);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
