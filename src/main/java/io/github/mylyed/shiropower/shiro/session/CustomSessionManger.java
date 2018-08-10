package io.github.mylyed.shiropower.shiro.session;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * 自定义会话管理器
 * <p>
 * 参考 MemorySessionDAO
 * Created by lilei on 2018/8/10.
 */
public class CustomSessionManger extends DefaultWebSessionManager {
    //解决
    // 重复去读sessionDao的问题

}
