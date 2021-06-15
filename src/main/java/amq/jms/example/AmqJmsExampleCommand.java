package amq.jms.example;

import amq.jms.example.producer.StringMessageProducer;
import io.micronaut.configuration.picocli.PicocliRunner;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.TypeHint;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.UUID;

@Command(name = "amq-jms-example", description = "Sends a JMS message to an ActiveMQ broker",
        mixinStandardHelpOptions = true)
@TypeHint(value = { org.apache.activemq.openwire.v10.MarshallerFactory.class },
        accessType = TypeHint.AccessType.ALL_DECLARED_METHODS)
public class AmqJmsExampleCommand implements Runnable {

    private final StringMessageProducer stringMessageProducer;

    @Option(names = {"-v", "--verbose"}, description = "Outputs more details")
    boolean verbose;

    @Property(name = "config.jms.destinationQueue")
    protected String queueName;

    AmqJmsExampleCommand(StringMessageProducer stringMessageProducer) {
        this.stringMessageProducer = stringMessageProducer;
    }

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(AmqJmsExampleCommand.class, args);
        //int exitCode = PicocliRunner.execute(AmqJmsExampleCommand.class, args);
        //System.exit(exitCode);
    }

    public void run() {
        String message = UUID.randomUUID().toString();
        if (verbose) {
            System.out.printf("About to send this message to the queue: %s%n", message);
            System.out.printf("Queue name: %s%n", queueName);
        }
        stringMessageProducer.send(message);
        System.out.printf("Sent message: %s%n", message);
        System.exit(0);
    }
}
