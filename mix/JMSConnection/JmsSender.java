package JMSConnection;

import model.bank.BankInterestRequest;
import model.loan.LoanRequest;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import model.loan.LoanRequest;
import org.apache.activemq.ActiveMQConnectionFactory;



public class JmsSender {
    Connection connection;
    Session session;
    Destination sendDestination;
    MessageProducer producer;
    String topicName;
    public boolean succesfull;


    public JmsSender(String topicName) {
        this.topicName = topicName;

        try {

            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");

            // connect to the Destination called “myFirstChannel”
            // queue or topic: “queue.myFirstDestination” or “topic.myFirstDestination”
            props.put(("queue." + topicName), topicName);

            Context jndiContext = new InitialContext(props);
            ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory) jndiContext
                    .lookup("ConnectionFactory");
            connectionFactory.setTrustAllPackages(true);
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // connect to the sender destination
            sendDestination = (Destination) jndiContext.lookup("loan");
            producer = session.createProducer(sendDestination);
            this.succesfull =true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendLoan(LoanRequest request){
        try {
            Message msg =  session.createObjectMessage(request);
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void sendBankInterestRequest(BankInterestRequest request){
        try {
            Message msg =  session.createObjectMessage(request);
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
