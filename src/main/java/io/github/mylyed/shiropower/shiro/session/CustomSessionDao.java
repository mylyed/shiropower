package io.github.mylyed.shiropower.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 自定义会话dao
 * <p>
 * 参考 MemorySessionDAO
 * Created by lilei on 2018/8/10.
 */
public class CustomSessionDao extends AbstractSessionDAO {


    ConcurrentMap<Serializable, Session> sessions = new ConcurrentHashMap<>();

    @Override
    protected Serializable doCreate(Session session) {
        //System.out.println("doCreate");
        Serializable sessionId = "shiro_" + generateSessionId(session);

        //把生成id 设置到创建的会话上
        //((SimpleSession) session).setId(sessionId);
        assignSessionId(session, sessionId);

        sessions.putIfAbsent(sessionId, session);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        // System.out.println("doReadSession");
        return sessions.get(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //System.out.println("update session");
        sessions.put(session.getId(), session);
    }

    @Override
    public void delete(Session session) {
        // System.out.println("delete session");
        sessions.remove(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        //System.out.println("getActiveSessions");
        return sessions.values();
    }
}
