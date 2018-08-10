package io.github.mylyed.shiropower.shiro.filter;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;

/**
 * 自定义 过滤器
 * 继承授权过滤器
 * Created by lilei on 2018/8/10.
 */
public class CustomFilter extends AuthorizationFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("自定义过滤器" + Arrays.toString((String[]) mappedValue));

        return true;
    }
}

