package com.rolex.tips.servlet;

import com.rolex.tips.wrapper.MyHttpServletRequestWrapper;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class MyDispatcherServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new MyHttpServletRequestWrapper(request), response);
    }
}
