//package com.example.webrest.services.kafka;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.binder.BinderHeaders;
//import org.springframework.cloud.stream.messaging.Processor;
//import org.springframework.integration.support.MessageBuilder;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.handler.annotation.SendTo;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@EnableBinding(ReRouteDlqKApplication.TwoOutputProcessor.class)
//public class ReRouteDlqKApplication {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(ReRouteDlqKApplication.class);
//
//    private static final String X_RETRIES_HEADER = "x-retries";
//
//    private final AtomicInteger processed = new AtomicInteger();
//
//    private final MessageChannel parkingLot;
//
//    @Autowired
//    public ReRouteDlqKApplication(MessageChannel parkingLot) {
//        this.parkingLot = parkingLot;
//    }
//
//    @StreamListener(Processor.INPUT)
//    @SendTo(Processor.OUTPUT)
//    public Message<?> reRoute(Message<?> failed) {
//        processed.incrementAndGet();
//        Integer retries = failed.getHeaders().get(X_RETRIES_HEADER, Integer.class);
//        if (retries == null) {
//            LOGGER.debug("First retry for " + failed);
//            return MessageBuilder.fromMessage(failed)
//                    .setHeader(X_RETRIES_HEADER, 1)
//                    .setHeader(BinderHeaders.PARTITION_OVERRIDE,
//                            failed.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID))
//                    .build();
//        }
//        else if (retries < 3) {
//            LOGGER.debug("Another retry for " + failed);
//            return MessageBuilder.fromMessage(failed)
//                    .setHeader(X_RETRIES_HEADER, retries + 1)
//                    .setHeader(BinderHeaders.PARTITION_OVERRIDE,
//                            failed.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID))
//                    .build();
//        }
//        else {
//            System.out.println("Retries exhausted for " + failed);
//            parkingLot.send(MessageBuilder.fromMessage(failed)
//                    .setHeader(BinderHeaders.PARTITION_OVERRIDE,
//                            failed.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID))
//                    .build());
//        }
//        return null;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        while (true) {
//            int count = this.processed.get();
//            Thread.sleep(5000);
//            if (count == this.processed.get()) {
//                System.out.println("Idle, terminating");
//                return;
//            }
//        }
//    }
//
//    public interface TwoOutputProcessor extends Processor {
//
//        @Output("parkingLot")
//        MessageChannel parkingLot();
//
//    }
//
//}