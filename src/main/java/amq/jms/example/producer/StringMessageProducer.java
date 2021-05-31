package amq.jms.example.producer;

import io.micronaut.jms.annotations.JMSProducer;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

@JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
public interface StringMessageProducer {

    @Queue("${config.jms.destinationQueue}")
    void send(@MessageBody String body);
}
