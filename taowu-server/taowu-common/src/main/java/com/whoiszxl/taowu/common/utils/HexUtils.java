package com.whoiszxl.taowu.common.utils;

public class HexUtils {

    public static String bytesToHexString(byte[] ...data) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte[] datum : data) {
            hexBuilder.append(bytesToHex(datum));
        }
        return hexBuilder.toString();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexBuilder.append(String.format("%02x", b));
        }
        return hexBuilder.toString();
    }
}
