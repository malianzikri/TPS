package com.edii.tools;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.io.File;

public class ReadFile {

    private ExcuteProses exc = new ExcuteProses();

    public String execute(String filename) throws Exception {
        String stringFile = "";
        String dumpString = null;
        FileReader fReader = null;
        BufferedReader bReader = null;
        try {
            fReader = new FileReader(filename);
            bReader = new BufferedReader(fReader);
            while ((dumpString = bReader.readLine()) != null) {
                stringFile = stringFile + dumpString;
            }
            bReader.close();
            fReader.close();
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_ReadFile", "Excute");

            e.printStackTrace();
        } finally {
            dumpString = null;
            fReader = null;
            bReader = null;
            return stringFile;
        }
    }

    public void testbaca(String filename) throws Exception {
        File f = null;
        RandomAccessFile rf = null;
        try {
            f = new File(filename);
            rf = new RandomAccessFile(f, "r");
            char c = rf.readChar();
            int i = (int) c;
            System.out.println(String.valueOf(c));
            System.out.println(i);
            rf.close();
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_ReadFile", "testbaca");

            e.printStackTrace();
        } finally {
            f = null;
            rf = null;
        }
    }
}
