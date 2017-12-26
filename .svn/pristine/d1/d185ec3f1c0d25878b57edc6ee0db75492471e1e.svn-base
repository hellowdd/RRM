package com.bocom.controller.rest;

import com.bocom.dto.session.SessionUserInfo;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manager/loginAction")
public class LoginRestController {

	@Autowired
	private HttpServletRequest request;

	@Value("${cas.casServerLoginUrl}")
	private String casServerUrlPrefix;



	/**
	 * 结合sso自动登录访问地址
	 */
	@RequestMapping(value = "/loginCasOut", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo loginCasOut(HttpSession session) {
		session.invalidate();
		Map map = new HashMap();
		map.put("casServerUrlPrefix",casServerUrlPrefix.replace("/login", ""));
		map.put("scheme",request.getScheme());
		map.put("serverName",request.getServerName());
		map.put("serverPort",request.getServerPort());
		map.put("contextPath",request.getContextPath());
		return ResponseVoUtil.success(map);
	}

	/**
	 * 结合sso自动登录访问地址
	 */
	@RequestMapping(value = "/getUserInffo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo getUserInffo(HttpSession session) {
		SessionUserInfo sessionUserInfo= (SessionUserInfo) session.getAttribute("sessionUserInfo");
		return ResponseVoUtil.success(sessionUserInfo);
	}

}
