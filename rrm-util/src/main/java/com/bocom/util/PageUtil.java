package com.bocom.util;

import com.bocom.dto.AdvancedDto;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PageUtil {
	private static Integer defaultPageSize = 20;
	private static Integer defaultPageNum = 1;

	public static void setParams(HttpServletRequest request,
			Map<String, Object> params) {
		String pageSize = request.getParameter("pageSize");
		String pageNum = request.getParameter("pageNum");
		params.put("pageSize", StringUtils.isEmpty(pageSize) ? defaultPageSize
				: Integer.valueOf(pageSize));
		params.put("pageNum", StringUtils.isEmpty(pageNum) ? defaultPageNum
				: Integer.valueOf(pageNum));
	}


	public static void setParams1(HttpServletRequest request,
								 Map<String, Object> params) {
//		String pageSize = request.getParameter("pageSize");
//		String pageNum = request.getParameter("pageNum");
		if(null==params.get("pageNum")){
			params.put("pageNum", defaultPageNum);
		}
		if(null==params.get("pageSize")){
			params.put("pageSize", defaultPageSize);
		}
//		params.put("pageSize", StringUtils.isEmpty(pageSize) ? defaultPageSize
//				: Integer.valueOf(pageSize));
//		params.put("pageNum", StringUtils.isEmpty(pageNum) ? defaultPageNum
//				: Integer.valueOf(pageNum));
	}


	public static void setParams2(HttpServletRequest request,
								  AdvancedDto advancedDto) {
//		String pageSize = request.getParameter("pageSize");
//		String pageNum = request.getParameter("pageNum");
		if(null==advancedDto.getPageNum()){
			advancedDto.setPageNum(defaultPageNum);
		}
		if(null==advancedDto.getPageSize()){
			advancedDto.setPageSize(defaultPageSize);
		}
//		params.put("pageSize", StringUtils.isEmpty(pageSize) ? defaultPageSize
//				: Integer.valueOf(pageSize));
//		params.put("pageNum", StringUtils.isEmpty(pageNum) ? defaultPageNum
//				: Integer.valueOf(pageNum));
	}


	public static Map covertMap(Object[] key, Object[] value) {
		Map returnMap = new HashMap();
		returnMap.put("success", false);
		if (null == key || null == value) {
			return returnMap;
		}
		if (key.length != value.length) {
			returnMap.put("message", "数据组装错误");
			return returnMap;
		}
		for (int i = 0, len = key.length; i < len; i++) {
			returnMap.put(key[i], value[i]);
		}
		returnMap.put("success", true);
		return returnMap;
	}

	public static void dealPage(Map<String, Object> param) {
		if (null != param) {
			Integer pageNum = (Integer) param.get("pageNum");
			Integer pageSize = (Integer) param.get("pageSize");
			if (null != pageNum && null != pageSize) {
				PageHelper.startPage(pageNum, pageSize);
			}
		}
	}

	public static void dealPage1(AdvancedDto advancedDto) {
		if (null != advancedDto) {
			Integer pageNum = (Integer) advancedDto.getPageNum();
			Integer pageSize = (Integer) advancedDto.getPageSize();
			if (null != pageNum && null != pageSize) {
				PageHelper.startPage(pageNum, pageSize);
			}
		}
	}
}
