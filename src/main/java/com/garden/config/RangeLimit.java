package com.garden.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RangeLimit extends OncePerRequestFilter {

    private Map<String, AtomicInteger> requestsCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> requestTimesStamps = new ConcurrentHashMap<>();

    private final int MAX_REQUEST = 6;
    private final long TIME_DURATION = 60000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        requestsCounts.computeIfAbsent(clientIp, k -> new AtomicInteger(0));
        requestTimesStamps.put(clientIp, currentTime);

        long windowStart = requestTimesStamps.get(clientIp);

        if(currentTime - windowStart > TIME_DURATION) {
            int requestCount = requestsCounts.get(clientIp).incrementAndGet();
            if (requestCount > MAX_REQUEST) {
                response.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
                response.getWriter().write("Demasiadas solicitudes. Inténtalo más tarde.");
                return;
            }
        } else {
            // Reiniciar contador y ventana de tiempo
            requestsCounts.put(clientIp, new AtomicInteger(1));
            requestTimesStamps.put(clientIp, currentTime);
        }

        filterChain.doFilter(request, response);
    }
}
