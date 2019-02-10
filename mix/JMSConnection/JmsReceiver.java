package JMSConnection;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class JmsReceiver {

    String topic;
    Connection connection;
    Session session;

    Destination receiveDestination;
    MessageConsumer consumer;
    public JmsReceiver(String topic) {
        this.topic = topic;
    }
    public boolean startConnection(){
        try{
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");

            props.put(("queue."+topic), topic);

            Context jndiContext = new InitialContext(props);

            ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory) jndiContext
                    .lookup("ConnectionFactory");
            connectionFactory.setTrustAllPackages(true);
            connection = connectionFactory.createConnection();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //connect to the receiver destination
            receiveDestination = (Destination)jndiContext.lookup(topic);

            consumer = session.createConsumer(receiveDestination);

            connection.start(); // this is needed to start receiving messages
            return true;


        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void registerListener(MessageListener listener){
        try {
            consumer.setMessageListener(listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
