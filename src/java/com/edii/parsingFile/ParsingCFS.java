/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelCFS;
import com.edii.operation.db.logDatabase;
import com.edii.operation.db.operation;
import com.edii.tools.ResCFS;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingCFS {

    private ParsingXML px;
    private ModelCFS model;
    private SaveData sv;
    private logDatabase logDb;

    public String parseDocument(String fStream) throws Exception {
        String result = null;
        px = new ParsingXML();
        model = new ModelCFS();
        sv = new operation();
        boolean duplicateFile;
        boolean duplicateRef = false;
        String tempRefNumber = null;
        String id = "";
        ResCFS res = null;
        logDb = new logDatabase();

        try {
            res = new ResCFS();

            if (fStream != null) {
                ArrayList<String> header = new ArrayList();
                header = px.xmlParsing(fStream, "LOADUBAHSTATUS>HEADER,", "KD_KANTOR,KD_TPS_ASAL,"
                        + "REF_NUMBER,NO_SURAT,TGL_SURAT,GUDANG_ASAL,KD_TPS_TUJUAN,GUDANG_TUJUAN,"
                        + "CALL_SIGN,NM_ANGKUT,"
                        + "NO_VOY_FLIGHT,TGL_TIBA,NO_BC11,TGL_BC11,NM_PEMOHON");
                for (String headers : header) {
                    String[] split_header = headers.split(",");
                    model.setKd_kantor(split_header[0]);
                    model.setKd_tps_asal(split_header[1]);

                    if (sv.cek_condition_data("REFF_TPS", "KD_TPS", model.getKd_tps_asal())) {
                        result = res.KdRes("004");
                    }

                    model.setRef_number(split_header[2]);
                    model.setNo_surat(split_header[3]);
                    model.setTgl_surat(split_header[4]);
                    model.setGudang_asal(split_header[5]);

                    if (sv.cek_condition_data("REFF_GUDANG", "KD_GUDANG", model.getGudang_asal())) {
                        result = res.KdRes("005");
                    }
                    model.setKd_tps_tujuan(split_header[6]);

                    if (sv.cek_condition_data("REFF_TPS", "KD_TPS", model.getKd_tps_tujuan())) {
                        result = res.KdRes("006");
                    }
                    model.setGudang_tujuan(split_header[7]);

                    if (sv.cek_condition_data("REFF_GUDANG", "KD_GUDANG", model.getGudang_tujuan())) {
                        result = res.KdRes("007");
                    }
                    model.setCall_sign(split_header[8]);
                    model.setNm_angkut(split_header[9]);
                    model.setNo_voy_flight(split_header[10]);
                    model.setTgl_tiba(split_header[11]);
                    model.setNo_bc11(split_header[12]);
                    model.setTgl_bc11(split_header[13]);
                    model.setNm_pemohon(split_header[14]);
                    duplicateFile = false;
                    if (sv.cekdata_cfs_header(model)) {
                        duplicateFile = true;
                        sv.savedata_cfs_header(model);
                    } else {
                        if (tempRefNumber == null) {
                            tempRefNumber = model.getRef_number() + ";";
                            //Jika RefNumber sudah Ada
                            result = res.KdRes("103");
                        } else {
                            tempRefNumber = model.getRef_number() + ";" + tempRefNumber;
                        }
                    }

                    ArrayList<String> detil_kms = new ArrayList();
                    detil_kms = px.xmlParsing(fStream, "LOADUBAHSTATUS>DETIL>KMS",
                            "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB");
                    model.setJns_kms(split_header[0]);
                    model.setJml_kms(split_header[0]);
                    model.setNo_bl_awb(split_header[0]);
                    model.setTgl_bl_awb(split_header[0]);
                    if (duplicateFile) {
                        sv.savedata_cfs_kms(model);
                    }

                    ArrayList<String> detil_cont = new ArrayList();
                    detil_cont = px.xmlParsing(fStream, "LOADUBAHSTATUS>DETIL>CONT",
                            "NO_CONT,UK_CONT");
                    model.setNo_cont(split_header[0]);
                    model.setUk_cont(split_header[0]);
                    if (duplicateFile) {
                        sv.savedata_cfs_kms(model);
                    }
                }
                result = "SUCCESS";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = res.KdRes("002");
            logDb.excuteLogError(e.getMessage(), "execute_class_ParsingCFS", result);
            result = "NOT SUCCESS";
        }
        return null;
    }
}
