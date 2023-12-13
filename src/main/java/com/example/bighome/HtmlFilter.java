package com.example.bighome;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebFilter("*.html")
public class HtmlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 在此处进行拦截逻辑判断
        // 例如，如果用户未登录，可以重定向到登录页面，否则继续执行下一个Filter或Servlet

        // 获取请求的资源路径
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        System.out.println("html url:" + requestURI);

        // 这里简单地示例，如果请求路径以.html结尾，则重定向到其他页面，可以根据实际需求修改
        if (requestURI.endsWith(".html")) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            System.out.println("[argv] the page is no access");
            // 如果没有拦截，继续执行下一个Filter或Servlet
            httpResponse.sendRedirect("showData?page=404page");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
