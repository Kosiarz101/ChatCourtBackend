package com.chathall.springchatserver.filters;

import com.chathall.springchatserver.models.AuthorizationHttpServletRequest;
import com.chathall.springchatserver.services.JWTTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.cookie.name}")
    private String accessTokenCookieName;
    private final JWTTokenService tokenService;

    private final List<String> shouldNotFilterUrls = List.of(
            "/auth/login", "/user/exists-by-email", "/ws"
    );

    private final List<String> shouldNotFilterPOSTUrls = List.of("/user");

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = tokenService.getJwtFromCookie(httpServletRequest);
        if (jwt != null) {
            AuthorizationHttpServletRequest authorizationRequest = new AuthorizationHttpServletRequest(httpServletRequest, accessTokenCookieName);
            authorizationRequest.setAuthorizationHeader("Bearer " + jwt);
            filterChain.doFilter(authorizationRequest, httpServletResponse);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (shouldNotFilterUrls.stream().anyMatch(url -> url.matches(request.getRequestURI())))
            return true;
        if (request.getMethod().equals(HttpMethod.POST.toString())) {
            if (shouldNotFilterPOSTUrls.stream().anyMatch(url -> url.matches(request.getRequestURI())))
                return true;
        }
        return false;
    }
}