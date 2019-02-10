package bank;

import javax.jms.Message;
import javax.jms.MessageListener;

import messaging.requestreply.RequestReply;
import model.bank.BankInterestRequest;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class bankInterestListener implements MessageListener {
    JMSBankFrame frame;

    public bankInterestListener(JMSBankFrame frame){
        this.frame = frame;
    }


    @Override
    public void onMessage(Message message) {

        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            BankInterestRequest request = (BankInterestRequest) objectMessage.getObject();
            System.out.println(request.getCorrelationID());
            System.out.println(request.toString());
            frame.add(request);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
