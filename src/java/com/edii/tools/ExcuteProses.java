///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.edii.tools;
//
//import com.edii.db.Db;
//import com.edii.tps.tps;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Properties;
//
///**
// *
// * @author Aslichatun Nisa
// */
//public class ExcuteProses extends tps {
//
//    private static final String PROPERTIES_FILE = "config.properties";
//    private static final Properties PROPERTIES = new Properties();
//
//    static {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
//
//        try {
//            PROPERTIES.load(propertiesFile);
//        } catch (IOException e) {
//        }
//    }
//
//    public String ExcuteCrateFile(String username, String fstream, String localDir, String Nmfile) throws Exception {
//        CreateFile cf = null;
//        String result = "";
//        File cekDir = null;
//        String file = null;
//        try {
//            //Deklarasi Class
//            cf = new CreateFile();
//            //Checking Directory TPS
//            cekDir = new File(localDir);
//            if (!cekDir.exists()) {
//                cekDir.mkdir();
//            }
//            file = localDir + File.separator + Nmfile;
//            if (cf.execute(file)) {
//                cf.content(file, fstream);
//                result = file;
//            }
//        } catch (Exception e) {
//            ExcuteError(e.getMessage(), "ExcuteCrateFile", username);
//            result = "false";
//        }
//        return result;
//    }
//
//    public String ExcuteParsingDok(String jnsDok, String fstream, String sementara) throws Exception {
//        String result = null;
//        if (jnsDok.equalsIgnoreCase("COCOCAR")) {
//            ParsingXMLCocoCarTer car = new ParsingXMLCocoCarTer();
//            result = car.parseDocument(fstream);
//        }
//        if (jnsDok.equalsIgnoreCase("COCOCONT") && !fstream.contains("</ETA>")) {
//            ParsingXmlCoarriCodeco_Con cont = new ParsingXmlCoarriCodeco_Con();
//            result = cont.parseDocument(fstream, "");
//        }
//        if (jnsDok.equalsIgnoreCase("COCOCONT") && !fstream.contains("</ETA>") && sementara.equalsIgnoreCase("ter3")) {
//            ParsingXmlCoarriCodeco_Con cont = new ParsingXmlCoarriCodeco_Con();
//            result = cont.parseDocument(fstream, "ter3");
//        }
//        if (jnsDok.equalsIgnoreCase("COCOKMS")) {
//            ParsingXMLCoarriCodeco_Kms kms = new ParsingXMLCoarriCodeco_Kms();
//            result = kms.parseDocument(fstream);
//        }
//        if (fstream.contains("</ETA>")) {
//            ParsingXMLCoarriCodecoSHL contSHL = new ParsingXMLCoarriCodecoSHL();
//            boolean hasil = contSHL.parseDocument(fstream);
//            if (hasil) {
//                result = "PROSES SUCCESS";
//            }
//        }
//        return result;
//    }
//
//    public boolean ExcuteBilling(String refNumber, String fstream, String username) {
//        PreparedStatement preparedStatement = null;
//        ResultSet rs = null;
//        Db mydb = null;
//        String query = null;
//        try {
//            mydb = new Db();
//            query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF,FILESIZE,EDINUMKIRIM,EDINUMTERIMA) VALUES (SYSDATE,'DOKCOARRI_CODECO_CONT',?,?,?,'EDITPS001')";
//            preparedStatement = mydb.preparedstmt(query);
//            preparedStatement.setString(1, refNumber);
//            preparedStatement.setInt(2, fstream.length());
//            preparedStatement.setString(2, username);
//            preparedStatement.executeUpdate();
//            mydb.execute("commit");
//        } catch (Exception e) {
//            ExcuteError(e.getMessage(), "ExcuteCrateFile", username);
//        }
//        return false;
//    }
//
//    public boolean ExcuteError(String error, String localError, String username) {
//        boolean result = false;
//        String query = null;
//        PreparedStatement preparedStatement = null;
//        Db mydb = null;
//        try {
//            mydb = new Db();
//            query = "INSERT INTO LOG_ERROR (DATE_INSERT,ERROR,NAMA_CLASS,REF_NUMBER) "
//                    + "VALUES (SYSDATE,?,?,?)";
//            //+ "VALUES (SYSDATE,'" + error + "','" + localError + "','" + username + "')";
//            preparedStatement = mydb.preparedstmt(query);
//            preparedStatement.setString(1, error);
//            preparedStatement.setString(2, localError);
//            preparedStatement.setString(2, username);
//            preparedStatement.executeUpdate();
//            //mydb.execute(query);
//            mydb.execute("commit");
//            result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public void ExcuteLog(String Username, String Password, String NmService, String result, String localFile, String fstream) {
//        Db mydb = null;
//        String query = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet rs = null;
//
//        try {
//            mydb = new Db();
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN,XML_REQUEST_NEW) VALUES "
//                    + "(LOG_SEQ.NEXTVAL,?,?,?,SYSDATE,?,?)";
//            preparedStatement = mydb.preparedstmt(query);
//            preparedStatement.setString(1, Username);
//            preparedStatement.setString(2, Password);
//            preparedStatement.setString(3, NmService);
//            preparedStatement.setString(4, result);
//            preparedStatement.setString(5, "Nama File " + localFile + "; Panjang File " + fstream.length());
//            preparedStatement.executeUpdate();
//            mydb.execute("commit");
//
//        } catch (Exception e) {
//        }
//    }
//}