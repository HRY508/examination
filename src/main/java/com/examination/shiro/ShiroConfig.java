package com.examination.shiro;

import com.examination.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @Description Shiro配置类主要用来配置:Realm、DefaultWebSecurityManager、ShiroFilterFactoryBean
 * @Author he
 * @Data 2022/9/27 17:42
 */

//配置类
@Configuration
public class ShiroConfig {

    //记住我Cookie配置
    @Bean
    public SimpleCookie rememberMeCookie(){
        // 这个参数是 cookie 的名称，叫什么都行,rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 记住我 cookie 生效时间30天 ,单位是秒
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    //Cookie安全管理器对记住我的cookie进行加密
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }


//    @Bean
//    public FormAuthenticationFilter formAuthenticationFilter(){
//        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
//        // 对应 rememberMeCookie() 方法中的 name
//        formAuthenticationFilter.setRememberMeParam("rememberMe");
//        return formAuthenticationFilter;
//    }

    //3、同样，把安全管理器注入到shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //权限设置：
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置退出页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
        //设置没有权限跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        //注意优先级从上到下依次降低---
        //登录页面放行
        map.put("/login","anon");
        //注册放行
        map.put("/registration","anon");
        map.put("/register","anon");
        map.put("/","anon");
        //登录请求放行
        map.put("/loginInto","anon");
        //注册页面放行
        //所有的静态文件都放行
        map.put("/css/**", "anon");
        map.put("/fonts/**", "anon");
        map.put("/images/**", "anon");
        map.put("/js/**", "anon");
        map.put("/static/**","anon");
        //请求所有的管理员下的界面都要有管理员权限
        map.put("/admin/**","perms["+"admin"+"]");
        //请求所有的用户下的界面都要有用户权限
        map.put("/user/**","perms["+"user"+"]");
        //进入的所有页面都要登录
        map.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    //2、@Qualifier("adminRealm")将容器中的adminRealm注入到安全管理器中,设置安全管理器的Realm
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("adminRealm") AdminRealm adminRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(adminRealm);
        return defaultWebSecurityManager;
    }
    //1、注入Realm
    @Bean
    public AdminRealm adminRealm(){
        AdminRealm realm = new AdminRealm();
        //设置加密的方式
        //设置加密算法为md5
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置加密次数
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }
}
