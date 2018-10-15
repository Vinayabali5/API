package uk.ac.reigate.filters

import java.io.IOException;

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig;
import javax.servlet.ServletException
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

import com.google.common.collect.Sets


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class SimpleCorsFilter implements Filter {
    
    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, authorization, cache-control, www-authenticate")
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Expose-Headers", "x-total-items, x-total-pages")
        // Access-Control-Expose-Headers: x-total-items, x-total-pages required to expose pagination data returned in header - certainly required for Exams project basedata viewer
        if (req.getMethod() != 'OPTIONS') {
            chain.doFilter(req, res)
        } else {
        }
    }
    
    void init(FilterConfig filterConfig) {}
    
    void destroy() {}
    
    
    
}