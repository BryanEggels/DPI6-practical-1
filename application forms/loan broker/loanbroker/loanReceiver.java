package loanbroker;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class loanReceiver {
    Connection connection;
    Session session;

    Destination receiveDestination;
    MessageConsumer consumer;
    LoanBrokerFrame frame;


    public loanReceiver(LoanBrokerFrame frame){
        this.frame = frame;
    }

    public void receiveLoan(){
        try{
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");

            props.put(("queue.loan"), "loan");

            Context jndiContext = new InitialContext(props);

            ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory) jndiContext
                    .lookup("ConnectionFactory");
            connectionFactory.setTrustAllPackages(true);
            connection = connectionFactory.createConnection();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //connect to the receiver destination
            receiveDestination = (Destination)jndiContext.lookup("loan");

            consumer = session.createConsumer(receiveDestination);

            connection.start(); // this is needed to start receiving messages



        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            MessageListener listener = new MyLoanListener(frame);
            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }


    }




}
