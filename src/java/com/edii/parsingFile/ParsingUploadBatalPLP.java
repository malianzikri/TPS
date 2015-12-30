/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingFile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelUploadBatalPLP;
import com.edii.operation.operation;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingUploadBatalPLP {

    private ParsingXML px;
    private ModelUploadBatalPLP model;
    private SaveData sv;

    public String parseDocument(String fStream) throws Exception {
        String result = null;
        px = new ParsingXML();
        model = new ModelUploadBatalPLP();
        sv = new operation();
        try {
            if (fStream != null) {

                String header = px.getStringParsingXml(fStream, "BATALPLP>HEADER", "KD_KANTOR,TIPE_DATA,KD_TPS,REF_NUMBER,NO_SURAT,TGL_SURAT,NO_PLP,TGL_PLP,ALASAN,NO_BC11,TGL_BC11,NM_PEMOHON");
                String[] split_header = header.replace(";", "").split(",");

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
                
                String detil_kms = px.getStringParsingXml(fStream, "BATALPLP>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB");
                String[] split_detil_kms = detil_kms.split(";");
                
                for (String detil : split_detil_kms) {
                    String[] split_kms = detil.split(",");
                    model.setJns_kms(split_kms[0]);
                    model.setJml_kms(split_kms[1]);
                    model.setNo_bl_awb(split_kms[2]);
                    model.setTgl_bl_awb(split_kms[3]);
                    sv.savedata_uploadbatalplp(model, "kms");
                }
                
                String detil_cont = px.getStringParsingXml(fStream, "BATALPLP>DETIL>CONT", "NO_CONT,UK_CONT");
                String[] split_detil_cont = detil_cont.split(";");

                for (String detil : split_detil_cont) {
                    String[] split_cont = detil.split(",");
                    model.setNo_cont(split_cont[0]);
                    model.setUk_cont(split_cont[1]);
                    sv.savedata_uploadbatalplp(model, "cont");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return result;
        }
    }

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }
}
