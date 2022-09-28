package com.examination.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
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
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        //设置退出页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/login");
        // 设置成功之后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        //
        map.put("/admin/login","anon");
        map.put("/admin/login1","anon");
        map.put("/css/**", "anon");
        map.put("/fonts/**", "anon");
        map.put("/images/**", "anon");
        map.put("/js/**", "anon");
        //请求/admin/common必须拥有管理员权限
//        map.put("/admin/common","perms[admin]");
        map.put("/admin/index","perms[admin]");
        //所有页面都要认证才能进入
//        map.put("/admin/*","authc");
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
