// 
// 
// 

package com.util;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.UUID;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Util
{
    public static String getYuefen() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        final Date date = new Date();
        return sdf.format(date.getTime());
    }
    
    public static String getTime() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date = new Date();
        return sdf.format(date.getTime());
    }
    
    public static String getRiqi() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date();
        return sdf.format(date.getTime());
    }
    
    public static Date parseTime(final String s) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(s);
    }
    
    public static String getTime2() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        final Date date = new Date();
        return sdf.format(date.getTime());
    }
    
    public static String uploadFile(final HttpServletRequest request, final MultipartFile prodFile) {
        final HttpSession session = request.getSession();
        final String basePath = session.getServletContext().getRealPath("/");
        final File target = new File("/uploads");
        final File destFolder = new File(target, getRiqi());
        final String fileOriginalName = prodFile.getOriginalFilename();
        final String suffix = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        final String uploadFileName = String.valueOf(UUID.randomUUID().toString()) + suffix;
        final File destFile = new File(destFolder, uploadFileName);
        final File fileUploadPath = new File(String.valueOf(basePath) + destFile.getPath());
        if (!fileUploadPath.getParentFile().exists()) {
            fileUploadPath.getParentFile().mkdirs();
        }
        DigestInputStream dis = null;
        FileOutputStream fos = null;
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(prodFile.getInputStream(), md);
            fos = new FileOutputStream(fileUploadPath);
            final byte[] bys = new byte[1024];
            int len = 0;
            while ((len = dis.read(bys)) > 0) {
                fos.write(bys, 0, len);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                return destFile.getPath();
            }
            return destFile.getPath();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (fos != null) {
            try {
                fos.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (dis != null) {
            try {
                dis.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        final String filePath = destFile.getPath();
        return filePath;
    }
}
