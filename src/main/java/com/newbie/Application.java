package com.newbie;

import com.easyond.utils.DateUtil;
import com.easyond.utils.ObjectUtil;
import com.easyond.utils.StringUtil;
import com.newbie.model.User;
import com.newbie.utils.AppCache;
import com.newbie.utils.HttpContextUtil;
import com.newbie.utils.RedisUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@ServletComponentScan
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.newbie.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    @Bean(name = "jedisPool")
    public JedisPool jedisPool(@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") int port, @Value("${spring.redis.password}") String password, @Value("${spring.redis.database}") int database, @Value("${spring.redis.timeout}") int timeout, @Value("${spring.redis.pool.max-wait}") int maxWait, @Value("${spring.redis.pool.max-idle}") int maxIdle, @Value("${spring.redis.pool.min-idle}") int minIdle) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        return new JedisPool(jedisPoolConfig, host, port, timeout, StringUtil.invalid(password) ? null : password, database);
    }

    @Bean(name = "redisUtil")
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }

    @Bean
    public AppCache appCache() {
        return new AppCache();
    }
}

@WebListener
class ApplicationListener implements ServletContextListener {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    AppCache appCache;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("=================================================================ServletContex初始化");
        List<String> requestMappingList = new ArrayList<>();
        AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping = (AbstractHandlerMethodMapping<RequestMappingInfo>) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
        for (RequestMappingInfo requestMappingInfo : mapRet.keySet()) {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            requestMappingList.addAll(patterns);
        }
        appCache.setRequestMappingList(requestMappingList);
        appCache.set("appStartDate", DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("===================================================================ServletContex销毁");
    }
}

@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
class SessionFilter implements Filter {
    @Autowired
    AppCache appCache;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String body = StringUtil.InputStreamToString(request.getInputStream());
        request.setAttribute("body", body);
        Map<String, String> requestParameter = request.getParameterMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue()[0]));
        String date = DateUtil.getDateString(new Date(), "【yyyy-MM-dd HH:mm:ss】");
        String[] whitelist = {"/apiError", "/webError", "/admin", "/admin/index"};
        String[] rpcApiList = {"/api/customer/exportCustomerListByDate","/api/yiche/test", "/api/yiche/getKidBrandListByBrandId", "/api/yiche/getAllBrand", "/api/yiche/getCityListByProvinceId", "/api/yiche/getAllProvince", "/api/login", "/v1/sms/sendSms", "/v1/customer/addCustomer", "/api/doCooperation", "/api/insertYicheCity", "/api/insertYicheProduct"};
        String uri = request.getRequestURI().substring(request.getContextPath().length());
        if (StringUtil.isHave(whitelist, uri) || uri.startsWith("/static") || "/favicon.ico".equals(uri)) {
            filterChain.doFilter(request, response);
        } else {
            if (!appCache.getRequestMappingList().contains(uri)) {
                System.out.println(date + "request：====【找不到路径】===|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.objectToJsonString(requestParameter) + "|===|" + body + "|");
                Map<String, Object> result = new LinkedHashMap<String, Object>() {{
                    put("rspCode", 0);
                    put("rspInfo", "Path request denied");
                }};
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(ObjectUtil.mapToJsonString(result));
            } else if (StringUtil.isHave(rpcApiList, uri)) {
                System.out.println(date + "request：========rmi========|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.objectToJsonString(requestParameter) + "|===|" + body + "|");
                filterChain.doFilter(request, response);
            } else {
                User loginUser = HttpContextUtil.getLoginUser(request);
                if (loginUser != null) {
                    if (uri.startsWith("/api")) {
                        System.out.println(date + "request：========api========|" + "==" + loginUser.getName() + "==|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.objectToJsonString(requestParameter) + "|===|" + body + "|");
                    } else {
                        System.out.println(date + "request：========web========|" + "==" + loginUser.getName() + "==|" + request.getMethod() + "|" + uri + "|===|" + ObjectUtil.objectToJsonString(requestParameter) + "|===|" + body + "|");
                    }
                    filterChain.doFilter(request, response);
                } else {
                    response.sendRedirect("/admin");
                }
            }
        }
    }

    @Override
    public void destroy() {
    }
}
