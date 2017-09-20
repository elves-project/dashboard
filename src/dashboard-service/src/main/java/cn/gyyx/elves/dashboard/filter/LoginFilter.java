package cn.gyyx.elves.dashboard.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : east.Fu
 * @Description : web 过滤器
 * @Date : Created in  2017/6/29 9:20
 */
public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replaceAll("/+", "/").replaceAll("/+", "/");
        if (requestURI.equals("")||requestURI.equals("/")) {
            request.getRequestDispatcher("/main").forward(request,response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
