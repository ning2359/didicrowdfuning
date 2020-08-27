package com.didi.admin.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.didi.crowd.CrowdConstant;
import com.didi.crowd.CrowdUtil;
import com.didi.crowd.ResultEntity;
import com.didi.exception.AccessForbiddenException;
import com.didi.exception.AcctKeyException;
import com.didi.exception.LoginFailedException;
import com.didi.exception.UpdateAcctRepeatExcepyion;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// @ControllerAdvice表示当前类是一个基于注解的异常处理器类

@ControllerAdvice()
public class CrowdExceptionResolver {

	@ExceptionHandler(value = AccessDeniedException.class)
	public ModelAndView accessForbiddenException(
			AccessDeniedException exception,
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		String viewName = "system_error";
		return commonResolve(viewName, exception, request, response);
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView resolveMathException(
				Exception exception,
				HttpServletRequest request,
				HttpServletResponse response
			) throws IOException {
		String viewName = "system_error";
		return commonResolve(viewName, exception, request, response);
	}
    @ExceptionHandler(value = AcctKeyException.class)
    public ModelAndView resolveAccyKeyException(
            AcctKeyException exception,
            HttpServletRequest request,
            HttpServletResponse response
    	) throws IOException {
        String viewName = "admin_add";
        return commonResolve(viewName, exception, request, response);
    }
    @ExceptionHandler(value = UpdateAcctRepeatExcepyion.class)
    public ModelAndView resolveUpdateAcctRepeat(
            UpdateAcctRepeatExcepyion exception,
            HttpServletRequest request,
            HttpServletResponse response
    	) throws IOException {
        String viewName = "system_error";
        return commonResolve(viewName, exception, request, response);
    }
	private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean judgeResult = CrowdUtil.judgeRequestType(request);
		if(judgeResult) {
			ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
			Gson gson = new Gson();
			String json = gson.toJson(resultEntity);
			response.getWriter().write(json);
			return null;
		}else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject(CrowdConstant.MESSAGE_NAME_EXCEPTION, exception);
			modelAndView.setViewName(viewName);
			return modelAndView;
		}
	}
}
