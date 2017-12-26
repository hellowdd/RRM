/** 
 * File Name:JSonUtils.java 
 * Package Name:com.bocom.util 
 * Date:2016年6月24日上午9:17:58 
 * Copyright (c) 2016, gaozhaosheng@bocom.cn All Rights Reserved. 
 * 
 */  
package com.bocom.util;

import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bocom.dto.deploy.AppDto;
import com.bocom.dto.deploy.DbDto;
import com.bocom.dto.deploy.DeployDto;
import com.fasterxml.jackson.databind.DeserializationFeature;

/** 
 * ClassName: JSonUtils <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2016年6月24日 上午9:17:58 <br/> 
 * 
 * @author gaozhaosheng 
 * @version  
 * @since JDK 1.7
 */
public class JsonUtil {
	
		private static Logger LOG = LoggerFactory.getLogger(JsonUtil.class);
	  
		static ObjectMapper objectMapper;
		
		private static String charset = "UTF-8";

	    /**
		 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
		 * (1)转换为普通JavaBean：readValue(json,Student.class)
		 * (2)转换为List:readValue(json,List.class).但是如果我们想把json转换为特定类型的List，比如List<Student>，就不能直接进行转换了。
		 *   因为readValue(json,List.class)返回的其实是List<Map>类型，你不能指定readValue()的第二个参数是List<Student>.class，所以不能直接转换。
		 *   我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List。
		 *(3)转换为Map：readValue(json,Map.class)
		 * 我们使用泛型，得到的也是泛型
	     * @param content 要转换的JavaBean类型
	     * @param valueType 原始json字符串数据
	     * @return JavaBean对象
	     */
	    public static <T> T readValue(String content, Class<T> valueType) {
	        if (objectMapper == null) {
	            objectMapper = new ObjectMapper();
	        }
	        try {
	            objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);  
	            return objectMapper.readValue(content, valueType);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    /**
		 * 把JavaBean转换为json字符串 
		 * (1)普通对象转换：toJson(Student) 
		 * (2)List转换：toJson(List)
		 * (3)Map转换:toJson(Map)
		 * 我们发现不管什么类型，都可以直接传入这个方法
	     * @param object JavaBean对象
	     * @return json字符串
	     */
	    public static String toJSon(Object object) {
	        if (objectMapper == null) {
	            objectMapper = new ObjectMapper();
	        }
	        try {
	            return objectMapper.writeValueAsString(object);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return null;
	    }
	    

	    public static <T> T readBase64Value(String content, Class<T> valueType) {
	        if (objectMapper == null) {
	            objectMapper = new ObjectMapper();
	        }
	        try {
	            return objectMapper.readValue(decodeBase64(content), valueType);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
		private static String decodeBase64(String str) {
			try {
				return new String(Base64.decodeBase64(str), charset);
			} catch (UnsupportedEncodingException e) {
				LOG.error("HttpClientUtil.decodeBase64 error", e);
			}
			return null;
		}
		
		/**
		 * 更换json中的内容
		 * 
		 * @param yJson 原来的json字符串
		 * @param jsonObject 新的json字符串
		 * @return
		 */
		public static String changeJson(String yJson, JSONObject jsonObject) {
			DeployDto deployDto = JsonUtil.readValue(yJson, DeployDto.class);
			List<DbDto> list = deployDto.getDb();
			DbDto dbDto = list.get(0);
			AppDto appDto = deployDto.getApp();
			String newSqlFIle = jsonObject.getJSONObject("module")
					.getJSONArray("db").getJSONObject(0).getString("fileName");
			String newWarFile = jsonObject.getJSONObject("module")
					.getJSONObject("app").getString("fileName");
			dbDto.setFileName(newSqlFIle);
			appDto.setFileName(newWarFile);
			appDto.setVersion(jsonObject.getString("version"));
			LOG.info("升级后的信息为：====================》"
					+ JsonUtil.toJSon(deployDto));
			return JsonUtil.toJSon(deployDto);

		}
		
	    
}
