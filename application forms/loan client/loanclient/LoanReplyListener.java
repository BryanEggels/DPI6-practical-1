package loanclient;

import JMSConnection.JmsSender;
import model.bank.BankInterestRequest;
import model.loan.LoanReply;
import model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class LoanReplyListener implements MessageListener {
    LoanClientFrame frame;

    public LoanReplyListener(LoanClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            LoanReply loanReply = (LoanReply) objectMessage.getObject();
            frame.add(loanReply);
            System.out.println(loanReply.toString());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
