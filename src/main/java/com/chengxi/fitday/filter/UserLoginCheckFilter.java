package com.chengxi.fitday.filter;

import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//用户登录拦截器

@WebFilter(filterName = "UserLoginCheckFilter",urlPatterns = "/fitdayfun/*")
public class UserLoginCheckFilter implements Filter {
    public static final AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String requesturi=request.getRequestURI();
        String [] urls=new String[]{
                "/fitdayfun/assets/**",
                "/fitdayfun/myapi/**",
                "/fitdayfun/page/login/login.html",
                "/fitdayfun/page/accountexplain/**",
                "/fitdayfun/page/rules/**",
                "/fitdayfun/page/changecode/**"
        };
        boolean check=check(urls,requesturi);
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("useruid")!=null){
            filterChain.doFilter(request,response);
            return;
        }
        response.sendRedirect("/fitdayfun/page/login/login.html");
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
