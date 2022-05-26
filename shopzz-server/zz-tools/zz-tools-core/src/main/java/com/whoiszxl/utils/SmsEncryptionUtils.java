package com.whoiszxl.utils;

import org.springframework.util.DigestUtils;

public class SmsEncryptionUtils {
    /**
     * 秘钥计算方法
     *
     * @param timestamp
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     */
    public static String encode(String timestamp, String accessKeyId, String accessKeySecret) {
        byte[] timestamps = timestamp.getBytes();
        byte[] cakis = new byte[timestamps.length];
        byte[] cakss = new byte[timestamps.length];
        for (int i = 0; i < timestamps.length; i++) {
            int ci = (int) timestamps[i] - (int) ('0');
            byte caki = (byte) accessKeyId.charAt(ci);
            byte caks = (byte) accessKeySecret.charAt(ci);
            cakis[i] = (byte) (caki >>> (i > 6 ? i / 2 : i) & i);
            cakss[i] = (byte) (caks | i);
        }
        byte[] accessKeyIds = accessKeyId.getBytes();
        byte[] accessKeySecrets = accessKeySecret.getBytes();

        byte[] md5Bytes = new byte[cakis.length + cakss.length + accessKeyIds.length + accessKeySecrets.length];

        System.arraycopy(cakis, 0, md5Bytes, 0, cakis.length);
        System.arraycopy(accessKeyIds, 0, md5Bytes, cakis.length, accessKeyIds.length);
        System.arraycopy(cakss, 0, md5Bytes, accessKeyIds.length, cakss.length);
        System.arraycopy(accessKeySecrets, 0, md5Bytes, cakss.length, accessKeySecrets.length);

        return DigestUtils.md5Digest(DigestUtils.md5Digest(md5Bytes)).toString();
    }
}
