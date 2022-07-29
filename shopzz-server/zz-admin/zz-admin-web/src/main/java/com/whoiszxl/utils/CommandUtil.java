package com.whoiszxl.utils;

import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.Session;
import com.whoiszxl.entity.Server;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * ssh命令行工具类
 */
@Slf4j
public class CommandUtil {

    public static final Integer SSH_TIMEOUT = 1000 * 60 * 60 *24;

    public static String exec(Session session, String cmd) {
        try{
            return JschUtil.exec(session, cmd, StandardCharsets.UTF_8);
        }catch (Exception e) {
            log.error("连接SSH出错", e);
        }
        return null;
    }

    public static Session getSession(String sshHost, Integer sshPort, String sshUser, String sshPass) {
        try{
            Session session = JschUtil.getSession(sshHost, sshPort, sshUser, sshPass);
            if(session.isConnected()) {
                return session;
            }

            session = JschUtil.openSession(sshHost, sshPort, sshUser, sshPass);
            return session;
        }catch (Exception e) {
            log.error("获取session连接失败", e);
        }
        return null;
    }

    public static Session getSession(Server server) {
        return getSession(server.getServerOuterIp(), Integer.parseInt(server.getServerPort()), server.getServerUsername(), server.getServerPassword());
    }
}
