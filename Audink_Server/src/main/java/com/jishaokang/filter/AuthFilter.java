package com.jishaokang.filter;

import com.alibaba.fastjson.JSON;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.jishaokang.cache.ResultCache;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*");
        // 假定session中有userId
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if ((request.getServletPath().contains("api") && !request.getServletPath().contains("all")) && userId == null) {
           /* PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(ResultCache.PERMISSION_DENIED));
            writer.flush();
            return ;*/
            request.getSession().setAttribute("userId", 1);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

}