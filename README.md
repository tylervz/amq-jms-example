# Micronaut ActiveMQ JMS Example

A Micronaut CLI application which demonstrated a [bug](https://github.com/micronaut-projects/micronaut-jms/issues/109).

Uses:
* Micronaut 2.5.4
* Java 11
* GraalVM (I'm using 21.1.0.r11)

Created the application with

    mn create-cli-app amq-jms-example --jdk 11

Then I added code for sending a JMS message using the library `io.micronaut.jms:micronaut-jms-activemq-classic:1.0.0.M3`

## Running an MQ Broker

To start an ActiveMQ instance, you can run:

    docker-compose -f activemq-docker-compose.yml up

If you do that, you'll run the following when you want to stop the docker container:

    docker-compose -f activemq-docker-compose.yml down

To start an [AMQ](https://developers.redhat.com/products/amq/overview) instance, you can run:

    docker-compose up

If you do that, you'll run the following when you want to stop the docker container:

    docker-compose down

## Building and Running the Application

You can build the application to run using the JVM with the following command
```bash
./gradlew build
```

Then run it with:

    java -jar build/libs/amq-jms-example-0.1-all.jar -v

This will send a message to the broker that you have running (either ActiveMQ or AMQ).

To generate a native image using Gradle run:

    $ ./gradlew nativeImage

The native image will be created in `build/native-image/application` and can be run with `./build/native-image/application -v` .
This will send a message to the ActiveMQ broker without an issue. 
Previously, you would get an error when running the native image while you have the AMQ broker running:

```
io.micronaut.messaging.exceptions.MessagingClientException: Problem sending message to example-messages
        at io.micronaut.jms.templates.JmsProducer.send(JmsProducer.java:93)
        at io.micronaut.jms.configuration.JMSProducerMethodInterceptor.intercept(JMSProducerMethodInterceptor.java:117)
        at io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:96)
        at amq.jms.example.producer.StringMessageProducer$Intercepted.send(Unknown Source)
        at amq.jms.example.AmqJmsExampleCommand.run(AmqJmsExampleCommand.java:40)
        at picocli.CommandLine.executeUserObject(CommandLine.java:1939)
        at picocli.CommandLine.access$1300(CommandLine.java:145)
        at picocli.CommandLine$RunLast.executeUserObjectOfLastSubcommandWithSameParent(CommandLine.java:2352)
        at picocli.CommandLine$RunLast.handle(CommandLine.java:2346)
        at picocli.CommandLine$RunLast.handle(CommandLine.java:2311)
        at picocli.CommandLine$AbstractParseResultHandler.execute(CommandLine.java:2179)
        at picocli.CommandLine.execute(CommandLine.java:2078)
        at io.micronaut.configuration.picocli.PicocliRunner.run(PicocliRunner.java:137)
        at io.micronaut.configuration.picocli.PicocliRunner.run(PicocliRunner.java:114)
        at amq.jms.example.AmqJmsExampleCommand.main(AmqJmsExampleCommand.java:29)
Caused by: io.micronaut.messaging.exceptions.MessagingSystemException: Problem creating pooled Connection
        at io.micronaut.jms.pool.JMSConnectionPool.doCreate(JMSConnectionPool.java:53)
        at io.micronaut.jms.pool.JMSConnectionPool.create(JMSConnectionPool.java:59)
        at io.micronaut.jms.pool.JMSConnectionPool.create(JMSConnectionPool.java:33)
        at io.micronaut.jms.pool.AbstractPool.request(AbstractPool.java:58)
        at io.micronaut.jms.pool.JMSConnectionPool.createConnection(JMSConnectionPool.java:73)
        at io.micronaut.jms.templates.JmsProducer.send(JmsProducer.java:88)
        ... 14 more
Caused by: javax.jms.JMSException: Disposed due to prior exception
        at org.apache.activemq.util.JMSExceptionSupport.create(JMSExceptionSupport.java:54)
        at org.apache.activemq.ActiveMQConnection.syncSendPacket(ActiveMQConnection.java:1403)
        at org.apache.activemq.ActiveMQConnection.ensureConnectionInfoSent(ActiveMQConnection.java:1486)
        at org.apache.activemq.ActiveMQConnection.start(ActiveMQConnection.java:527)
        at io.micronaut.jms.pool.JMSConnectionPool.doCreate(JMSConnectionPool.java:50)
        ... 19 more
Caused by: org.apache.activemq.transport.TransportDisposedIOException: Disposed due to prior exception
        at org.apache.activemq.transport.ResponseCorrelator.onException(ResponseCorrelator.java:125)
        at org.apache.activemq.transport.TransportFilter.onException(TransportFilter.java:114)
        at org.apache.activemq.transport.TransportFilter.onException(TransportFilter.java:114)
        at org.apache.activemq.transport.WireFormatNegotiator.onException(WireFormatNegotiator.java:173)
        at org.apache.activemq.transport.WireFormatNegotiator.negociate(WireFormatNegotiator.java:161)
        at org.apache.activemq.transport.WireFormatNegotiator.onCommand(WireFormatNegotiator.java:123)
        at org.apache.activemq.transport.AbstractInactivityMonitor.onCommand(AbstractInactivityMonitor.java:301)
        at org.apache.activemq.transport.TransportSupport.doConsume(TransportSupport.java:83)
        at org.apache.activemq.transport.tcp.TcpTransport.doRun(TcpTransport.java:233)
        at org.apache.activemq.transport.tcp.TcpTransport.run(TcpTransport.java:215)
        at java.lang.Thread.run(Thread.java:829)
        at com.oracle.svm.core.thread.JavaThreads.threadStartRoutine(JavaThreads.java:553)
        at com.oracle.svm.core.posix.thread.PosixJavaThreads.pthreadStartRoutine(PosixJavaThreads.java:192)
Caused by: java.io.IOException: Invalid version: 10, could not load org.apache.activemq.openwire.v10.MarshallerFactory
        at org.apache.activemq.util.IOExceptionSupport.create(IOExceptionSupport.java:46)
        ... 9 more
Caused by: java.lang.IllegalArgumentException: Invalid version: 10, could not load org.apache.activemq.openwire.v10.MarshallerFactory
        at org.apache.activemq.openwire.OpenWireFormat.setVersion(OpenWireFormat.java:335)
        at org.apache.activemq.openwire.OpenWireFormat.renegotiateWireFormat(OpenWireFormat.java:614)
        at org.apache.activemq.transport.WireFormatNegotiator.negociate(WireFormatNegotiator.java:145)
        ... 8 more
Caused by: java.lang.ClassNotFoundException: org.apache.activemq.openwire.v10.MarshallerFactory
        at com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:64)
        at java.lang.Class.forName(DynamicHub.java:1308)
        at org.apache.activemq.openwire.OpenWireFormat.setVersion(OpenWireFormat.java:333)
        ... 10 more
```

And I experienced the same problem when using a Docker image built using a GraalVM Native Image:

    ./gradlew dockerBuildNative

But the issue has been resolved by adding a TypeHint above the main class.
