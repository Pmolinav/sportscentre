package net.pmolinav.bookings.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookings.service.HistoryService;
import net.pmolinav.bookingslib.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {

    @Autowired
    private final HistoryService historyService;

    public MessageConsumer(HistoryService historyService) {
        this.historyService = historyService;
    }

    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void listen(String message) {
        try {
            historyService.createHistory(new ObjectMapper().readValue(message, History.class));
        } catch (Exception e) {
            log.warn("Kafka operation with message {} failed in consumer and need to be reviewed", message);
        }
    }

}