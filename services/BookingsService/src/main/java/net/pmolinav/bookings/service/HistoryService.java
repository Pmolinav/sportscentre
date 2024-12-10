package net.pmolinav.bookings.service;

import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookings.repository.HistoryRepository;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    public History createHistory(History history) {
        try {
            return historyRepository.save(history);
        } catch (Exception e) {
            log.error("Unexpected error while creating new history: {} in repository.", history.toString(), e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
