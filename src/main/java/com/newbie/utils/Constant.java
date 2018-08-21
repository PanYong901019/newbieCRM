package com.newbie.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constant {

    public static Map<String, String> statusConstant = new LinkedHashMap<String, String>() {{
        put("0", "无效");
        put("1", "有效");
    }};

    public static Map<String, String> customerPlatformConstant = new LinkedHashMap<String, String>() {{
        put("qdzq", "渠道抓取");
        put("zfb", "支付宝");
        put("xmt", "新媒体推广");
    }};

    public static Map<String, String> customerInputTypeConstant = new LinkedHashMap<String, String>() {{
        put("form", "表单");
        put("leyu", "乐语");
    }};

    public static Map<String, String> customerSourceConstant = new LinkedHashMap<String, String>() {{
        put("audition", "试听");
        put("ui", "UI设计");
        put("java", "Java");
        put("3d", "3D建模");
        put("data", "大数据");
        put("fellowship", "助学金");
        put("vrar", "VR/AR");
        put("websafe", "网络安全");
        put("python", "Python");
        put("html", "H5");
    }};

    public static Map<String, String> customerFlagConstant = new LinkedHashMap<String, String>() {{
        put("1", "未接通");
        put("2", "无意向不需跟进客户");
        put("3", "无意向可跟进客户");
        put("4", "有意向可跟进客户");
        put("5", "重点跟进客户");
    }};

}
