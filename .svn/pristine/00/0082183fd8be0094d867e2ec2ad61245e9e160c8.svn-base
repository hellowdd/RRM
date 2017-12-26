package com.bocom.controller.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocom.dto.img.FormatImageDto;
import com.bocom.service.ImageFormatService;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

@Controller
@RequestMapping("/manager/rest/user/")
public class UserRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ImageFormatService imageFormatService;

	/**
	 * 结合sso自动登录访问地址
	 */
	@RequestMapping(value = "/loginCasOut")
	@ResponseBody
	public ResponseVo loginCasOut(HttpSession session) {
		session.invalidate();
		return new ResponseVo();
	}
	

	@RequestMapping(value = "/querImageZipUrl")
	@ResponseBody
	public ResponseVo querImageZipUrl(@RequestBody FormatImageDto formatImage) {
		try {
			return ResponseVoUtil.success(imageFormatService.getUrl(formatImage));
		} catch (Exception e) {
			logger.error("获取图片转换后压缩地址失败" + e.getMessage());
			ResponseVoUtil.fail("获取图片转换后压缩地址失败" , e.getMessage());
		}
		return ResponseVoUtil.success(null);
	}
	
	@RequestMapping(value = "/downLoadFile")
	@ResponseBody
	public void downLoadFile(String fileName, HttpServletRequest request, HttpServletResponse response){
		imageFormatService.downLoadZip(fileName, request, response);
	}

}
