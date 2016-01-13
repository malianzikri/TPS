/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelGetResponPLPTujuan;
import com.edii.operation.db.logDatabase;
import com.edii.operation.db.operation;
import com.edii.tools.Tanggalan;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingGetResponPLPTujuan {

    private ParsingXML px;
    private ModelGetResponPLPTujuan model;
    private SaveData sv;
    logDatabase logDb;

    public String parseDocumentPLPTujuan(String fStream) throws Exception {

        String result = null;
        px = new ParsingXML();
        model = new ModelGetResponPLPTujuan();
        sv = new operation();
        boolean condition = false;
        Tanggalan tgl = new Tanggalan();
        logDb = new logDatabase();
        try {

            if (fStream != null) {
                ArrayList<String> header = new ArrayList<>();
                header = px.xmlParsing(fStream, "RESPONPLP>HEADER", "KD_KANTOR,"
                        + "KD_TPS,KD_TPS_ASAL,GUDANG_TUJUAN,NO_PLP,TGL_PLP,CALL_SIGN,NM_ANGKUT,"
                        + "NO_VOY_FLIGHT,TGL_TIBA,NO_BC11,TGL_BC11,NO_SURAT,TGL_SURAT");
                for (String headers : header) {
                    String[] split_header = headers.split(",");
                    model.setKd_kantor(split_header[0]);
                    model.setKd_tps(split_header[1]);
                    model.setKd_tps_asal(split_header[2]);
                    model.setGudang_tujuan(split_header[3]);
                    model.setNo_plp(split_header[4]);
                    model.setTgl_plp(split_header[5]);
                    model.setCall_sign(split_header[6]);
                    model.setNm_angkut(split_header[7]);
                    model.setNo_voy_flight(split_header[8]);
                    model.setTgl_tiba(split_header[9]);
                    model.setNo_bc11(split_header[10]);
                    model.setTgl_bc11(split_header[11]);
                    model.setTgl_surat(split_header[12]);

                    String respon_id = sv.savedata_getresponplptujuan_header(model);

                    ArrayList<String> detil_kms = new ArrayList<>();
                    detil_kms = px.xmlParsing(fStream, "RESPONPLP>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB");

                    for (String detil : detil_kms) {
                        String[] split_kms = detil.split(",");
                        model.setJns_kms(split_kms[0]);
                        model.setJml_kms(split_kms[1]);
                        model.setNo_bl_awb(split_kms[2]);
                        model.setTgl_bl_awb(split_kms[3]);
                        sv.savedata_getresponplptujuan_kms(model, respon_id);
                    }

                    ArrayList<String> detil_cont = new ArrayList<>();
                    detil_cont = px.xmlParsing(fStream, "RESPONPLP>DETIL>CONT", "NO_CONT,UK_CONT,"
                            + "JNS_CONT,NO_POS_BC11,CONSIGNEE,NO_BL_AWB,TGL_BL_AWB");

                    for (String detil : detil_cont) {
                        String[] split_cont = detil.split(",");
                        model.setNo_cont(split_cont[0]);
                        model.setUk_cont(split_cont[1]);
                        model.setJns_cont(split_cont[2]);
                        model.setNo_pos_bc11(split_cont[3]);
                        model.setConsignee(split_cont[4]);
                        model.setNo_bl_awb(split_cont[5]);
                        model.setTgl_bl_awb(split_cont[6]);
                        sv.savedata_getresponplptujuan_cont(model, respon_id);
                    }
                    condition = true;
                }
                if (condition) {
                    logDb.insert_mblling("RESPLP", tgl.UNIXNUMBER(), model.getKd_tps(), fStream);
                }
                result = "SUCCESS";
            }
        } catch (Exception ex) {
            boolean hasil = logDb.excuteLogError(ex.getMessage(), "execute_class_parsing_getresponplptujuan", "CoarriCodeco_Kemasan");
            result = "NOT SUCCESS";
        }
        return result;

    }

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }
}
