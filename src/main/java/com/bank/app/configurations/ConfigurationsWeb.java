package com.bank.app.configurations;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@WebFilter(urlPatterns = "/**")
public class ConfigurationsWeb implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        var header = req.getHeader("Access-Control-Allow-Origin");

        ((HttpServletResponse) res).addHeader("Access-Control-Allow-Origin", header);
        ((HttpServletResponse) res).addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        ((HttpServletResponse) res).addHeader("Access-Control-Allow-Headers", "*");
        chain.doFilter(req, res);
    }

}
