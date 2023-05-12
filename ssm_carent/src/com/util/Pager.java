// 
// 
// 

package com.util;

public class Pager
{
    public static String getPagerNormal(final int total, final int pagesize, final int pagenum, String pageurl, final String info) {
        int count = total / pagesize;
        if (total % pagesize > 0) {
            ++count;
        }
        if (pageurl.indexOf("?") > -1) {
            pageurl = String.valueOf(pageurl) + "&";
        }
        else {
            pageurl = String.valueOf(pageurl) + "?";
        }
        final StringBuffer buf = new StringBuffer();
        buf.append(String.valueOf(info) + "&nbsp;&nbsp;");
        buf.append(String.valueOf(pagenum) + "/" + count + "&nbsp;&nbsp;");
        if (pagenum == 1) {
            buf.append("<SPAN style='color:#CCCCCC'>\u3010\u9996\u9875\u3011</SPAN><SPAN style='color:#CCCCCC'>\u3010\u4e0a\u4e00\u9875\u3011</SPAN>&nbsp;&nbsp;");
        }
        else {
            buf.append("\u3010<a href='" + pageurl + "pagenum=1'>\u9996\u9875</a>\u3011\u3010<a href='" + pageurl + "pagenum=" + (pagenum - 1) + "' >\u4e0a\u4e00\u9875</a>\u3011");
        }
        final int bound1 = (pagenum - 2 <= 0) ? 1 : (pagenum - 2);
        final int bound2 = (pagenum + 2 >= count) ? count : (pagenum + 2);
        for (int i = bound1; i <= bound2; ++i) {
            if (i == pagenum) {
                buf.append("<SPAN style='color:#FF0000'>" + i + "</SPAN>&nbsp;&nbsp;");
            }
            else {
                buf.append("<a href='" + pageurl + "pagenum=" + i + "'>" + i + "</a>&nbsp;&nbsp;");
            }
        }
        if (bound2 < count) {
            buf.append("<SPAN>...</SPAN>");
        }
        if (pagenum == count || count == 0) {
            buf.append("<SPAN style='color:#CCCCCC'>\u3010\u4e0b\u4e00\u9875\u3011</SPAN><SPAN style='color:#CCCCCC'>\u3010\u5c3e\u9875\u3011</SPAN>");
        }
        else {
            buf.append("\u3010<a href='" + pageurl + "pagenum=" + (pagenum + 1) + "'>\u4e0b\u4e00\u9875</a>\u3011\u3010<a href='" + pageurl + "pagenum=" + count + "'>\u5c3e\u9875</a>\u3011");
        }
        return buf.toString();
    }
}
