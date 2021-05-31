package amq.jms.example;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.inject.Singleton;
import javax.jms.ConnectionFactory;

@Singleton
public class CustomizeBrokerJMSConnectionPoolListener implements BeanCreatedEventListener<ConnectionFactory> {

    @Override
    public ConnectionFactory onCreated(BeanCreatedEvent<ConnectionFactory> event) {

        ConnectionFactory connectionFactory = event.getBean();
        if (connectionFactory instanceof ActiveMQConnectionFactory) {
            ActiveMQConnectionFactory amqcf = (ActiveMQConnectionFactory) connectionFactory;
            // Can customize the settings of the connection factory here
        }

        return connectionFactory;
    }
}
