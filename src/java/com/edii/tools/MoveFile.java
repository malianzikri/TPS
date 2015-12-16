package com.edii.tools;

import java.io.File;

import java.io.BufferedReader;
import java.io.FileReader;

public class MoveFile {

    private ExcuteProses exc = new ExcuteProses();

    public boolean moveFile(String source, String destination) {
        boolean retMove = false;

        ReadFile rf = null;
        CreateFile cf = null;
        File file = null;
        try {
            cf = new CreateFile();
            rf = new ReadFile();
            if (cf.execute(destination)) {
                retMove = cf.content(destination, rf.execute(source));
                if (retMove) {
                    file = new File(source);
                    retMove = file.delete();
                }
            }
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "class_MOVEFILE", "movefile");
            System.out.println("error :" + e.getMessage());
        } finally {

            cf = null;
            rf = null;
            return retMove;
        }
    }

    public boolean move(String source, String destination) {
        boolean retMove = false;
        FileReader fReader = null;
        BufferedReader bReader = null;
        String dumpString = null;
        String stringFile = null;
        CreateFile cf = null;
        File file = null;
        int baris = 0;
        try {
            stringFile = "";
            fReader = new FileReader(source);
            bReader = new BufferedReader(fReader);
            while ((dumpString = bReader.readLine()) != null) {
                if (baris > 0) {
                    stringFile += "\n" + dumpString;
                } else {
                    stringFile = dumpString;
                }
                baris += 1;
            }
            bReader.close();
            fReader.close();

            cf = new CreateFile();
            if (cf.execute(destination)) {
                retMove = cf.content(destination, stringFile);
                if (retMove) {
                    file = new File(source);
                    retMove = file.delete();
                }
            }
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "class_MOVEFILE", "move");

            System.out.println("error : " + e.getMessage());
        } finally {
            dumpString = null;
            fReader = null;
            bReader = null;
            cf = null;
            file = null;
            stringFile = null;
            return retMove;
        }
    }

    public boolean copyFile(String source, String destination) {
        boolean retCopy = false;

        ReadFile rf = null;
        CreateFile cf = null;
        File file = null;
        try {
            cf = new CreateFile();
            rf = new ReadFile();
            if (cf.execute(destination)) {
                retCopy = cf.content(destination, rf.execute(source));
            }
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "class_MOVEFILE", "copyFile");

            System.out.println("error : " + e.getMessage());
        } finally {
            cf = null;
            rf = null;
            return retCopy;
        }
    }
}
