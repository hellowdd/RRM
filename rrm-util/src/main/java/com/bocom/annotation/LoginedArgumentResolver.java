package com.bocom.annotation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bocom.dto.session.SessionUserInfo;

public class LoginedArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter arg0,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest
				.getNativeRequest();
		SessionUserInfo sessionUserInfo = (SessionUserInfo) request.getSession()
				.getAttribute("sessionUserInfo");
		return sessionUserInfo;
	}

	@Override
	public boolean supportsParameter(MethodParameter arg0) {
		if (arg0.getParameterAnnotation(Logined.class) != null
				&& arg0.getParameterType() == SessionUserInfo.class) {
			return true;
		}
		return false;
	}

}
