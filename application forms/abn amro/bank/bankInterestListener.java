package bank;

import loanbroker.BankInterestSender;
import loanbroker.LoanBrokerFrame;
import model.bank.BankInterestRequest;
import model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
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
            System.out.println(request.toString());
            //new BankInterestSender().sendBankInterestRequest(new BankInterestRequest(loanRequest.getAmount(),loanRequest.getTime())); //directly sends the bankrequest
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
