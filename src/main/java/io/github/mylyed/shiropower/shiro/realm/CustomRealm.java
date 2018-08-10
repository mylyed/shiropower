package io.github.mylyed.shiropower.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义
 * 可以参考SimpleAccountRealm
 * Created by lilei on 2018/8/8.
 */
public class CustomRealm extends AuthorizingRealm {


    /**
     * 授权
     * 获取用户的角色和权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权");

        Set<String> roles = new HashSet<>();

        roles.add("admin");
        roles.add("user");

        Set<String> permissions = new HashSet<>();

        permissions.add("user:add");
        permissions.add("user:delete");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);

        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * 密码验证不在这里
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String pwd = getUser(upToken.getUsername());
        if (pwd != null) {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(upToken.getUsername(), pwd, getName());
            //设置盐 数据库要存储这个值
            //authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("盐"));
            return authenticationInfo;
        }
        return null;
    }

    protected final Map<String, String> usersAndPwd;

    public CustomRealm() {
        this.usersAndPwd = new LinkedHashMap<>();
        usersAndPwd.put("user", "pwd");
        //数据库存储密文
        usersAndPwd.put("md5", new Md5Hash("111").toString());
        //加了盐的密码
        usersAndPwd.put("md52", new Md5Hash("111", "盐").toString());
    }

    private String getUser(String username) {
        return usersAndPwd.get(username);
    }
}
