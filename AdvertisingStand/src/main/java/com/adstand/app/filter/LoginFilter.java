package com.adstand.app.filter;

import com.adstand.app.services.LoginBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Inject
    private LoginBean loginBean;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(loginBean.isHaveManagerRights()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/AdvertisingStand/login.xhtml");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // not needed
    }

    @Override
    public void destroy() {
        // not needed
    }
}