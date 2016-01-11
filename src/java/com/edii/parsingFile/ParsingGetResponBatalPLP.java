/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelGetResponBatalPLP;
import com.edii.operation.db.operation;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingGetResponBatalPLP {
    
    private ParsingXML px;
    private ModelGetResponBatalPLP model;
    private SaveData sv;
    
    public String parseDocumentPLPBatalAsal(String fStream) throws Exception {
        
        String result = null;
        px = new ParsingXML();
        model = new ModelGetResponBatalPLP();
        sv = new operation();
        try {
            if (fStream != null) {
                ArrayList<String> header = new ArrayList<>();
                header = px.xmlParsing(fStream, "RESPON_BATAL>HEADER", "KD_KANTOR,KD_TPS,REF_NUMBER,NO_BATAL_PLP,TGL_BATAL_PLP");
                
                String[] split_header = header.get(0).split(",");
                model.setKd_kantor(split_header[0]);
                model.setKd_tps(split_header[1]);
                model.setRef_number(split_header[2]);
                model.setNo_batal_plp(split_header[3]);
                model.setTgl_batal_plp(split_header[4]);
                
                String respon_id = sv.savedata_getresponbatalplp_header(model);
                
                ArrayList<String> detil_kms = new ArrayList<>();
                detil_kms = px.xmlParsing(fStream, "RESPON_BATAL>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB,FL_SETUJU");
                for (String detil : detil_kms) {
                    String[] split_kms = detil.split(",");
                    model.setJns_kms(split_kms[0]);
                    model.setJml_kms(split_kms[1]);
                    model.setNo_bl_awb(split_kms[2]);
                    model.setTgl_bl_awb(split_kms[3]);
                    model.setFl_setuju_kms(split_kms[4]);
                    sv.savedata_getresponbatalplp_kms(model, respon_id);
                }
                
                ArrayList<String> detil_cont = new ArrayList<>();
                detil_cont = px.xmlParsing(fStream, "RESPONPLP>DETIL>CONT", "NO_CONT,UK_CONT,FL_SETUJU");
                for (String detil : detil_cont) {
                    String[] split_cont = detil.split(",");
                    model.setNo_cont(split_cont[0]);
                    model.setUk_cont(split_cont[1]);
                    model.setFl_setuju_cont(split_cont[2]);
                    sv.savedata_getresponbatalplp_cont(model, respon_id);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
        
    }
}
