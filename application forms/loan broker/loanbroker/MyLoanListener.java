package loanbroker;

import JMSConnection.JmsSender;
import model.bank.BankInterestRequest;
import model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


public class MyLoanListener implements MessageListener {
    LoanBrokerFrame frame;

    public MyLoanListener(LoanBrokerFrame frame){
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {

        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            LoanRequest loanRequest = (LoanRequest) objectMessage.getObject();
            frame.add(loanRequest);
            System.out.println(loanRequest.toString());
            JmsSender sender = new JmsSender("bankInterest",message.getJMSMessageID());
            sender.connect();
            System.out.println(message.getJMSMessageID());
            BankInterestRequest bankInterestRequest = new BankInterestRequest(loanRequest.getAmount(),loanRequest.getTime(),message.getJMSMessageID());

            sender.sendBankInterestRequest(bankInterestRequest);//directly sends the bankrequest
            frame.add(loanRequest,bankInterestRequest);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
