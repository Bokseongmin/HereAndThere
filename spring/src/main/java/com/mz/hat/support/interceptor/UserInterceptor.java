package com.mz.hat.support.interceptor;

import com.mz.hat.support.annotation.MSPSession;
import com.mz.hat.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logger.info(">>>> Session user: {}", request.getSession().getAttribute("user"));

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        MSPSession mspSession = handlerMethod.getMethodAnnotation(MSPSession.class);

        if (mspSession == null) {
            return true;
        }

        HttpSession session = request.getSession();

        if (session == null) {
            response.sendRedirect("/error");
            return false;
        }

        UserVo userVo = (UserVo) session.getAttribute("user");
        if (userVo == null) {
            response.sendRedirect("/error");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
