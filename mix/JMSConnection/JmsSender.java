package JMSConnection;

import loanclient.LoanClientFrame;
import messaging.requestreply.RequestReply;
import model.bank.BankInterestReply;
import model.bank.BankInterestRequest;
import model.loan.LoanReply;
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
    String correlationID;

    public JmsSender(String topicName) {
        this.topicName = topicName;
    }
    public JmsSender(String topicName, String correlationID){
        this.topicName = topicName;
        this.correlationID = correlationID;
    }

    public boolean connect(){
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
            sendDestination = (Destination) jndiContext.lookup(topicName);
            producer = session.createProducer(sendDestination);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendLoanRequest(LoanRequest request, LoanClientFrame frame){
        try {

            Message msg =  session.createObjectMessage(request);


            producer.send(msg);
            request.setCorrelationID(msg.getJMSMessageID());

            frame.add(request);
            System.out.println(msg.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void sendLoanReply(LoanReply reply){
        try {
            Message msg =  session.createObjectMessage(reply);
            msg.setJMSCorrelationID(reply.getCorrelationID());
            producer.send(msg);
            System.out.println(msg.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void sendBankInterestRequest(BankInterestRequest request){
        try {
            Message msg =  session.createObjectMessage(request);
            msg.setJMSCorrelationID(request.getCorrelationID());

            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void sendBankInterestReply(RequestReply reply){
        try {
            Message msg =  session.createObjectMessage(reply);

            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
