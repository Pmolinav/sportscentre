package net.pmolinav.configuration.controller;

import net.pmolinav.bookingslib.dto.MDCKeys;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void setupMdcAndValidateRequestUid(@RequestParam String requestUid) {
        MDC.clear();
        MDC.put(MDCKeys.requestUid.name(), requestUid);
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<String> handleUnexpectedException(UnexpectedException e) {
        logError(e);
        return ResponseEntity.status(e.getStatusCode()).build();
    }

    private void logError(Exception e) {
        String controllerName = getControllerName();
        Logger logger = LoggerFactory.getLogger(controllerName);

        logger.error("Unexpected error while executing controller" + controllerName + ": " + e.getMessage(), e);
    }

    private String getControllerName() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String handler = (String) requestAttributes.getRequest().getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");

        // We assume that the controller name is the last segment in the controller's path.
        String[] segments = handler.split("/");
        return segments[segments.length - 1];
    }
}
