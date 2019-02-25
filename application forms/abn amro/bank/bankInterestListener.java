package bank;

import javax.jms.Message;
import javax.jms.MessageListener;

import model.bank.BankInterestRequest;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class INGbankInterestListener implements MessageListener {
    INGFrame frame;

    public INGbankInterestListener(INGFrame frame){
        this.frame = frame;
    }
    public INGbankInterestListener(){};


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
