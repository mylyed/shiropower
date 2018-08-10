package io.github.mylyed.shiropower.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * ini文件配置
 * Created by lilei on 2018/8/9.
 */
public class JdbcRealmTest {

    //数据源
    DataSource dataSource = null;

    @Test
    public void testAuthentication() {
        //构建环境
        JdbcRealm jdbcRealm = new JdbcRealm();


        jdbcRealm.setDataSource(dataSource);
        //查询权限数据
        jdbcRealm.setPermissionsLookupEnabled(true);

        DefaultSecurityManager defaultWebSecurityManager = new DefaultSecurityManager();

        defaultWebSecurityManager.setRealm(jdbcRealm);
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
