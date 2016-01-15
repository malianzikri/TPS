/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.operation.db;

import DatabaseGenerator.DatabaseOracle;
import com.edii.db.Db;
import com.edii.model.ModelGetResponBatalPLP;
import com.edii.model.ModelGetResponBatalPLPTujuan;
import com.edii.model.ModelGetResponPLP;
import com.edii.model.ModelGetResponPLPTujuan;
import com.edii.model.ModelUploadBatalPLP;
import com.edii.model.ModelUploadMohonPLP;
import com.edii.controller.SaveData;
import com.edii.model.ModelCoCoCarTer;
import com.edii.model.ModelCoarriCodecoContainer;
import com.edii.model.ModelCoarriCodecoKemasan;
import com.edii.model.ModelCoarriCodecoSHL;
import com.edii.model.ModelDW;
import com.edii.model.ModelGetSPJM;
import com.edii.model.ModelSPPB;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.LogFactory;
import File.TXT.CreateFile;
import Loggers.Loggers;

/**
 *
 * @author Aslichatun Nisa
 */
public class operation implements SaveData {

    static org.apache.commons.logging.Log logger = LogFactory.getLog(Db.class);
    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROPERTIES = new Properties();
    DatabaseOracle dbO = new DatabaseOracle();
    CreateFile cf = new CreateFile();
    Loggers log = new Loggers();
    private ArrayList<String> data;
    String value = "";
    String column = "";
    String tabel = "";
    String colomn_where = "";
    String value_where = "";
    String messagesc = "";
    String messageer = "";

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
                    + "," + plp.getYor_asal() + "," + plp.getYor_tujuan() + "," + plp.getNm_pemohon() + ",500," + getCurrentTimeStamp();

