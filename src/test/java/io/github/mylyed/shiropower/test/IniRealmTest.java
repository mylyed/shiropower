package io.github.mylyed.shiropower.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * ini文件配置
 * Created by lilei on 2018/8/9.
 */
public class IniRealmTest {


    @Test
    public void testAuthentication() {
        //构建环境
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        System.out.println(iniRealm.getIni().getSections());


        DefaultSecurityManager defaultWebSecurityManager = new DefaultSecurityManager();

        defaultWebSecurityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(defaultWebSecurityManager);

        //主体提交认证请求
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("user", "123");


        //登录
        subject.login(usernamePasswordToken);


        System.out.println("是否认证" + subject.isAuthenticated());

        //登出
//        subject.logout();
//        System.out.println("是否认证" + subject.isAuthenticated());


        //检查是否有该角色的权限
        subject.checkRole("admin");
        System.out.println("是admin角色");


        subject.checkPermission("user:add");
        System.out.println("有user:add权限");

        subject.checkRole("admin1");
        subject.checkPermission("user:test");
    }
}
