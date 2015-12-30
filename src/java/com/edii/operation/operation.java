/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.operation;

import DatabaseGenerator.DatabaseOracle;
import com.edii.controller.SaveData;
import com.edii.db.Db;
import com.edii.model.ModelGetResponPLP;
import com.edii.model.ModelGetResponPLPTujuan;
import com.edii.model.ModelUploadBatalPLP;
import com.edii.model.ModelUploadMohonPLP;
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
public class operation implements SaveData {

    static org.apache.commons.logging.Log logger = LogFactory.getLog(Db.class);
    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROPERTIES = new Properties();
    DatabaseOracle dbO = new DatabaseOracle();
    private ArrayList<String> data;
    String value = "";
    String column = "";
    String tabel = "";

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
        dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
    }

    @Override
    public String savedata_uploadmohonplp(ModelUploadMohonPLP plp, String type) {

        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (type.equalsIgnoreCase("header")) {
            tabel = "T_REQUEST_PLP";
            column = "REF_NUMBER,NO_PLP,TGL_PLP,KODE_TPS_ASAL,KODE_GUDANG_ASAL,KODE_TPS_TUJUAN,"
                    + "KODE_GUDANG_TUJUAN,NAMA_KAPAL,NO_VOYAGE,TGL_TIBA,CALL_SIGN,KODE_KANTOR,"
                    + "KODE_ALASAN_PLP,NO_BC11,TGL_BC11,TIPE_DATA,YOR_ASAL,YOR_TUJUAN,NAMA_PEMOHON,"
                    + "STATUS,RECEIVED_DATE";
            value = plp.getRef_number() + "," + plp.getNo_surat() + "," + plp.getTgl_surat() + "," + plp.getKd_tps_asal()
                    + "'" + plp.getGudang_asal() + "," + plp.getKd_tps_tujuan() + "," + plp.getGudang_tujuan() + "," + plp.getNm_angkut()
                    + "," + plp.getNo_voy_flight() + "," + plp.getTgl_tiba() + "," + plp.getCall_sign() + "," + plp.getKd_kantor()
                    + "," + plp.getKd_alasan_plp() + "," + plp.getNo_bc11() + "," + plp.getTgl_bc11() + "," + plp.getTipe_data()
                    + "," + plp.getYor_asal() + "," + plp.getYor_tujuan() + "," + plp.getNm_pemohon() + ",500,SYSDATE";
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("kms")) {
            tabel = "T_REQUEST_PLP_KMS";
            column = "REF_NUMBER,KODE_KEMASAN,JUMLAH_KEMASAN,NO_BL,TGL_BL";
            value = plp.getRef_number() + "," + plp.getJns_kms() + "," + plp.getJml_kms() + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb();
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("cont")) {
            tabel = "T_REQUEST_PLP_CONT";
            column = "REF_NUMBER,NO_CONT,UKURAN_CONT";
            value = plp.getRef_number() + "," + plp.getNo_cont() + "," + plp.getUk_cont();
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        }
        return plp.getKd_kantor();
    }

    @Override
    public String savedata_uploadbatalplp(ModelUploadBatalPLP plp, String type) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (type.equalsIgnoreCase("header")) {
            tabel = "T_REQUEST_PLP";
            column = "REF_NUMBER,NO_PLP,TGL_PLP,KODE_TPS_ASAL,KODE_KANTOR,NO_BC11,TGL_BC11,TIPE_DATA,NAMA_PEMOHON,"
                    + "NO_BATAL_PLP,TGL_BATAL_PLP,ALASAN_BATAL,STATUS_BATAL,RECEIVED_DATE";

            value = plp.getRef_number() + "," + plp.getNo_surat() + "," + plp.getTgl_surat() + "," + plp.getKd_tps_asal()
                    + "," + plp.getKd_kantor() + "," + plp.getNo_bc11() + "," + plp.getTgl_bc11() + "," + plp.getTipe_data()
                    + "," + plp.getNm_pemohon() + "," + plp.getNo_batal_plp() + "," + plp.getTgl_batal_plp()
                    + "," + plp.getAlasan() + ",500,SYSDATE";
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("kms")) {
            tabel = "T_REQUEST_PLP_KMS";
            column = "REF_NUMBER,KODE_KEMASAN,JUMLAH_KEMASAN,NO_BL,TGL_BL";
            value = plp.getRef_number() + "," + plp.getJns_kms() + "," + plp.getJml_kms() + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb();
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("cont")) {
            tabel = "T_REQUEST_PLP_CONT";
            column = "REF_NUMBER,NO_CONT,UKURAN_CONT";
            value = plp.getRef_number() + "," + plp.getNo_cont() + "," + plp.getUk_cont();
            dbO.query_insert(tabel, column, value);
            dbO.close_connection();
        }
        return plp.getKd_kantor();
    }

    @Override
    public String savedata_getresponplp_header(ModelGetResponPLP plp) {

        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = new ArrayList<>();
        tabel = "DUAL";
        column = "RESPLP_SEQ.NEXTVAL as ID";
        data = dbO.query_select_raw(tabel, column);
        String respon_id = data.get(0);

        tabel = "T_RESPON_PLP";
        column = "RESPONID,KD_KANTOR,KD_TPS,REF_NUMBER,NO_PLP,TGL_PLP,ALASAN_REJECT,RECEIVED_DATE";
        value = respon_id + "," + plp.getKd_kantor() + "," + plp.getRef_number() + "," + plp.getNo_plp()
                + "," + plp.getTgl_plp() + "," + plp.getAlasan_reject() + ",SYSDATE";
        dbO.query_insert(tabel, column, value);
        dbO.close_connection();

        return respon_id;
    }

    @Override
    public String savedata_getresponplp_kms(ModelGetResponPLP plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_RESPON_PLP_KMS";
        column = "RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL,STATUS_PLP";
        value = plp.getRef_number() + "," + plp.getJns_kms() + "," + plp.getJml_kms()
                + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb() + "," + plp.getFl_setuju_kms();
        dbO.query_insert(tabel, column, value);
        dbO.close_connection();
        return plp.getKd_kantor();
    }

    @Override
    public String savedata_getresponplp_cont(ModelGetResponPLP plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_RESPON_PLP_CONT";
        column = "RESPONID,NO_CONT,UK_CONT,JNS_CONT,STATUS_PLP";
        value = plp.getRef_number() + "," + plp.getNo_cont() + "," + plp.getUk_cont()
                + "," + plp.getJns_cont() + "," + plp.getFl_setuju_cont();
        dbO.query_insert(tabel, column, value);
        dbO.close_connection();
        return plp.getKd_kantor();
    }

    @Override
    public String savedata_getresponplptujuan_header(ModelGetResponPLPTujuan plp) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = new ArrayList<>();
        tabel = "DUAL";
        column = "RESPLP_SEQ.NEXTVAL as ID";
        data = dbO.query_select_raw(tabel, column);
        String respon_id = data.get(0);

        tabel = "T_RESPON_PLP";
        column = "RESPONID,KD_KANTOR,KD_TPS,REF_NUMBER,NO_PLP,TGL_PLP,ALASAN_REJECT,RECEIVED_DATE";
        value = respon_id + "," + plp.getKd_kantor() + "," + plp.getRef_number() + "," + plp.getNo_plp()
                + "," + plp.getTgl_plp() + "," + plp.getAlasan_reject() + ",SYSDATE";
        dbO.query_insert(tabel, column, value);
        dbO.close_connection();

        return respon_id;
    }

    @Override
    public String savedata_getresponplptujuan_kms(ModelGetResponPLPTujuan plp, String respon_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String savedata_getresponplptujuan_cont(ModelGetResponPLPTujuan plp, String respon_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
