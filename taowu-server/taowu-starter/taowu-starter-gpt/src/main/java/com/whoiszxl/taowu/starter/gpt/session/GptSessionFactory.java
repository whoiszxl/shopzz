package com.whoiszxl.taowu.starter.gpt.session;

/**
 * gpt session 工厂
 * @author whoiszxl
 */
public interface GptSessionFactory {

    /**
     * 打开 session会话
     * @return session
     */
    GptSession openSession();
}
