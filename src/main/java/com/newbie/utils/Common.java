package com.newbie.utils;

import com.easyond.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static String stampToDate(String s) {
        if (!StringUtil.invalid(s)) {
            String res;
            s = s.length() < 13 ? s + "0000000000000".substring(0, 13 - s.length()) : s;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } else {
            return "";
        }
    }

    public static String dateToStamp(String s) {
        if (!StringUtil.invalid(s)) {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = simpleDateFormat.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long ts = date.getTime();
            res = String.valueOf(ts);
            return res;
        } else {
            return "";
        }

    }


    public static String doCooperationContent(String companyName, String email, String context) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!Doctype html>");
        sb.append("<html lang=\"zh-CN\">");
        sb.append("<head>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sb.append("<style>");
        sb.append(".qe_email_info{");
        sb.append("width: 500px;");
        sb.append("border: 1px solid #cdcdcd;");
        sb.append("margin: 35px auto;");
        sb.append("font-family: 'microsoft yahei';");
        sb.append("font-size: 14px;");
        sb.append("}");
        sb.append(".qe_email_info .qe_email_title{");
        sb.append("background: #3eb97f;");
        sb.append("color: #fff;");
        sb.append("height: 50px;");
        sb.append("line-height: 50px;");
        sb.append("padding-left: 20px;");
        sb.append("}");
        sb.append(".qe_email_info h3{");
        sb.append("font-size: 16px;");
        sb.append("padding-left: 20px;");
        sb.append("padding-top: 30px;");
        sb.append("display: block;");
        sb.append("}");
        sb.append(".qe_email_info .qe_email_content{");
        sb.append("padding: 20px 20px 40px 40px;");
        sb.append("word-wrap: break-word;");
        sb.append("line-height: 24px;");
        sb.append("}");
        sb.append(".qe_email_content p{");
        sb.append("margin: 14px 0;");
        sb.append("}");
        sb.append(".qe_email_content p.tips{");
        sb.append("margin-top: 40px;");
        sb.append("font-size: 12px;");
        sb.append("color: #555;");
        sb.append("}");
        sb.append(".qe_email_content a{");
        sb.append("color: #3eb97f;");
        sb.append("}");
        sb.append(".qe_email_content .tr{");
        sb.append("text-align: right;");
        sb.append("font-size: 12px;");
        sb.append("}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<div class=\"qe_email_info\">");
        sb.append("<div class=\"qe_email_title\">");
        sb.append("泰通大有");
        sb.append("</div>");
        sb.append("<h3>您好！</h3>");
        sb.append("<div class=\"qe_email_content\">");
        sb.append("<p>提醒您，客户：" + companyName + "，联系方式为" + email + "</p>");
        sb.append("<p>留言：</p>");
        sb.append("<p>" + context + "</p>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

}
