package com.example.lvx.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dhh on 2017/3/22.
 */

public class MatcherUtil {
    /**
     * 验证手机号码
     * @param phone
     * @return
     */
    public static boolean matcherPhone(String phone){
        Pattern pattern = Pattern.compile("[1][3456789]\\d{9}");
        Matcher matcher = pattern.matcher(phone);
        if(matcher.matches()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 校验汉字(1-9个汉字)  {1,9} 自定义区间
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,6}$";
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
}
