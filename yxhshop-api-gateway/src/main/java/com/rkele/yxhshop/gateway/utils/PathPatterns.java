package com.rkele.yxhshop.gateway.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by root on 2020/4/29.
 */
@Component
public class PathPatterns {

    /**
     * 将通配符表达式转化为正则表达式
     *
     * @param path
     * @return
     */
    private String getRegPath(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for (int i = 0; i < len; i++) {
            if (chars[i] == '*') {//遇到*字符
                if (preX) {//如果是第二次遇到*，则将**替换成.*
                    sb.append(".*");
                    preX = false;
                } else if (i + 1 == len) {//如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
                    sb.append("[^/]*");
                } else {//否则单星后面还有字符，则不做任何动作，下一把再做动作
                    preX = true;
                    continue;
                }
            } else {//遇到非*字符
                if (preX) {//如果上一把是*，则先把上一把的*对应的[^/]*添进来
                    sb.append("[^/]*");
                    preX = false;
                }
                if (chars[i] == '?') {//接着判断当前字符是不是?，是的话替换成.
                    sb.append('.');
                } else {//不是?的话，则就是普通字符，直接添进来
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 通配符模式
     *
     * @param excludePath - 不过滤地址
     * @param reqUrl      - 请求地址
     * @return
     */
    private boolean filterUrls(String excludePath, String reqUrl) {
        String regPath = getRegPath(excludePath);
        return Pattern.compile(regPath).matcher(reqUrl).matches();
    }

    /**
     * 检验是否在非过滤地址
     *
     * @param excludeUrls
     * @param reqUrl
     * @return
     */
    public boolean checkWhiteList(String[] excludeUrls, String reqUrl) {
        for (String url : excludeUrls) {
            if (filterUrls(url, reqUrl)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        PathPatterns pathPatterns = new PathPatterns();
        String[] excludeUrl = new String[]{"/abf/*","/abd/**","*.login","*.html","*.jsp","/abg/login"};
        long start  = 0;
        long end = 0;
        for(int i=0;i<10000000;i++){
            if(i==0){
                System.out.println(System.currentTimeMillis());
                start = System.currentTimeMillis();
            }
            if(i==9999999){
                System.out.println(System.currentTimeMillis());
                end = System.currentTimeMillis();
                System.out.println(pathPatterns.checkWhiteList(excludeUrl, "/abf/test"));
            }
            pathPatterns.checkWhiteList(excludeUrl, "/abd/test/login");
            //System.out.println(pathPatterns.checkWhiteList(excludeUrl, "/abf/test/login"));
            //System.out.println(pathPatterns.checkWhiteList(excludeUrl, "/abd/test/login"));
        }
        System.out.println(end-start);

    }

}
