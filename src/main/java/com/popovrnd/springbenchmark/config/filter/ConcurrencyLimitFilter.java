package com.popovrnd.springbenchmark.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class ConcurrencyLimitFilter extends OncePerRequestFilter {

    private final Semaphore semaphore;

    public ConcurrencyLimitFilter(int maxConcurrency) {
        this.semaphore = maxConcurrency > 0
                ? new Semaphore(maxConcurrency)
                : null;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        if (semaphore == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!semaphore.tryAcquire()) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Concurrency limit exceeded");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            semaphore.release();
        }
    }
}
