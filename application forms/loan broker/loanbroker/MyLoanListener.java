package loanbroker;

import model.bank.BankInterestRequest;
import model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.ArrayList;


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
            new BankInterestSender().sendBankInterestRequest(new BankInterestRequest(loanRequest.getAmount(),loanRequest.getTime())); //directly sends the bankrequest
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
