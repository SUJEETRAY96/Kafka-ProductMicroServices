package com.example.ms.ProductMicroservice.services;

import com.example.ms.ProductMicroservice.models.CreateRestProductModel;
import org.example.core.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public String createProduct(CreateRestProductModel product) throws Exception {
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent event = new ProductCreatedEvent(productId,
                product.getTitle(),
                product.getPrice(),
                product.getQuantity());

        LOGGER.info("******* Before publishing a Event *******");


        SendResult<String , ProductCreatedEvent> result = kafkaTemplate.send("product-created-event-topic", productId , event).get();

        // for synchronous call
        //SendResult<String , ProductCreatedEvent> result = kafkaTemplate.send("insync-topic", productId , event).get();


//        CompletableFuture<SendResult<String , ProductCreatedEvent>> future = kafkaTemplate.send("product-created-event-topic", productId , event);
//
//        future.whenComplete((result, ex) ->{
//            if (ex != null){
//                LOGGER.error("Failed to send message to kafka topic"+ ex.getMessage());
//            }else{
//                LOGGER.info("Message sent successfully"+ result.getRecordMetadata());
//            }
//        });
//        future.join();
        LOGGER.info("******* partitions : "+result.getRecordMetadata().partition()+" *******");
        LOGGER.info("******* Topic : "+result.getRecordMetadata().topic()+" *******");
        LOGGER.info("******* Offset : "+result.getRecordMetadata().offset()+" *******");
        LOGGER.info("******* Returning Product ID *******");
        return productId;
    }
}
