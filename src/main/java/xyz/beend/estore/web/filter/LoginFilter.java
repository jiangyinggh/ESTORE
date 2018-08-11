package xyz.beend.estore.web.filter;

import xyz.beend.estore.common.bean.Customer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 对涉及到需要超级用户的操作，进行登录拦截，
 * 未登录则让页面跳转至登录界面
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {     //用户不存在，页面跳转至登录界面
            String ajaxHeader = request.getHeader("X-Requested-With");
            //对于ajax请求，由于session过期而被拦截的处理方案
            if("XMLHttpRequest".equals(ajaxHeader)) {  //判断为ajax请求,对session过期进行处理
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write("{\"status\":401,\"msg\":\"您的登录已过期\"}");
                out.flush();
                out.close();
            }else {
                response.sendRedirect("/login.html");
            }
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
