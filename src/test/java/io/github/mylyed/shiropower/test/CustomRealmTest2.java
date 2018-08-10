package io.github.mylyed.shiropower.test;

import io.github.mylyed.shiropower.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * ini文件配置
 * Created by lilei on 2018/8/9.
 */
public class CustomRealmTest2 {


    @Test
    public void testAuthentication() {
        //构建环境
        CustomRealm customRealm = new CustomRealm();


        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        matcher.setHashIterations(1);

        customRealm.setCredentialsMatcher(matcher);


        DefaultSecurityManager defaultWebSecurityManager = new DefaultSecurityManager();

        defaultWebSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultWebSecurityManager);

        //主体提交认证请求
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("md52", "111");


        //登录
        subject.login(usernamePasswordToken);


        System.out.println("是否认证" + subject.isAuthenticated());

        //登出
//        subject.logout();
//        System.out.println("是否认证" + subject.isAuthenticated());


        //  检查是否有该角色的权限
        subject.checkRole("admin");
        System.out.println("是admin角色");
//
//
        subject.checkPermission("user:add");
        System.out.println("有user:add权限");
//
//        subject.checkRole("admin1");
//        subject.checkPermission("user:test");
    }
}
