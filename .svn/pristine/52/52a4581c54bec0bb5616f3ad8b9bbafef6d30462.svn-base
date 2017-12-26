package com.bocom.controller.view;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
//import com.bocom.annotation.Logined;
//import com.bocom.dto.session.SessionUserInfo;

@Controller
@RequestMapping("/manager/view")
public class UserViewController {
	
//	private static final Logger logger = LoggerFactory.getLogger(UserViewController.class); 
	
	  
	/**
	 *默认主页
	 */	
	@RequestMapping("/homepage")
	public String homepage( ModelMap model/*,@Logined SessionUserInfo sessionUserInfo*/){
/*		try {
			//带个人配置的应用列表，如果null则表示没有配置)，若已下线的app的对象只有appId其他值都为空（前端可判断给提示，或者不显示；）
			model.addAttribute("myAppInfoList", simpleBusiness.queryAppByUser(sessionUserInfo.getUserId().toString()));
		} catch (Exception e) {
			logger.info("homepage:{}", e.getMessage());
		}*/
		return "/homepage";
	} 
	
}
