package com.whoiszxl.common.utils;


import java.util.Random;

/**
 * 随机字符串工具
 *
 * @author scq
 *
 */
public class RandomStringUtil {
        /**
         * 获取指定长度随机字符串
         *
         * @param length
         * @return
         */
        public static String getRandomString(int length) {
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < length; i++) {
                int number = random.nextInt(3);
                long result = 0;
                switch (number) {
                    case 0:
                        result = Math.round(Math.random() * 25 + 65);
                        sb.append(String.valueOf((char) result));
                        break;
                    case 1:
                        result = Math.round(Math.random() * 25 + 97);
                        sb.append(String.valueOf((char) result));
                        break;
                    case 2:
                        sb.append(String.valueOf(new Random().nextInt(10)));
                        break;
                }
            }
            return sb.toString();
        }

        /**
         * 测试验证
         *
         * @param args
         */
        //public static void main(String[] args) {
        //    System.out.println(RandomStringUtil.getRandomString(5));
        //    String  str2 = RandomStringUtils.random(12,"123456789");
        //    System.out.println(str2);
        //}


}
