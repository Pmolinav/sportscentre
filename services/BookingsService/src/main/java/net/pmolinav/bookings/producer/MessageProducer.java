package net.pmolinav.bookings.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookingslib.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, History history) {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(history));
        } catch (JsonProcessingException e) {
            log.error("Unexpected error occurred while trying to parse the following historical message: {}", history);
            throw new RuntimeException(e);
        }
    }

}