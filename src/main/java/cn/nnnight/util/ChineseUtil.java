package cn.nnnight.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseUtil {

    /**
     * 判断字符串中是否含有中文(不能判断中文标点符号)
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 过滤掉字符串中的中文
     *
     * @param str
     * @return
     */
    public static String filterChinese(String str) {
        String result = str;
        boolean strContainflag = isContainChinese(str);
        if (strContainflag) {
            StringBuffer sb = new StringBuffer();
            boolean charContainFlag = false;
            char chinese = 0;
            char[] charArray = str.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                charContainFlag = isContainChinese(String.valueOf(chinese));
                if (!charContainFlag) {
                    sb.append(chinese);
                }
            }
            result = sb.toString();
        }
        return result;
    }

}
