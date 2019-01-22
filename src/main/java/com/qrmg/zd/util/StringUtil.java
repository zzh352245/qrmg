package com.qrmg.zd.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 字符串工具类
 * 
 * @author wangys7@asiainfo-linkage.com
 * @since 2013-03-17 21:07:21
 */
public final class StringUtil {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
	/** Private Constructor **/
	private StringUtil() {
	}

	/**
	 * 判断字符串是否非null && 非空字符
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param) {
		return param != null && !"".equals(param.trim());
	}

	/**
	 * 判断字符串是否为null || 空字符串
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		return param == null || "".equals(param.trim()) || "null".equals(param.trim());
	}

	public static String trim(String str, String defStr) {
		return (str == null) || ("null".equals(str)) ? defStr : str.trim();
	}

	public static String trim(String str) {
		return trim(str, "");
	}

	public static boolean isEmptyExcludeNull(String param) {
		String str = trim(param);
		return str.length() < 1;
	}

	public static String replaceStrAllParamWithDelKey(final String str, String beforSplit1, String afterSplit1,
			Map<String, String> map) {
	    String beforSplit = beforSplit1;
	    String afterSplit = afterSplit1;
		if (map == null || map.size() == 0) {
			return str;
		}
		if (isEmptyExcludeNull(str)) {
			return null;
		}
		if (isEmptyExcludeNull(beforSplit)) {
			beforSplit = "${";
		}
		if (isEmptyExcludeNull(afterSplit)) {
			afterSplit = "}";
		}
		StringBuilder sb = new StringBuilder((int) (str.length() * 1.5));
		int cursor = 0;
		List<String> keyList = new ArrayList<String>();
		for (int start, end; (start = str.indexOf(beforSplit, cursor)) != -1
				&& (end = str.indexOf(afterSplit, start)) != -1;) {
			sb.append(str.substring(cursor, start));
			String key = str.substring(start + beforSplit.length(), end);
			if (map.get(trim(key)) != null) {
				sb.append("\"").append(map.get(trim(key))).append("\"");
				keyList.add(trim(key));
			} else {
				sb.append("\"").append("\"");
			}
			cursor = end + afterSplit.length();
		}
		sb.append(str.substring(cursor, str.length()));
		for (String key : keyList) {
			map.remove(key);
		}
		return sb.toString();
	}
	
	public static String delStrAllQuo(String str1,int length) {
	    String str = str1;
		if (StringUtil.isEmptyExcludeNull(str)) {
			return "";
		}
		str = str.replaceAll("\\n"," ").replaceAll("\\s{2,}"," ").replaceAll("\\\\\"", "").replaceAll("[\"']", "");
		if (str.length() >= length) {
			str = str.substring(0, Math.max(length-10,0));
		}
		return new String(str);
	}
	
	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
	 * 例如：HelloWorld->HELLO_WORLD
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underScoreName(String str) {
	    StringBuilder result = new StringBuilder();
	    if (str != null && str.length() > 0) {
	        // 将第一个字符处理成大写
	        result.append(str.substring(0, 1).toUpperCase());
	        // 循环处理其余字符
	        for (int i = 1; i < str.length(); i++) {
	            String s = str.substring(i, i + 1);
	            // 在大写字母前添加下划线
	            if (StringUtils.equals(s, StringUtils.upperCase(s)) && !Character.isDigit(s.charAt(0))) {
	                result.append("_");
	            }
	            // 其他字符直接转成大写
	            result.append(s.toUpperCase());
	        }
	    }
	    return result.toString();
	}
	
	public static boolean isHttpReqSuccess(String str1) {
	    String str = str1;
		if (StringUtil.isEmptyExcludeNull(str)) {
			return false;
		}
		str = str.replaceAll("\\s+", "");
		String status = "9999";
		if (str.indexOf("status") != -1) {
			status = new String(str.substring(str.indexOf("status:") + 7, str.indexOf("status") + 8));
		}
		return "0".equals(status);
	}
	
	public static boolean isMobileNO(String mobiles) {
		if (StringUtil.isEmptyExcludeNull(mobiles)) {
			return false;
		}
		Pattern p = Pattern.compile("^((13[4-9])|147|(15[0-2])|(15[7-9])|178|(18[2-4])|(18[7-8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * @Description:实体类属性是否为空 (有属性为空就返回true)
	 * @author zhangzheng
	 * @date 2017年11月10日 下午2:58:56
	 * @return boolean
	 * @param @return
	 */
	public static boolean isEmptyForEntity(Object obj){
	    boolean flag = false;
	    if(null == obj){
	        flag = true;
	        return flag;
	    }
	    try {
            for(Field f : obj.getClass().getDeclaredFields()){
                f.setAccessible(true);
                if(f.get(obj) == null){
                    flag = true;
                    return flag;
                }
            }
        } catch (Exception e) {
        }
	    return flag;
	}
	
	@SuppressWarnings("unchecked")
    public static List<Map<String, String>> convertStrtoListMap(String str) {
        List<Map<String, String>> resultList=null;
        try {
            resultList = MAPPER.readValue(str,List.class);
        } catch (Exception e) {
        }
        return resultList;
    }
	
	@SuppressWarnings("unchecked")
    public static Map<String, Object> convertStrtoMap(String str) {
        Map<String, Object> resultList = null;
        try {
            MAPPER.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            resultList = MAPPER.readValue(str, Map.class);
        } catch (Exception e) {
        }
        return resultList;
    }
	
	/**
	 * @Description: String转List<String>
	 * @author zhangzheng
	 * @date 2018年2月28日 上午11:14:57
	 * @param str 要转的字符串
	 * @param split 用来区分的字符
	 * @param @return
	 */
	public static List<String> convertStrToListStr(String str, String split){
	    String[] a = str.split(split);
	    return Arrays.asList(a);
	}
	
	/**
	 * @Description: List<Map<String, Object>>转String
	 * @author zhangzheng
	 * @date 2018年6月12日 上午10:18:46
	 * @param @param obj
	 * @param @return
	 */
	public static String convertListMapToString(List<Map<String, Object>> obj) {
        String str = "";
        try {
            str = MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
        }
        return str;
    }
	
}
