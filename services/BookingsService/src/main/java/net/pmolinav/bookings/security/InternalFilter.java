package net.pmolinav.bookings.security;

import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookingslib.dto.MDCKeys;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class InternalFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        MDC.clear();
        String correlationUid = UUID.randomUUID().toString();

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request, correlationUid);
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response, correlationUid);

        MDC.put(MDCKeys.originIP.name(), requestWrapper.getRemoteAddr());
        MDC.put(MDCKeys.correlationUid.name(), correlationUid);

        log.info("Incoming call received. Method: {}. Path: {}. Query params: {}. " +
                        "Request Body: {}. Headers: {}. Client IP: {}. Correlation-Uid: {} ",
                requestWrapper.getMethod(), requestWrapper.getRequestURI(),
                requestWrapper.getQueryParams(), requestWrapper.getRequestBody(),
                requestWrapper.getHeaders(), requestWrapper.getRemoteAddr(), correlationUid
        );

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long duration = System.currentTimeMillis() - startTime;

        byte[] responseData = responseWrapper.getResponseData();
        String responseBody = new String(responseData, response.getCharacterEncoding());
        if (responseWrapper.getStatus() >= 400 && responseWrapper.getStatus() < 500) {
            log.warn("Incoming call failed with status {}. Elapsed time: {} ms. Response Body: {}. Correlation-Uid: {}",
                    responseWrapper.getStatus(), duration, responseBody, correlationUid);
        } else if (responseWrapper.getStatus() >= 500) {
            log.error("Incoming call failed with status {}. Elapsed time: {} ms. Response Body: {}. Correlation-Uid: {}",
                    responseWrapper.getStatus(), duration, responseBody, correlationUid);
        } else {
            log.info("Incoming call succeeded with status {}. Elapsed time: {} ms. Response Body: {}. Correlation-Uid: {}",
                    responseWrapper.getStatus(), duration, responseBody, correlationUid);
        }

        MDC.put(MDCKeys.elapsedTime.name(), String.valueOf(duration));

        // Write the response data back to the actual response
        responseWrapper.copyBodyToResponse();
    }
}


