package net.pmolinav;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class HeaderSettingRequestCallback implements RequestCallback {
    private String body;
    private Map<String, String> queryParams;
    final Map<String, String> requestHeaders;

    public HeaderSettingRequestCallback(Map<String, String> queryParams, String body, Map<String, String> requestHeaders) {
        this.queryParams = queryParams;
        this.body = body;
        this.requestHeaders = requestHeaders;
    }

    public HeaderSettingRequestCallback(String body, Map<String, String> requestHeaders) {
        this.body = body;
        this.requestHeaders = requestHeaders;
    }

    public HeaderSettingRequestCallback(final Map<String, String> headers) {
        this.requestHeaders = headers;
    }

    public void setBody(final String postBody) {
        this.body = postBody;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        final HttpHeaders clientHeaders = request.getHeaders();
        for (final Map.Entry<String, String> entry : requestHeaders.entrySet()) {
            clientHeaders.add(entry.getKey(), entry.getValue());
        }
        if (null != body) {
            request.getBody().write(body.getBytes());
        }
        try {
            URI uriWithParams = appendQueryParams(request.getURI(), queryParams);
            request.getHeaders().setLocation(uriWithParams);
        } catch (URISyntaxException e) {
            throw new IOException("Error adding query parameters to URI", e);
        }
    }

    private URI appendQueryParams(URI uri, Map<String, String> queryParams) throws URISyntaxException {
        String existingQuery = uri.getQuery();
        StringJoiner newQuery = new StringJoiner("&");

        if (existingQuery != null && !existingQuery.isEmpty()) {
            newQuery.add(existingQuery);
        }

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
            newQuery.add(encodedKey + "=" + encodedValue);
        }

        return new URI(
                uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                newQuery.toString(),
                uri.getFragment()
        );
    }
}