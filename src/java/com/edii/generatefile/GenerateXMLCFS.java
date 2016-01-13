/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.generatefile;

import DatabaseGenerator.DatabaseOracle;
import XMLGenerator.GeneratorXML;
import com.edii.db.Db;
import com.edii.operation.db.operation;
import File.TXT.CreateFile;
import com.edii.tools.Tanggalan;
import java.io.File;
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
public class GenerateXMLCFS {

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

    public String execute(String outboxDir, String kdAsp) throws Exception {
        CreateFile cf = null;
        Tanggalan tg = null;

        String REF_NUMBER = null;
        String filename = null;
        String result = null;

        ArrayList<String> list = new ArrayList<>();
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        colomn = "KODE_KANTOR,TIPE_DATA,KODE_TPS_ASAL,REF_NUMBER,NO_PLP,"
                + "TGL_SURAT_PLP,KODE_GUDANG_ASAL,KODE_TPS_TUJUAN,KODE_GUDANG_TUJUAN,"
                + "KODE_ALASAN_PLP,YOR_ASAL,YOR_TUJUAN,CALL_SIGN,NAMA_KAPAL,NO_VOYAGE,"
                + "TGL_TIBA,NO_BC11,TGL_BC11,NAMA_PEMOHON";
        query = "SELECT KODE_KANTOR,TIPE_DATA,KODE_TPS_ASAL,REF_NUMBER,NO_PLP,"
                + "TO_CHAR(TGL_PLP,'YYYYMMDD'),KODE_GUDANG_ASAL,KODE_TPS_TUJUAN,KODE_GUDANG_TUJUAN,"
                + "KODE_ALASAN_PLP,YOR_ASAL,YOR_TUJUAN,CALL_SIGN,NAMA_KAPAL,NO_VOYAGE,"
                + "TO_CHAR(TGL_TIBA,'YYYYMMDD'),NO_BC11,TO_CHAR(TGL_BC11,'YYYYMMDD'),"
                + "NAMA_PEMOHON FROM T_REQ_UBAH_STATUS WHERE STATUS = '300' AND FL_SEND = '0' AND "
                + "KODE_TPS_ASAL = '" + kdAsp + "'";
        try {
            cf = new CreateFile();
            tg = new Tanggalan();
            gx = new GeneratorXML("DOCUMENT", "loadplp.xsd");
            gx.addXML("DOCUMENT", "LOADPLP", "");
            list = dbO.query_select_raw(colomn, query);
            try {
                if (!list.isEmpty()) {
                    REF_NUMBER = list.get(0).split("#")[3].split(":")[1];
                    gx.addXML("LOADPLP", "HEADER", list.get(0).replace("#", ","));
                    gx.addXML("LOADPLP", "DETIL", "");

                    //GET CONT
                    table = "T_REQ_UBAH_STATUS_CONT";
                    colomn = "NO_CONT,UKURAN_CONT ";
                    String colwhere = "REF_NUMBER";
                    String valwhere = REF_NUMBER;
                    list = dbO.query_select_with_where(table, colomn, colwhere, valwhere, "");
                    for (String isi_list : list) {
                        gx.addXML("DETIL", "CONT", isi_list.replace("#", ","));
                    }

                    //GET KMS
                    colomn = "KODE_KEMASAN,JUMLAH_KEMASAN,NO_BL,TGL_BL_AWB";
                    query = "SELECT KODE_KEMASAN,JUMLAH_KEMASAN,NO_BL,TO_CHAR(TGL_BL,'YYYYMMDD') FROM T_REQ_UBAH_STATUS_KMS WHERE REF_NUMBER = '" + REF_NUMBER + "'";
                    list = dbO.query_select_raw(colomn, query);
                    for (String isi_list : list) {
                        gx.addXML("DETIL", "KMS", isi_list.replace("#", ","));
                    }
                }

                if (gx.getStringxml().length() > 100) {
                    table = "T_REQ_UBAH_STATUS";
                    colomn = "STATUS,DATE_SEND,FL_SEND";
                    value = "400," + getCurrentTimeStamp()+",1";
                    String colwhere = "REF_NUMBER = ,FL_SEND =";
                    String valwhere = REF_NUMBER + ",0";
                    if (dbO.query_update_with_where(table, colomn, value, colwhere, valwhere, "AND")) {
                        filename = REF_NUMBER + "_GET_UBAH_STATUS_" + tg.UNIXNUMBER() + ".xml";
                        if (cf.newFile(outboxDir + File.separator, filename, gx.getStringxml())) {
                            result = gx.getStringxml();
                        }
                    }
//                    query = "UPDATE T_REQ_UBAH_STATUS SET STATUS = ?, DATE_SEND = ? , FL_SEND = ? WHERE REF_NUMBER = ? AND FL_SEND = ?";
//                    preparedStatement = mydb.preparedstmt(query);
//                    preparedStatement.setString(1, "400");
//                    preparedStatement.setTimestamp(2, getCurrentTimeStamp());
//                    preparedStatement.setString(3, "1");
//                    preparedStatement.setString(4, REF_NUMBER);
//                    preparedStatement.setString(5, "0");
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

    public static void main(String[] args) throws Exception {
        GenerateResponPLP_Tujuan_Tes tes = new GenerateResponPLP_Tujuan_Tes();
        String r = tes.excute("D://DATA", "KOJA");
        System.out.println(r);
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

}
