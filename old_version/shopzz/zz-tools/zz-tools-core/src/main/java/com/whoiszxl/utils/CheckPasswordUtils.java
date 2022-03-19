package com.whoiszxl.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 校验密码强度
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class CheckPasswordUtils {
    public enum LEVEL {
        EASY, MIDIUM, STRONG, VERY_STRONG, EXTREMELY_STRONG
    }

    private final static int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
            Integer.MAX_VALUE};

    private static final int NUM = 1;
    private static final int SMALL_LETTER = 2;
    private static final int CAPITAL_LETTER = 3;
    private static final int OTHER_CHAR = 4;

    /**
     * Simple password dictionary
     */
    private final static String[] DICTIONARY = {"password", "abc123", "iloveyou", "adobe123", "123123", "sunshine",
            "1314520", "a1b2c3", "123qwe", "aaa111", "qweasd", "admin", "passwd"};

    /**
     * Check character's type, includes num, capital letter, small letter and other character.
     *
     * @param c
     * @return
     */
    private static int checkCharacterType(char c) {
        if (c >= 48 && c <= 57) {
            return NUM;
        }
        if (c >= 65 && c <= 90) {
            return CAPITAL_LETTER;
        }
        if (c >= 97 && c <= 122) {
            return SMALL_LETTER;
        }
        return OTHER_CHAR;
    }

    /**
     * Count password's number by different type
     *
     * @param passwd
     * @param type
     * @return
     */
    private static int countLetter(String passwd, int type) {
        int count = 0;
        if (null != passwd && passwd.length() > 0) {
            for (char c : passwd.toCharArray()) {
                if (checkCharacterType(c) == type) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Check password's strength
     *
     * @param passwd
     * @return strength level
     */
    public static int checkPasswordStrength(String passwd) {
        if (StringUtils.isBlank(passwd)) {
            throw new IllegalArgumentException("password is empty");
        }
        int len = passwd.length();
        int level = 0;

        // increase points
        if (countLetter(passwd, NUM) > 0) {
            level++;
        }
        if (countLetter(passwd, SMALL_LETTER) > 0) {
            level++;
        }
        if (len > 4 && countLetter(passwd, CAPITAL_LETTER) > 0) {
            level++;
        }
        if (len > 6 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }

        if (len > 4 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                || countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }

        if (len > 6 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 || countLetter(passwd, NUM) > 0
                && countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                && countLetter(passwd, OTHER_CHAR) > 0 || countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }

        if (len > 8 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }

        if (len > 6 && countLetter(passwd, NUM) >= 3 && countLetter(passwd, SMALL_LETTER) >= 3
                || countLetter(passwd, NUM) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3
                || countLetter(passwd, NUM) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3
                || countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, CAPITAL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }

        if (len > 8 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 || countLetter(passwd, NUM) >= 2
                && countLetter(passwd, SMALL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, NUM) >= 2 && countLetter(passwd, CAPITAL_LETTER) >= 2
                && countLetter(passwd, OTHER_CHAR) >= 2 || countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }

        if (len > 10 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }

        if (countLetter(passwd, OTHER_CHAR) >= 3) {
            level++;
        }
        if (countLetter(passwd, OTHER_CHAR) >= 6) {
            level++;
        }

        if (len > 12) {
            level++;
            if (len >= 16) {
                level++;
            }
        }

        // decrease points
        if ("abcdefghijklmnopqrstuvwxyz".indexOf(passwd) > 0 || "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(passwd) > 0) {
            level--;
        }
        if ("qwertyuiop".indexOf(passwd) > 0 || "asdfghjkl".indexOf(passwd) > 0 || "zxcvbnm".indexOf(passwd) > 0) {
            level--;
        }
        if (StringUtils.isNumeric(passwd) && ("01234567890".indexOf(passwd) > 0 || "09876543210".indexOf(passwd) > 0)) {
            level--;
        }

        if (countLetter(passwd, NUM) == len || countLetter(passwd, SMALL_LETTER) == len
                || countLetter(passwd, CAPITAL_LETTER) == len) {
            level--;
        }

        if (len % 2 == 0) { // aaabbb
            String part1 = passwd.substring(0, len / 2);
            String part2 = passwd.substring(len / 2);
            if (part1.equals(part2)) {
                level--;
            }
            if (StringUtils.isBlank(part1) && StringUtils.isBlank(part2)) {
                level--;
            }
        }
        if (len % 3 == 0) { // ababab
            String part1 = passwd.substring(0, len / 3);
            String part2 = passwd.substring(len / 3, len / 3 * 2);
            String part3 = passwd.substring(len / 3 * 2);
            if (part1.equals(part2) && part2.equals(part3)) {
                level--;
            }
        }

        if (StringUtils.isNumeric(passwd) && len >= 6) { // 19881010 or 881010
            int year = 0;
            if (len == 8 || len == 6) {
                year = Integer.parseInt(passwd.substring(0, len - 4));
            }
            int size = sizeOfInt(year);
            int month = Integer.parseInt(passwd.substring(size, size + 2));
            int day = Integer.parseInt(passwd.substring(size + 2, len));
            if (year >= 1950 && year < 2050 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                level--;
            }
        }

        if (null != DICTIONARY && DICTIONARY.length > 0) {// dictionary
            for (int i = 0; i < DICTIONARY.length; i++) {
                if (passwd.equals(DICTIONARY[i]) || DICTIONARY[i].indexOf(passwd) >= 0) {
                    level--;
                    break;
                }
            }
        }

        if (len <= 6) {
            level--;
            if (len <= 4) {
                level--;
                if (len <= 3) {
                    level = 0;
                }
            }
        }

        if (StringUtils.isBlank(passwd)) {
            level = 0;
        }

        if (level < 0) {
            level = 0;
        }

        return level;
    }

    /**
     * Get password strength level, includes easy, midium, strong, very strong, extremely strong
     *
     * @param passwd
     * @return
     */
    public static LEVEL getPasswordLevel(String passwd) {
        int level = checkPasswordStrength(passwd);
        switch (level) {
            case 0:
            case 1:
            case 2:
            case 3:
                return LEVEL.EASY;
            case 4:
            case 5:
            case 6:
                return LEVEL.MIDIUM;
            case 7:
            case 8:
            case 9:
                return LEVEL.STRONG;
            case 10:
            case 11:
            case 12:
                return LEVEL.VERY_STRONG;
            default:
                return LEVEL.EXTREMELY_STRONG;
        }
    }

    /**
     * calculate the size of an integer number
     *
     * @param x
     * @return
     */
    public static int sizeOfInt(int x) {
        for (int i = 0; ; i++)
            if (x <= SIZE_TABLE[i]) {
                return i + 1;
            }
    }

}