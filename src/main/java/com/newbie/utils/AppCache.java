package com.newbie.utils;


import com.easyond.utils.StringUtil;

import java.util.*;

public class AppCache {

    //系统参数配置缓存信息
    private static Map<String, String> systemConfig = null;
    private static List<String> requestMappingList = new ArrayList<>();
    private static Map<String, Object> systemCache = new HashMap<>();

    public Map<String, String> getSystemConfig() {
        if (systemConfig == null) {
            throw new AppException("读取配置失败");
        }
        return systemConfig;
    }

    public void setSystemConfig(Map<String, String> systemConfig) {
        AppCache.systemConfig = systemConfig;
    }

    public Map<String, Object> set(String key, Object value) {
        systemCache.put(key, value);
        return systemCache;
    }

    public <T> T get(String key) {
        return (T) systemCache.get(key);
    }

    public String getConfigValue(String key, String defaultValue) {
        String value = "";
        value = getSystemConfig().get(key);
        if (StringUtil.invalid(value)) {
            value = defaultValue;
        }
        return value;
    }

    public List<String> getRequestMappingList() {
        return new ArrayList<>(new TreeSet<>(requestMappingList));
    }

    public void setRequestMappingList(List<String> requestMappingList) {
        AppCache.requestMappingList = requestMappingList;
    }
}
