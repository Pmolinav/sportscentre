
package net.pmolinav.configuration.service;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookingslib.exception.CustomStatusException;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.configuration.client.HealthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HealthBOService {

    @Autowired
    private HealthClient healthClient;

    public void health() {
        try {
            healthClient.health();
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("No activities found.", e);
                throw new NotFoundException("No activities found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
