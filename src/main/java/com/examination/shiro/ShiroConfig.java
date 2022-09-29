package com.examination.shiro;

import com.examination.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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
        map.put("/","anon");
        //登录请求放行
        map.put("/loginInto","anon");
        //注册页面放行
        map.put("/registration","anon");
        //所有的静态文件都放行
        map.put("/css/**", "anon");
        map.put("/fonts/**", "anon");
        map.put("/images/**", "anon");
        map.put("/js/**", "anon");
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
        return new AdminRealm();
    }
}
