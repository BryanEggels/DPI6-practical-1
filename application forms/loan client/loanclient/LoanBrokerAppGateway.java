package loanbroker;

import JMSConnection.JmsReceiver;
import JMSConnection.JmsSender;

public class LoanBrokerAppGateway {
    JmsSender sender;
    JmsReceiver receiver;


    public LoanBrokerAppGateway(JmsSender sender, JmsReceiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
