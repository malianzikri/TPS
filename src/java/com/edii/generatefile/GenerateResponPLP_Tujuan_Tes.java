/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.generatefile;

import com.edii.db.Db;
import com.edii.tools.Tanggalan;
import DatabaseGenerator.DatabaseOracle;
import XMLGenerator.GeneratorXML;
import com.edii.operation.db.operation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.LogFactory;
import File.TXT.CreateFile;
import java.io.File;

/**
 *
 * @author Aslichatun Nisa
 */
public class GenerateResponPLP_Tujuan_Tes {

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

    public String excute(String outboxDir, String kdAsp) throws Exception {
        CreateFile cf = null;
        Tanggalan tg = null;

        //DOK PLP Element Header
        String RESPONID = null;
        String result = null;

        ArrayList<String> list = new ArrayList<>();
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        //GET HEADER
        colomn = "RESPONID,KD_KANTOR,KD_TPS,KD_TPS_ASAL,NO_PLP,TGL_PLP,"
                + "NAMA_KAPAL,NO_VOYAGE,CALL_SIGN, TGL_TIBA , GUDANG_TUJUAN,NO_BC11, "
                + "TGL_BC11, NO_SURAT,TGL_SURAT ";
        query = "SELECT RESPONID,KD_KANTOR,KD_TPS,KD_TPS_ASAL,NO_PLP,TO_CHAR(TGL_PLP,'YYYYMMDD') AS TGL_PLP,"
                + "NAMA_KAPAL,NO_VOYAGE,CALL_SIGN,TO_CHAR(TGL_TIBA,'YYYYMMDD') AS TGL_TIBA , GUDANG_TUJUAN,NO_BC11, "
                + "TO_CHAR(TGL_BC11,'YYYYMMDD') AS TGL_BC11, NO_SURAT, TO_CHAR(TGL_SURAT,'YYYYMMDD') AS TGL_SURAT "
                + "FROM T_PLP_TES WHERE FL_SEND = '0' AND KD_TPS = '" + kdAsp + "'";
        try {
            cf = new CreateFile();
            tg = new Tanggalan();
            gx = new GeneratorXML("DOCUMENT", "");
            gx.addXML("DOCUMENT", "RESPONPLP", "");
            list = dbO.query_select_raw(colomn, query);
            try {
                if (!list.isEmpty()) {
                    RESPONID = list.get(0).split("#")[0].split(":")[1];
                    gx.addXML("RESPONPLP", "HEADER", list.get(0).replace("#", ","));
                    gx.addXML("RESPONPLP", "DETIL", "");

                    //GET CONT
                    colomn = "NO_CONT,UK_CONT,JNS_CONT,NO_POS_BC11,CONSIGNEE, NO_BL_AWB, TGL_BL_AWB";
                    query = "SELECT NO_CONT,UK_CONT,JNS_CONT,NO_POS_BC11,CONSIGNEE, NO_BL_AWB, TO_CHAR(TGL_BL_AWB,'YYYYMMDD') AS TGL_BL_AWB FROM T_PLP_CONT_TES WHERE RESPONID = '" + RESPONID + "'";
                    list = dbO.query_select_raw(colomn, query);
                    for (String isi_list : list) {
                        gx.addXML("DETIL", "CONT", isi_list.replace("#", ","));
                    }

                    //GET KMS
                    colomn = "JNS_KMS,JML_KMS,NO_BL,TGL_BL_AWB";
                    query = "SELECT JNS_KMS,JML_KMS,NO_BL,TO_CHAR(TGL_BL,'YYYYMMDD') FROM T_PLP_KMS WHERE RESPONID = '" + RESPONID + "'";
                    list = dbO.query_select_raw(colomn, query);

                    for (String isi_list : list) {
                        gx.addXML("DETIL", "KMS", isi_list.replace("#", ","));
                    }
                }

                if (!gx.getStringxml().isEmpty()) {
                    table = "T_PLP_TES";
                    colomn = "FL_SEND,DATE_SEND";
                    value = "1,"+getCurrentTimeStamp();
                    String colwhere = "RESPONID = ,FL_SEND =";
                    String valwhere = RESPONID+",0";
                    if (dbO.query_update_with_where(table, colomn, value, colwhere, valwhere, "AND")) {
                        String filename = RESPONID + "_T_PLP_TES_" + tg.UNIXNUMBER() + ".xml";
                        if (cf.newFile(outboxDir + File.separator, filename, gx.getStringxml())) {
                            result = gx.getStringxml();
                        }
                    }
//                    query = "UPDATE T_PLP_TES SET FL_SEND = ? , DATE_SEND = ?  WHERE RESPONID = ? AND FL_SEND = ?";
//                    preparedStatement = mydb.preparedstmt(query);
//                    preparedStatement.setString(1, "1");
//                    preparedStatement.setDate(2, getCurrentTimeStamp());
//                    preparedStatement.setString(3, RESPONID);
//                    preparedStatement.setString(4, "0");
//                    mydb.execute("commit");
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

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }

    public static void main(String[] args) throws Exception {
        GenerateResponPLP_Tujuan_Tes tes = new GenerateResponPLP_Tujuan_Tes();
        String r = tes.excute("D://DATA", "KOJA");
        System.out.println(r);
    }
}
