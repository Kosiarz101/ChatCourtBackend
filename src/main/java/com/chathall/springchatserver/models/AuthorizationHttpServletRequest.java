package com.chathall.springchatserver.models;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class AuthorizationHttpServletRequest extends HttpServletRequestWrapper {

    private final String AUTHORIZATION_HEADER;
    private Map<String, String> authorizationHeader = new HashMap<>();
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public AuthorizationHttpServletRequest(HttpServletRequest request, String authorizationHeader) {
        super(request);
        this.AUTHORIZATION_HEADER = authorizationHeader;
    }

    public void setAuthorizationHeader(String value){
        this.authorizationHeader = Collections.singletonMap(AUTHORIZATION_HEADER, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = authorizationHeader.get(name);

        if (headerValue != null){
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> allCookies = new HashSet<String>(authorizationHeader.keySet());

        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            allCookies.add(n);
        }

        return Collections.enumeration(allCookies);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (authorizationHeader.containsKey(name)) {
            values.add(authorizationHeader.get(name));
        }
        return Collections.enumeration(values);
    }
}
