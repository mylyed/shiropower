package io.github.mylyed.shiropower.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


/**
 * 认证测试
 * Created by lilei on 2018/8/9.
 */
public class AuthenticationTest {


    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    {
        simpleAccountRealm.addAccount("123", "pwd", "admin", "user");
    }

    @Test
    public void testAuthentication() {
        //构建环境

        DefaultSecurityManager defaultWebSecurityManager = new DefaultSecurityManager();

        defaultWebSecurityManager.setRealm(simpleAccountRealm);
        SecurityUtils.setSecurityManager(defaultWebSecurityManager);

        //主体提交认证请求
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("123", "pwd");


        //登录
        subject.login(usernamePasswordToken);


        System.out.println("是否认证" + subject.isAuthenticated());

        //登出
//        subject.logout();
//        System.out.println("是否认证" + subject.isAuthenticated());


        //检查是否有该角色的权限
        subject.checkRole("admin");
        subject.checkRole("admin1");
    }
}
