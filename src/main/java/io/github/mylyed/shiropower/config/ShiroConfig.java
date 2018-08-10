package io.github.mylyed.shiropower.config;

import io.github.mylyed.shiropower.shiro.filter.CustomFilter;
import io.github.mylyed.shiropower.shiro.realm.CustomRealm;
import io.github.mylyed.shiropower.shiro.session.CustomSessionDao;
import io.github.mylyed.shiropower.shiro.session.CustomSessionManger;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro 配置
 * Created by lilei on 2018/8/8.
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm() {
        CustomRealm customRealm = new CustomRealm();


        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        return customRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm());

        //会话管理器
        DefaultWebSessionManager sessionManager = new CustomSessionManger();
        sessionManager.setSessionDAO(new CustomSessionDao());
        defaultWebSecurityManager.setSessionManager(sessionManager);


        //设置缓存管理器
        defaultWebSecurityManager.setCacheManager(new MemoryConstrainedCacheManager());


        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //登录页面 用户没登录会自动跳转到这个页面
        shiroFilterFactoryBean.setLoginUrl("/loginPage");
        //登录成功跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/");
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        //登录接口
        filterChainDefinitionMap.put("/subLogin", "anon");
//        filterChainDefinitionMap.put("/403", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/logout", "logout");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        Map<String, Filter> filters = new HashMap<>();

        //注册自定义的过滤器
        filters.put("custom", new CustomFilter());
        //使用自定义的过滤器
        filterChainDefinitionMap.put("/customFilter", "custom[aaa,bbb,123]");

        shiroFilterFactoryBean.setFilters(filters);


        return shiroFilterFactoryBean;
    }


    //------------------为了注释拦截
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
