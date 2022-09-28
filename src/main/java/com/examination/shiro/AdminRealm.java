package com.examination.shiro;

import com.examination.bean.User;
import com.examination.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author he
 * @Data 2022/9/27 17:43
 */

public class AdminRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addStringPermission("user:add");
        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();//拿到当前的user对象
        info.addStringPermission(currentUser.getPerms());//给当前对象添加权限
        return info;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取token,并强转为(UsernamePasswordToken)类型，用户名密码会封装到token中
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //对从token中拿到用户名进行查询并赋值到user中
        User user = userService.queryUserByName(token.getUsername());
        if(user != null){//用户输入的判断用户名存在
            //参数：数据库中查到的user、数据库中查到的user的密码，当前这个对象 返回的SimpleAuthenticationInfo对象会进行密码的验证
            System.out.println("rengzheng");
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
        return null;
    }
}