            if (!dbO.query_insert(tabel, column, value)) {
                messageer += log.LogError(dbO.get_message_error()) + "\r\n";
                cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
                messageer = "";
            }
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("kms")) {
            tabel = "T_REQUEST_PLP_KMS";
            column = "REF_NUMBER,KODE_KEMASAN,JUMLAH_KEMASAN,NO_BL,TGL_BL";
            value = plp.getRef_number() + "," + plp.getJns_kms() + "," + plp.getJml_kms() + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb();
            if (!dbO.query_insert(tabel, column, value)) {
                messageer += log.LogError(dbO.get_message_error()) + "\r\n";
                cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
                messageer = "";
            }
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("cont")) {
            tabel = "T_REQUEST_PLP_CONT";
            column = "REF_NUMBER,NO_CONT,UKURAN_CONT";
            value = plp.getRef_number() + "," + plp.getNo_cont() + "," + plp.getUk_cont();
            if (!dbO.query_insert(tabel, column, value)) {
                messageer += log.LogError(dbO.get_message_error()) + "\r\n";
                cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
                messageer = "";
            }
            dbO.close_connection();
        }

        return "";
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
                    + "," + plp.getAlasan() + ",500," + getCurrentTimeStamp();
            if (!dbO.query_insert(tabel, column, value)) {
                messageer += log.LogError(dbO.get_message_error()) + "\r\n";
                cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
                messageer = "";
            }
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("kms")) {
            tabel = "T_REQUEST_PLP_KMS";
            column = "REF_NUMBER,KODE_KEMASAN,JUMLAH_KEMASAN,NO_BL,TGL_BL";
            value = plp.getRef_number() + "," + plp.getJns_kms() + "," + plp.getJml_kms() + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb();
            if (!dbO.query_insert(tabel, column, value)) {
                messageer += log.LogError(dbO.get_message_error()) + "\r\n";
                cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
                messageer = "";
            }
            dbO.close_connection();
        } else if (type.equalsIgnoreCase("cont")) {
            tabel = "T_REQUEST_PLP_CONT";
            column = "REF_NUMBER,NO_CONT,UKURAN_CONT";
            value = plp.getRef_number() + "," + plp.getNo_cont() + "," + plp.getUk_cont();
            if (!dbO.query_insert(tabel, column, value)) {
                messageer += log.LogError(dbO.get_message_error()) + "\r\n";
                cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
                messageer = "";
            }
            dbO.close_connection();
        }

        return "";
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
                + "," + plp.getTgl_plp() + "," + plp.getAlasan_reject() + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        //savelog
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
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
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
        value = respon_id + "," + plp.getNo_cont() + "," + plp.getUk_cont()
                + "," + plp.getJns_cont() + "," + plp.getFl_setuju_cont();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
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

        tabel = "T_PLP";
        column = "RESPONID,KD_KANTOR,KD_TPS,KD_TPS_ASAL,GUDANG_TUJUAN,NO_PLP,TGL_PLP,"
                + "NAMA_KAPAL,NO_VOYAGE,CALL_SIGN,TGL_TIBA,NO_BC11,TGL_BC11,"
                + "NO_SURAT,TGL_SURAT,RECEIVED_DATE";
        value = respon_id + "," + plp.getKd_kantor() + "," + plp.getKd_tps_asal() + "," + plp.getGudang_tujuan()
                + "," + plp.getNo_plp() + "," + plp.getTgl_plp() + "," + plp.getNm_angkut() + "," + plp.getNo_voy_flight()
                + "," + plp.getCall_sign() + "," + plp.getTgl_tiba() + "," + plp.getNo_bc11() + "','" + plp.getTgl_bc11()
                + "," + plp.getNo_surat() + "," + plp.getTgl_surat() + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();

        return respon_id;
    }

    @Override
    public String savedata_getresponplptujuan_kms(ModelGetResponPLPTujuan plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_PLP_KMS";
        column = "RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL";
        value = respon_id + "," + plp.getJns_kms() + "," + plp.getJml_kms()
                + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_getresponplptujuan_cont(ModelGetResponPLPTujuan plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_PLP_CONT";
        column = "RESPONID,NO_CONT,UK_CONT,JNS_CONT,NO_POS_BC11,CONSIGNEE,NO_BL_AWB,TGL_BL_AWB";
        value = respon_id + "," + plp.getNo_cont() + "," + plp.getUk_cont()
                + "," + plp.getJns_cont() + "," + plp.getNo_pos_bc11() + "," + plp.getConsignee()
                + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_getresponbatalplp_header(ModelGetResponBatalPLP plp) {
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

        tabel = "T_RESPON_BATAL_PLP";
        column = "RESPONID,KD_KANTOR,KD_TPS,REF_NUMBER,NO_BATAL_PLP,TGL_BATAL_PLP,RECEIVED_DATE";
        value = respon_id + "," + plp.getKd_kantor() + "," + plp.getRef_number() + "," + plp.getNo_batal_plp()
                + "," + plp.getTgl_batal_plp() + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();

        return respon_id;
    }

    @Override
    public String savedata_getresponbatalplp_kms(ModelGetResponBatalPLP plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_RESPON_BATAL_PLP_KMS";
        column = "RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL,STATUS_PLP";
        value = respon_id + "," + plp.getJns_kms() + "," + plp.getJml_kms()
                + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb() + "," + plp.getFl_setuju_kms();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_getresponbatalplp_cont(ModelGetResponBatalPLP plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_RESPON_BATAL_PLP_CONT";
        column = "RESPONID,NO_CONT,UK_CONT,STATUS_PLP";
        value = respon_id + "," + plp.getNo_cont() + "," + plp.getUk_cont() + "," + plp.getFl_setuju_cont();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_getresponbatalplptujuan_header(ModelGetResponBatalPLPTujuan plp) {
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

        tabel = "T_BATAL_PLP";
        column = "RESPONID,KD_KANTOR,KD_TPS,KD_TPS_ASAL,NO_BATAL_PLP,TGL_BATAL_PLP,NO_PLP,TGL_PLP,RECEIVED_DATE";
        value = respon_id + "," + plp.getKd_kantor() + "," + plp.getKd_tps() + "," + plp.getNo_batal_plp()
                + "," + plp.getTgl_batal_plp() + "," + plp.getNo_plp() + "," + plp.getTgl_plp() + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();

        return respon_id;
    }

    @Override
    public String savedata_getresponbatalplptujuan_kms(ModelGetResponBatalPLPTujuan plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_BATAL_PLP_KMS";
        column = "RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL";
        value = respon_id + "," + plp.getJns_kms() + "," + plp.getJml_kms()
                + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_getresponbatalplptujuan_cont(ModelGetResponBatalPLPTujuan plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_BATAL_PLP_CONT";
        column = "RESPONID,NO_CONT,UK_CONT";
        value = respon_id + "," + plp.getNo_cont() + "," + plp.getUk_cont();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_coarricodecocontianer_header(ModelCoarriCodecoContainer coco) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = new ArrayList<>();
        tabel = "DUAL";
        column = "COARRI_CODECO_SEQ.NEXTVAL as ID";
        data = dbO.query_select_raw(tabel, column);
        String id = data.get(0);

        tabel = "COCOHDR";
        column = "ID,KD_DOK,KD_TPS,NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER";
        value = id + "," + coco.getKd_dok() + "," + coco.getKd_tps() + "," + coco.getNm_angkut()
                + "," + coco.getNo_voy_flight() + "," + coco.getCall_sign()
                + "," + coco.getTgl_tiba() + "," + coco.getKd_gudang() + coco.getRef_number();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();

        return id;
    }

    @Override
    public String savedata_coarricodecocontianer_cont(ModelCoarriCodecoContainer coco, String id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "COCOCONT";
        column = "ID,NO_CONT,UK_CONT,NO_SEGEL,JNS_CONT,NO_BL_AWB,TGL_BL_AWB,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
                + "ID_CONSIGNEE,CONSIGNEE,BRUTO,NO_BC11,TGL_BC11,NO_POS_BC11,KD_TIMBUN,KD_DOK_INOUT,"
                + "NO_DOK_INOUT,TGL_DOK_INOUT,WK_INOUT,KD_SAR_ANGKUT_INOUT,NO_POL,FL_CONT_KOSONG,ISO_CODE,"
                + "PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,FLAG,GUDANG_TUJUAN,KODE_KANTOR,NO_DAFTAR_PABEAN,"
                + "TGL_DAFTAR_PABEAN,NO_SEGEL_BC,TGL_SEGEL_BC,NO_IJIN_TPS,TGL_IJIN_TPS,REF_NUMBER,KD_DOK,FLAG";
        value = id + "," + coco.getNo_cont() + "," + coco.getUk_cont() + "," + coco.getNo_segel() + "," + coco.getJns_cont()
                + "," + coco.getNo_bl_awb() + "," + coco.getTgl_bl_awb() + "," + coco.getNo_master_bl_awb() + "," + coco.getTgl_master_bl_awb()
                + "," + coco.getId_consignee() + "," + coco.getConsignee() + "," + coco.getBruto() + "," + coco.getNo_bc11() + "," + coco.getTgl_bc11()
                + "," + coco.getNo_pos_bc11() + "," + coco.getKd_timbun() + "," + coco.getKd_dok_inout() + "," + coco.getNo_dok_inout() + "," + coco.getTgl_dok_inout()
                + "," + coco.getWk_inout() + "," + coco.getKd_sar_angkut_inout() + "," + coco.getNo_pol() + "," + coco.getFl_cont_kosong() + "," + coco.getIso_code()
                + "," + coco.getPel_muat() + "," + coco.getPel_transit() + "," + coco.getPel_bongkar() + ",flag," + coco.getGudang_tujuan() + "," + coco.getKode_kantor()
                + "," + coco.getNo_daftar_pabean() + "," + coco.getTgl_daftar_pabean() + "," + coco.getNo_segel_bc() + "," + coco.getTgl_segel_bc() + "," + coco.getNo_ijin_tps()
                + "," + coco.getTgl_ijin_tps() + "," + coco.getRef_number() + "," + coco.getKd_dok() + ",flag";
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_coarricodecokemasan_header(ModelCoarriCodecoKemasan coco) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = new ArrayList<>();
        tabel = "DUAL";
        column = "COARRI_CODECO_SEQ.NEXTVAL as ID";
        data = dbO.query_select_raw(tabel, column);
        String id = data.get(0);

        tabel = "COCOHDR";
        column = "ID,KD_DOK,KD_TPS,NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER";
        value = id + "," + coco.getKd_dok() + "," + coco.getKd_tps() + "," + coco.getNm_angkut()
                + "," + coco.getNo_voy_flight() + "," + coco.getCall_sign()
                + "," + coco.getTgl_tiba() + "," + coco.getKd_gudang() + coco.getRef_number();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();

        return id;
    }

    @Override
    public String savedata_coarricodecokemasan_kms(ModelCoarriCodecoKemasan coco, String id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "COCOKMS";
        column = "ID,NO_BL_AWB,TGL_BL_AWB,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
                + "ID_CONSIGNEE,CONSIGNEE,BRUTO,NO_BC11,TGL_BC11,NO_POS_BC11,CONT_ASAL,SERI_KEMAS,KD_KEMAS,"
                + "JML_KEMAS,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,WK_INOUT,KD_SAR_ANGKUT_INOUT,"
                + "NO_POL,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,FLAG,GUDANG_TUJUAN,KODE_KANTOR,"
                + "NO_DAFTAR_PABEAN,TGL_DAFTAR_PABEAN,NO_SEGEL_BC,TGL_SEGEL_BC,NO_IJIN_TPS,TGL_IJIN_TPS,REF_NUMBER,KD_DOK";
        value = id + "," + coco.getNo_bl_awb() + "," + coco.getTgl_bl_awb() + "," + coco.getNo_master_bl_awb() + "," + coco.getTgl_master_bl_awb()
                + "," + coco.getId_consignee() + "," + coco.getConsignee() + "," + coco.getBruto() + "," + coco.getNo_bc11() + "," + coco.getTgl_bc11()
                + "," + coco.getNo_pos_bc11() + "," + coco.getCont_asal() + "," + coco.getSeri_kemas() + "," + coco.getKd_kemas() + "," + coco.getJml_kemas()
                + "," + coco.getKd_timbun() + "," + coco.getKd_dok_inout() + "," + coco.getNo_dok_inout() + "," + coco.getTgl_dok_inout()
                + "," + coco.getWk_inout() + "," + coco.getKd_sar_angkut_inout() + "," + coco.getNo_pol()
                + "," + coco.getPel_muat() + "," + coco.getPel_transit() + "," + coco.getPel_bongkar() + ",flag," + coco.getGudang_tujuan() + "," + coco.getKode_kantor()
                + "," + coco.getNo_daftar_pabean() + "," + coco.getTgl_daftar_pabean() + "," + coco.getNo_segel_bc() + "," + coco.getTgl_segel_bc() + "," + coco.getNo_ijin_tps()
                + "," + coco.getTgl_ijin_tps() + "," + coco.getRef_number() + "," + coco.getKd_dok();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public boolean cekdata_coarricodecshl_header(ModelCoarriCodecoSHL shl) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result;
        data = new ArrayList<>();
        tabel = "COCOHDR";
        column = "ID";
        colomn_where = "VESSEL_CODE,VESSEL_NAME,VOYAGE_NO,ETA,ETD";
        value_where = shl.getVessel_code() + "," + shl.getVessel_name() + "," + shl.getNo_voy_flight() + "," + shl.getEta() + "," + shl.getEtd();
        data = dbO.query_select_with_where(tabel, column, colomn_where, value_where, "AND,AND,AND,AND");
        dbO.close_connection();
        result = !data.get(0).equalsIgnoreCase(null);
        return result;
    }

    @Override
    public String savedata_coarricodecshl_header(ModelCoarriCodecoSHL shl) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = new ArrayList<>();
        tabel = "DUAL";
        column = "COARRI_CODECO_SEQ.NEXTVAL as ID";
        data = dbO.query_select_raw(tabel, column);
        String id = data.get(0);

        tabel = "COCOHDR";
        column = "ID,KD_TPS,KD_GUDANG,VESSEL_CODE,VESSEL_NAME,VOYAGE_NO,CALL_SIGN,ETA,ETD,WK_REKAM";
        value = id + "," + shl.getKd_tps() + "," + shl.getKd_gudang() + "," + shl.getVessel_code() + "," + shl.getVessel_name()
                + "," + shl.getNo_voy_flight() + "," + shl.getCall_sign() + "," + shl.getEta() + "," + shl.getEtd() + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();

        return id;
    }

    @Override
    public String savedata_coarricodecshl_con(ModelCoarriCodecoSHL shl, String id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "COCOCONT";
        column = "ID,KD_DOK,NO_CONT,UK_CONT,"
                + "OWNER,ISO_CODE,NO_SEGEL,BRUTO,LOC_IN_VESSEL,"
                + "LOC_IN_YARD,WK_INOUT,TRUCKER_NAME,NO_POL,"
                + "POL,POT,POD,REF_NUMBER,NO_BL_AWB,TGL_BL_AWB,"
                + "NO_BOOKING,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
                + "ID_CONSIGNEE,CONSIGNEE,NO_BC11,TGL_BC11,NO_POS_BC11,"
                + "KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,"
                + "FL_CONT_KOSONG,TEMPERATURE,SHIPPER,STAT_CONT,REMARKS,"
                + "WK_REKAM,PORT_ORIGIN,PORT_FINAL_DESTINATION";
        value = id + "," + shl.getKd_dok() + "," + shl.getNo_cont() + "," + shl.getUk_cont()
                + "," + shl.getOwner() + "," + shl.getIso_code() + "," + shl.getNo_segel() + "," + shl.getBruto() + "," + shl.getLoc_in_vessel()
                + "," + shl.getLoc_in_yard() + "," + shl.getWk_inout() + "," + shl.getTrucker_name() + "," + shl.getNo_pol()
                + "," + shl.getPel_muat() + "," + shl.getPel_transit() + "," + shl.getPel_bongkar() + "," + shl.getRef_number() + "," + shl.getNo_bl_awb() + "," + shl.getTgl_bl_awb()
                + "," + shl.getBooking_no() + "," + shl.getNo_master_bl_awb() + "," + shl.getTgl_master_bl_awb()
                + "," + shl.getId_consignee() + "," + shl.getConsignee() + "," + shl.getNo_bc11() + "," + shl.getTgl_bc11() + "," + shl.getNo_pos_bc_11()
                + "," + shl.getKd_dok_inout() + "," + shl.getNo_dok_inout() + "," + shl.getTgl_dok_inout()
                + "," + shl.getFl_cont_kosong() + "," + shl.getTemperature() + "," + shl.getShipper() + "," + shl.getStat_cont() + "," + shl.getRemarks()
                + "," + getCurrentTimeStamp() + "," + shl.getPort_origin() + "," + shl.getPort_final_destination();

        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_spjm_header(ModelGetSPJM spjm) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new ArrayList<>();

        tabel = "SPJMHDR";
        column = "CAR,KD_KANTOR,NO_PIB,TGL_PIB,NPWP_IMP,"
                + "NAMA_IMP,NPWP_PPJK,NAMA_PPJK,KD_GUDANG,JML_CONT,NO_BC11,TGL_BC11,"
                + "NO_POS_BC11,FL_KARANTINA,NM_ANGKUT,NO_VOY_FLIGHT,RECEIVED_DATE";
        value = spjm.getCAR() + "," + spjm.getKD_KANTOR() + "," + spjm.getNO_PIB() + "," + spjm.getTGL_PIB() + "," + spjm.getNPWP_IMP() + ","
                + spjm.getNAMA_IMP() + "," + spjm.getNPWP_PPJK() + "," + spjm.getNAMA_PPJK() + "," + spjm.getGUDANG() + "," + spjm.getJML_CONT() + "," + spjm.getNO_BC11() + "," + spjm.getTGL_BC11() + ","
                + spjm.getNO_POS_BC11() + "," + spjm.getFL_KARANTINA() + "," + spjm.getNM_ANGKUT() + "," + spjm.getNO_VOY_FLIGHT() + "," + getCurrentTimeStamp();

        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public boolean cekdata_spjm_header(ModelGetSPJM spjm) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result;
        data = new ArrayList<>();
        tabel = "SPJMHDR";
        column = "*";
        colomn_where = "CAR";
        value_where = spjm.getCAR();
        data = dbO.query_select_with_where(tabel, column, colomn_where, value_where, "");
        dbO.close_connection();
        result = !data.get(0).equalsIgnoreCase("datakosong");
        return result;
    }

    @Override
    public String savedata_spjm_kms(ModelGetSPJM spjm) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new ArrayList<>();

        tabel = "SPJMKMS";
        column = "CAR,JNS_KMS,MERK_KMS,JML_KMS";
        value = spjm.getCAR() + "," + spjm.getJNS_KMS() + "," + spjm.getMERK_KMS() + "," + spjm.getJML_KMS();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_spjm_cont(ModelGetSPJM spjm) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new ArrayList<>();

        tabel = "SPJMCONT";
        column = "CAR,NO_CONT,SIZE_CONT";
        value = spjm.getCAR() + "," + spjm.getNO_CONT() + "," + spjm.getSIZE().substring(0, 11) + "," + spjm.getJML_KMS().substring(0, 2);
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_spjm_dok(ModelGetSPJM spjm) {
        /*struktur tunggu dari mbak aslich*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static String getCurrentTimeStamp() {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }

    @Override
    public boolean cekdata_sppb_header(ModelSPPB sppb) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result;
        data = new ArrayList<>();
        tabel = "CUSPERMITHDR";
        column = "*";
        colomn_where = "CAR";
        value_where = sppb.getCAR();
        data = dbO.query_select_with_where(tabel, column, colomn_where, value_where, "");
        dbO.close_connection();
        result = !data.get(0).equalsIgnoreCase(null);
        return result;
    }

    @Override
    public String savedata_sppb_header(ModelSPPB sppb) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new ArrayList<>();

        tabel = "CUSPERMITHDR";
        column = "CAR,NO_SPPB,TGL_SPPB,KD_KPBC,NO_PIB,"
                + "TGL_PIB,NPWP_IMP,NAMA_IMP,ALAMAT_IMP,NPWP_PPJK,NAMA_PPJK,"
                + "ALAMAT_PPJK,NM_ANGKUT,NO_VOY_FLIGHT,BRUTO,NETTO,GUDANG,"
                + "STATUS_JALUR,JML_CONT,NO_BC11,TGL_BC11,NO_POS_BC11,NO_BL_AWB,"
                + "TG_BL_AWB,NO_MASTER_BL_AWB,TG_MASTER_BL_AWB,WK_INSERT";
        value = sppb.getCAR() + "," + sppb.getNO_SPPB() + "," + sppb.getTGL_SPPB() + "," + sppb.getKD_KPBC() + "," + sppb.getNO_PIB() + ","
                + sppb.getTGL_PIB() + "," + sppb.getNPWP_IMP() + "," + sppb.getNAMA_IMP() + "," + sppb.getALAMAT_IMP() + "," + sppb.getNPWP_PPJK() + "," + sppb.getNAMA_PPJK() + ","
                + sppb.getALAMAT_PPJK() + "," + sppb.getNM_ANGKUT() + "," + sppb.getNO_VOY_FLIGHT() + "," + sppb.getBRUTTO() + "," + sppb.getNETTO() + "," + sppb.getGUDANG() + ","
                + sppb.getSTATUS_JALUR() + "," + sppb.getJML_CONT() + "," + sppb.getNO_BC11() + "," + sppb.getTGL_BC11() + "," + sppb.getNO_POS_BC11() + "," + sppb.getNO_BL_AWB() + ","
                + sppb.getTG_BL_AWB() + "," + sppb.getNO_MASTER_BL_AWB() + "," + sppb.getTG_MASTER_BL_AWB() + ","
                + getCurrentTimeStamp();

        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_cococarter_header(ModelCoCoCarTer carter) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = new ArrayList<>();
        tabel = "DUAL";
        column = "COARRI_CODECO_SEQ.NEXTVAL as ID";
        data = dbO.query_select_raw(tabel, column);
        String id = data.get(0);

        tabel = "COCOHDR";
        column = "ID,KD_DOK,KD_TPS,NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER,RECEIVED_DATE";
        value = id + "," + carter.getKd_dok() + "," + carter.getNm_angkut() + "," + carter.getNo_voy_flight() + "," + carter.getCall_sign()
                + "," + carter.getTgl_tiba() + "," + carter.getKd_gudang() + "," + carter.getRef_number() + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += dbO.get_message_error() + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();

        return id;
    }

    @Override
    public String savedata_cococarter_cont(ModelCoCoCarTer carter, String id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "COCOCARTER";
        column = "ID,NO_BL_AWB,TGL_BL_AWB,ID_CONSIGNEE,CONSIGNEE,NO_BC11,TGL_BC11,NO_POS_BC11,VIN_NUMBER,"
                + "NO_RANGKA,NO_MESIN,TIPE,WARNA,MERK,BRUTO,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,WK_INOUT,"
                + "KD_SAR_ANGKUT_INOUT,NO_POL,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,GUDANG_TUJUAN";
        value = id + "," + carter.getNo_bl_awb() + "," + carter.getTgl_bl_awb() + "," + carter.getId_consignee() + "," + carter.getConsignee()
                + "," + carter.getNo_bc11() + "," + carter.getTgl_bc11() + "," + carter.getNo_pos_bc11() + "," + carter.getVin_number()
                + "," + carter.getNo_rangka() + "," + carter.getNo_mesin() + "," + carter.getTipe() + "," + carter.getWarna() + "," + carter.getMerk()
                + "," + carter.getBruto() + "," + carter.getKd_timbun() + "," + carter.getKd_dok_inout() + "," + carter.getNo_dok_inout() + "," + carter.getTgl_dok_inout()
                + "," + carter.getWk_inout() + "," + carter.getKd_sar_angkut_inout() + "," + carter.getNo_pol() + "," + carter.getPel_muat()
                + "," + carter.getPel_transit() + "," + carter.getPel_bongkar() + "," + carter.getGudang_tujuan();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_sppb_kms(ModelSPPB sppb) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new ArrayList<>();
        tabel = "CUSPERMITKMS";
        column = "CAR,JNS_KMS,MERK_KMS,JML_KMS,FLAG_TRANSFER_IPC,WK_INSERT";
        value = sppb.getCAR() + "," + sppb.getJNS_KMS() + "," + sppb.getMERK_KMS() + "," + sppb.getJML_KMS() + "," + "0" + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_sppb_cont(ModelSPPB sppb) {
        try {
            OpenConnection();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new ArrayList<>();
        tabel = "CUSPERMITCONT";
        column = "CAR,NO_CONT,UK_CONT,JNS_MUAT,FLAG_TRANSFER_IPC,WK_INSERT";
        value = sppb.getCAR() + "," + sppb.getNO_CONT() + "," + sppb.getSIZE() + "," + sppb.getJNS_MUAT() + "," + "0" + "," + getCurrentTimeStamp();
        if (!dbO.query_insert(tabel, column, value)) {
            messageer += log.LogError(dbO.get_message_error()) + "\r\n";
            cf.newFile("", "Error" + getCurrentTimeStamp() + ".txt", messageer);
            messageer = "";
        }
        dbO.close_connection();
        return "";
    }

    @Override
    public String savedata_DW_header(List<ModelDW> dw, String RefNumber, String responId) {
//        try {
//            OpenConnection();
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        tabel = "COCOHDR";
//        column = "ID";
//        colomn_where = "VESSEL_CODE,VESSEL_NAME,VOYAGE_NO,ETA,ETD";
//        value_where = shl.getVessel_code() + "," + shl.getVessel_name() + "," + shl.getNo_voy_flight() + "," + shl.getEta() + "," + shl.getEtd();
//        data = dbO.query_select_with_where(tabel, column, colomn_where, value_where, "AND,AND,AND,AND");
//        dbO.close_connection();
//        tabel = "SELECT A.KODE_TPS_ASAL , A.KODE_GUDANG_ASAL , A.CALL_SIGN, A.NAMA_KAPAL, "
//                + "A.NO_VOYAGE, A.KODE_TPS_TUJUAN, A.KODE_GUDANG_TUJUAN, B.REF_NUMBER, B.RESPONID   "
//                + "FROM T_REQUEST_PLP A "
//                + "INNER JOIN T_RESPON_PLP B ON A.REF_NUMBER = B.REF_NUMBER";
//        column = "SELECT A.KODE_TPS_ASAL , A.KODE_GUDANG_ASAL , A.CALL_SIGN, A.NAMA_KAPAL, "
//                + "A.NO_VOYAGE, A.KODE_TPS_TUJUAN, A.KODE_GUDANG_TUJUAN, B.REF_NUMBER, B.RESPONID   "
//                + "FROM T_REQUEST_PLP A "
//                + "INNER JOIN T_RESPON_PLP B ON A.REF_NUMBER = B.REF_NUMBER "
//                + "WHERE A.KODE_TPS_ASAL <> ? AND B.FL_SEND = ?"
//                + "AND B.REF_NUMBER = ? AND B.RESPONID =?";
//        String field = "KD_TPS_ASAL,GUDANG_ASAL,KD_TPS_TUJUAN,GUDANG_TUJUAN,CALL_SIGN,NM_ANGKUT,NO_VOY_FLIGHT";
//        dw = dbO.query_select_with_where(tabel, field, column, value_where, column);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String savedata_DW_cont(List<ModelDW> dw, String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
