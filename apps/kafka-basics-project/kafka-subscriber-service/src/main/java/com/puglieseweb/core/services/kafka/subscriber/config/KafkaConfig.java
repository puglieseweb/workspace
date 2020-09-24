package com.puglieseweb.core.services.kafka.subscriber.config;

import com.puglieseweb.core.services.kafka.subscriber.handler.GlobalListenerErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory(){
        var properties = kafkaProperties.buildConsumerProperties();
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");
        return new DefaultKafkaConsumerFactory<Object, Object>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
        var factory = new ConcurrentKafkaListenerContainerFactory();
        configurer.configure(factory, consumerFactory());
        factory.setRecordFilterStrategy(new RecordFilterStrategy() {
            @Override
            public boolean filter(ConsumerRecord consumerRecord) {
                consumerRecord.value();

                return false;
            }
        });
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
        var factory = new ConcurrentKafkaListenerContainerFactory();
        configurer.configure(factory, consumerFactory());
        factory.setErrorHandler(new GlobalListenerErrorHandler());
        return factory;
    }

    private RetryTemplate retryTemplate(){
        var retryTemplate = new RetryTemplate();
        var retryPolicy = new SimpleRetryPolicy(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        var backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(10_000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> retryKafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
        var factory = new ConcurrentKafkaListenerContainerFactory();
        configurer.configure(factory, consumerFactory());
        factory.setErrorHandler(new GlobalListenerErrorHandler());
        factory.setRetryTemplate(retryTemplate());
        return factory;
    }

    @Bean(value = "invoiceDltContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> dltContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer, KafkaOperations<Object, Object> kafkaTemplate) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());

        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new TopicPartition("t_invoice_dlt", record.partition()));

        // 5 retry, 10 second interval for each retry
        var errorHandler = new SeekToCurrentErrorHandler(recoverer, new FixedBackOff(10_000, 5));

//        factory.getContainerProperties().setAckOnError(false);
        factory.setErrorHandler(errorHandler);

        return factory;
    }
}
