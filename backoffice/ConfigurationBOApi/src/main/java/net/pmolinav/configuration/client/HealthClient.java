package net.pmolinav.configuration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "HealthService", url = "localhost:8001/actuator")
public interface HealthClient {

    @GetMapping("/health")
    void health();
}
