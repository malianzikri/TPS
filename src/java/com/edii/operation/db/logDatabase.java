/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.operation.db;

import DatabaseGenerator.DatabaseOracle;
import File.TXT.CreateFile;
import Loggers.Loggers;
import com.edii.tools.Encrypt;
import com.edii.tools.Tanggalan;
import com.edii.tps.service.TPSUpload;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Aslichatun Nisa
 */
public class logDatabase {

    private static final Logger logger = LoggerFactory.getLogger(TPSUpload.class);
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();
    DatabaseOracle dbO = new DatabaseOracle();
    private static String succes = "";
    private static String error = "";
    private static Tanggalan tgl = new Tanggalan();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
        if (propertiesFile == null) {
            logger.debug("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
            succes += new Loggers().LogSuccess("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.") + "\r\n";
        }

        try {
            PROPERTIES.load(propertiesFile);
            succes += new Loggers().LogSuccess("Properties load '" + propertiesFile) + "\r\n";
        } catch (IOException e) {
            logger.debug("Cannot load properties file '" + PROPERTIES_FILE + "'." + e.getMessage());
            error = new Loggers().LogSuccess("Cannot load properties file '" + PROPERTIES_FILE + "'." + e.getMessage()) + "\r\n";
        } finally {
            String filename = "Load propertiesFile di Class TPSUpload" + tgl.UNIXNUMBER() + ".txt";
            if (!error.equalsIgnoreCase("")) {
                new CreateFile().newFile("isi dengan direktory", filename, error);
            } else {
                new CreateFile().newFile("isi dengan direktory", filename, succes);
            }
        }
    }

    private static String getCurrentTimeStamp() {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }

    public boolean excuteLogError(String error, String localError, String username) {
        boolean result = false;
        try {
            dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
            String tabel = "LOG_ERROR";
            String column = "DATE_INSERT,ERROR,NAMA_CLASS,REF_NUMBER";
            String value = getCurrentTimeStamp() + "," + error + "," + localError + "," + username + "";
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean insert_mblling(String aprf, String snrf, String edinumkirim, String data) {
        try {
            dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
            String tabel = "MIBILLING";
            String column = "EDIFACTTIME,APRF,SNRF,FILESIZE,EDINUMKIRIM,EDINUMTERIMA";
            String value = getCurrentTimeStamp() + "," + aprf + "," + snrf + "," + data.length() + "," + edinumkirim + ",EDITPS001";
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        } catch (Exception e) {
            excuteLogError(e.getMessage(), "ExcuteCrateFile", aprf);
        }
        return false;
    }

    public void excuteLogSucces(String Username, String Password, String NmService, String result, String localFile, String fstream) {
        try {
            dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
            String tabel = "T_LOG_SERVICES";
            String column = "LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN,XML_REQUEST_NEW";
            String value = Username + "," + Password + "," + NmService + "," + getCurrentTimeStamp() + "," + result + ",Nama File " + localFile + "; Panjang File " + fstream.length();
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        } catch (Exception e) {
            excuteLogError(e.getMessage(), "excuteLog", Username);
        }
    }

    //insert tpslog
    public String excuteLogTpsLog(String error_code, String file, String resultdb) {
        String result = "";
        try {
            dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
            String tabel = "TPSLOG";
            String column = "ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE";
            String value = error_code + ",Parsing Dok PLP,Insert DB,Nama File " + file + "; Error " + resultdb + "," + getCurrentTimeStamp();
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
            result = "Gagal Insert ke Database IPC ";
        } catch (Exception e) {
            excuteLogError(e.getMessage(), "excuteLog", "");
        }

        return result;
    }

    //update mibiling
    public void update_mblling(String Username, String file, String fStream, String refNumber) {
        try {
            dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
            String tabel = "MIBILLING";
            String column = "EDINUMKIRIM,EDINUMTERIMA,FILENAME,FILESIZE";
            String value = Username + ",EDITPS001," + file + "," + fStream.length();
            String column_where = "SNRF";
            String value_where = refNumber;
            dbO.query_update_with_where(tabel, column, column, column, value, column);
            dbO.close_connection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
