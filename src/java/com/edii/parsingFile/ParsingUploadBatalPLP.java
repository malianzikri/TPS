/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelUploadBatalPLP;
import com.edii.operation.db.logDatabase;
import com.edii.operation.db.operation;
import com.edii.tools.Tanggalan;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingUploadBatalPLP {

    private ParsingXML px;
    private ModelUploadBatalPLP model;
    private SaveData sv;
    logDatabase logDb;

    public String parseDocument(String fStream) throws Exception {
        String result = null;
        px = new ParsingXML();
        model = new ModelUploadBatalPLP();
        sv = new operation();
        logDb = new logDatabase();
        boolean condition = false;
        Tanggalan tgl = new Tanggalan();
        try {
            if (fStream != null) {
                ArrayList<String> header = new ArrayList<>();
                header = px.xmlParsing(fStream, "BATALPLP>HEADER", "KD_KANTOR,TIPE_DATA,KD_TPS,REF_NUMBER,NO_SURAT,TGL_SURAT,NO_PLP,TGL_PLP,ALASAN,NO_BC11,TGL_BC11,NM_PEMOHON");
                for (String headers : header) {
                    String[] split_header = headers.split(",");

                    model.setKd_kantor(split_header[0]);
                    model.setTipe_data(split_header[1]);
                    model.setKd_tps_asal(split_header[2]);
                    model.setRef_number(split_header[3]);
                    model.setNo_surat(split_header[4]);
                    model.setNo_batal_plp(split_header[5]);
                    model.setTgl_batal_plp(split_header[6]);
                    model.setAlasan(split_header[7]);
                    model.setNo_bc11(split_header[8]);
                    model.setTgl_bc11(split_header[9]);
                    model.setNm_pemohon(split_header[10]);
                    sv.savedata_uploadbatalplp(model, "header");

                    ArrayList<String> detil_kms = new ArrayList<>();
                    detil_kms = px.xmlParsing(fStream, "BATALPLP>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB");

                    for (String detil : detil_kms) {
                        String[] split_kms = detil.split(",");
                        model.setJns_kms(split_kms[0]);
                        model.setJml_kms(split_kms[1]);
                        model.setNo_bl_awb(split_kms[2]);
                        model.setTgl_bl_awb(split_kms[3]);
                        sv.savedata_uploadbatalplp(model, "kms");
                    }

                    ArrayList<String> detil_cont = new ArrayList<>();
                    detil_cont = px.xmlParsing(fStream, "BATALPLP>DETIL>CONT", "NO_CONT,UK_CONT");

                    for (String detil : detil_cont) {
                        String[] split_cont = detil.split(",");
                        model.setNo_cont(split_cont[0]);
                        model.setUk_cont(split_cont[1]);
                        sv.savedata_uploadbatalplp(model, "cont");
                    }
                    condition = true;
                }
                if (condition) {
                    logDb.insert_mblling("DOKPLP", tgl.UNIXNUMBER(), model.getRef_number(), fStream);
                }
                result = "SUCCESS";
            }
        } catch (Exception ex) {
            boolean hasil = logDb.excuteLogError(ex.getMessage(), "execute_class_parsinguploadbatalplp", "CoarriCodeco_Kemasan");
            result = "NOT SUCCESS";
        } finally {
            sv = null;
            model = null;
            px = null;
        }
        return result;
    }
}
