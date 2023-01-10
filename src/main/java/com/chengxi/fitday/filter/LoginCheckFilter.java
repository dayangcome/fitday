package com.chengxi.fitday.filter;

import com.alibaba.fastjson.JSON;
import com.chengxi.fitday.common.R;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//员工登录拦截器

@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/fitdaybackend/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher antPathMatcher=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String requesturi=request.getRequestURI();
        String [] urls=new String[]{
                "/fitdaybackend/images/**",
                "/fitdaybackend/myapi/**",
                "/fitdaybackend/page/login/login.html"
        };
        boolean check=check(urls,requesturi);
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("empid")!=null){
            filterChain.doFilter(request,response);
            return;
        }
        response.sendRedirect("/fitdaybackend/page/login/login.html");
    }

    public boolean check(String []urls,String requestURL){
        for(String url:urls){
            boolean match= antPathMatcher.match(url,requestURL);
            if(match){
                return true;
            }
        }
        return false;
    }
}
