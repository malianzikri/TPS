/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelGetResponPLP;
import com.edii.operation.db.logDatabase;
import com.edii.operation.db.operation;
import com.edii.tools.Tanggalan;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingGetResponPLP {

    private ParsingXML px;
    private ModelGetResponPLP model;
    private SaveData sv;
    logDatabase logDb;

    public String parseDocumentPLPAsal(String fStream) throws Exception {

        String result = "";
        px = new ParsingXML();
        model = new ModelGetResponPLP();
        sv = new operation();
        boolean condition = false;
        logDb = new logDatabase();
        Tanggalan tgl = new Tanggalan();
        try {
            if (fStream != null) {
                ArrayList<String> header = new ArrayList<>();
                header = px.xmlParsing(fStream, "RESPONPLP>HEADER", "KD_KANTOR,KD_TPS,REF_NUMBER,NO_PLP,TGL_PLP,ALASAN_REJECT");
                for (String headers : header) {
                    String[] split_header = headers.split(",");
                    model.setKd_kantor(split_header[0]);
                    model.setKd_tps(split_header[1]);
                    model.setRef_number(split_header[2]);
                    model.setNo_plp(split_header[3]);
                    model.setTgl_plp(split_header[4]);
                    model.setAlasan_reject(split_header[5]);
                    String respon_id = sv.savedata_getresponplp_header(model);

                    ArrayList<String> detil_kms = new ArrayList<>();
                    detil_kms = px.xmlParsing(fStream, "RESPONPLP>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB,FL_SETUJU");

                    for (String detil : detil_kms) {
                        String[] split_kms = detil.split(",");
                        model.setJns_kms(split_kms[0]);
                        model.setJml_kms(split_kms[1]);
                        model.setNo_bl_awb(split_kms[2]);
                        model.setTgl_bl_awb(split_kms[3]);
                        model.setFl_setuju_kms(split_kms[4]);
                        sv.savedata_getresponplp_kms(model, respon_id);
                    }

                    ArrayList<String> detil_cont = new ArrayList<>();
                    detil_cont = px.xmlParsing(fStream, "RESPONPLP>DETIL>CONT", "NO_CONT,UK_CONT,FL_SETUJU");

                    for (String detil : detil_cont) {
                        String[] split_cont = detil.split(",");
                        model.setNo_cont(split_cont[0]);
                        model.setUk_cont(split_cont[1]);
                        model.setFl_setuju_cont(split_cont[2]);
                        sv.savedata_getresponplp_cont(model, respon_id);
                    }
                    condition = true;
                }
                if (condition) {
                    logDb.insert_mblling("RESPLP", tgl.UNIXNUMBER(), model.getKd_tps(), fStream);
                }
                result = "SUCCESS";
            }
        } catch (Exception ex) {
            boolean hasil = logDb.excuteLogError(ex.getMessage(), "execute_class_parsing_getresponplp", "CoarriCodeco_Kemasan");
            result = "NOT SUCCESS";
        } finally {
            px = null;
            model = null;
            sv = null;
        }
        return result;
    }
}
