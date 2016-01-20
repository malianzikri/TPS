/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.generatefile;

import DatabaseGenerator.DatabaseOracle;
import XMLGenerator.GeneratorXML;
import com.edii.db.Db;
import com.edii.operation.db.operation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Aslichatun Nisa
 */
public class GenerateDW {

    String table = "";
    String colomn = "";
    String value = "";
    String query = "";
    GeneratorXML gx = null;

    static org.apache.commons.logging.Log logger = LogFactory.getLog(Db.class);
    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROPERTIES = new Properties();
    DatabaseOracle dbO = null;

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            logger.debug("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            logger.debug("Cannot load properties file '" + PROPERTIES_FILE + "'." + e.getMessage());
        }
    }

    private void OpenConnection() throws ClassNotFoundException {
        dbO = new DatabaseOracle();
        dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
    }

    public String excute(String RefNumber, String responId) throws Exception {
        String RESPONID = null;
        String result = null;

        ArrayList<String> list = new ArrayList<>();
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        colomn = "KODE_TPS_ASAL , KODE_GUDANG_ASAL , CALL_SIGN, NAMA_KAPAL, "
                + "NO_VOYAGE, KODE_TPS_TUJUAN, KODE_GUDANG_TUJUAN, REF_NUMBER, RESPONID";
        query = "SELECT A.KODE_TPS_ASAL , A.KODE_GUDANG_ASAL , A.CALL_SIGN, A.NAMA_KAPAL, "
                + "A.NO_VOYAGE, A.KODE_TPS_TUJUAN, A.KODE_GUDANG_TUJUAN, B.REF_NUMBER, B.RESPONID   "
                + "FROM T_REQUEST_PLP A "
                + "INNER JOIN T_RESPON_PLP B ON A.REF_NUMBER = B.REF_NUMBER "
                + "WHERE A.KODE_TPS_ASAL <> 'PLDC' AND B.FL_SEND = '0'"
                + "AND B.REF_NUMBER = '" + RefNumber + "' AND B.RESPONID = '" + responId + "'";
        //System.out.println(list.toString());
        try {
            gx = new GeneratorXML("DOCUMENT", "");
            gx.addXML("DOCUMENT", "RESPLP", "");
            list = dbO.query_select_raw(colomn, query);
            try {
                if (!list.isEmpty()) {
                    RESPONID = list.get(0).split("#")[8].split(":")[1];
                    gx.addXML("RESPLP", "HEADER", list.get(0).replace("#", ","));
                    gx.addXML("RESPLP", "DETIL", "");

                    //CONT
                    colomn = "NO_CONT,UK_CONT,STATUS_PLP";
                    query = "SELECT NO_CONT,UK_CONT,STATUS_PLP FROM T_RESPON_PLP_CONT WHERE RESPONID = '" + RESPONID + "' AND STATUS_PLP = 'Y'";
                    list = dbO.query_select_raw(colomn, query);
                    for (String isi_list : list) {
                        gx.addXML("DETIL", "CONT", isi_list.replace("#", ","));
                    }

                }
                if (gx.getStringxml().length() > 100) {
                    table = "T_RESPON_PLP";
                    colomn = "FL_SEND,DATE_SEND";
                    value = "1," + getCurrentTimeStamp();
                    String colwhere = "RESPONID = ";
                    String valwhere = RESPONID;
                    if (dbO.query_update_with_where(table, colomn, value, colwhere, valwhere, "")) {
//                    query = "UPDATE T_RESPON_PLP SET FL_SEND = ? , DATE_SEND = ? WHERE RESPONID = ?";
//                    preparedStatement = mydb.preparedstmt(query);
//                    preparedStatement.setString(1, "1");
//                    preparedStatement.setTimestamp(2, getCurrentTimeStamp());
//                    preparedStatement.setString(3, RESPONID);
//                    mydb.execute("commit");
                        result = gx.getStringxml();
                    }
                }
            } catch (Exception e) {
                System.out.println("message" + e.getMessage());
            }
            return result;
        } catch (Exception e) {
            System.out.println("message nian" + e.getMessage());
            return result;
        } finally {
            list.clear();
        }
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    public static void main(String[] args) throws Exception {
        GenerateDW gd = new GenerateDW();
        String r = gd.excute("KOJA141110367703", "9673");
        System.out.println(r);
    }
}
