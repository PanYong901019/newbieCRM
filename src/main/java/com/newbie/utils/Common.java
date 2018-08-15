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
}
