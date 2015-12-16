package com.edii.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    static int prevNumber = 0;

    public static String readFileAsString(String filePath) throws java.io.IOException {
        byte[] buffer = new byte[(int) new File(filePath).length()];
        BufferedInputStream f = null;
        try {
            f = new BufferedInputStream(new FileInputStream(filePath));
            f.read(buffer);
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException ignored) {
                }
            }
        }
        return new String(buffer);
    }

    public static boolean writeStringtoFile(String filename, String content) throws Exception {
        boolean success = false;
        File f = null;
        RandomAccessFile rf = null;
        try {
            f = new File(filename);
            rf = new RandomAccessFile(f, "rwd");
            rf.writeBytes(content);
            rf.close();
            success = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            success = false;
        } finally {
            f = null;
            rf = null;
            return success;
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;

        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public static String getNow() {
        return new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
    }

    public static boolean isFileExist(String filename) {
        //filename is full with filepath
        File file = new File(filename);
        return file.isFile();
    }

    public static String addXmlHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    }

    public static String getUniqueString() {
        long currentTime = System.currentTimeMillis();
        int threadID = Thread.currentThread().hashCode();

        StringBuffer strBuf = new StringBuffer();

        int num = 0;
        do {
            num = (int) (Math.round(Math.random() * 36.0D) % 36L);
        } while (num == prevNumber);
        prevNumber = num;

        convertToRadix(strBuf, num, 36);
        convertToRadix(strBuf, currentTime, 36);
        convertToRadix(strBuf, threadID, 36);

        strBuf.reverse();

        return strBuf.toString();
    }

    public static void convertToRadix(StringBuffer strBuf, long num, int radix) {
        while (num > 0L) {
            int n = (int) (num % radix);
            char ch = Character.forDigit(n, radix);
            strBuf.append(Character.toUpperCase(ch));
            num /= radix;
        }
    }
    
}
