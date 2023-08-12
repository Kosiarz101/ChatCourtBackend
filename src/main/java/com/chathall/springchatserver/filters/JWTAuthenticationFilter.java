package com.chathall.springchatserver.filters;

import com.chathall.springchatserver.models.AuthorizationHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.cookie.name}")
    private String accessTokenCookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = getJwtFromCookie(httpServletRequest);
        if (jwt != null) {
            AuthorizationHttpServletRequest authorizationRequest = new AuthorizationHttpServletRequest(httpServletRequest, accessTokenCookieName);
            authorizationRequest.setAuthorizationHeader("Bearer " + jwt);
            filterChain.doFilter(authorizationRequest, httpServletResponse);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        for (Cookie cookie : cookies) {
            if (accessTokenCookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}