/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingFile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelGetResponBatalPLPTujuan;
import com.edii.operation.db.operation;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingGetResponBatalPLPTujuan {
    
    private ParsingXML px;
    private ModelGetResponBatalPLPTujuan model;
    private SaveData sv;
    
    public String parseDocumentPLPBatalTujuan(String fStream) throws Exception {
        String result = null;
        px = new ParsingXML();
        model = new ModelGetResponBatalPLPTujuan();
        sv = new operation();
        
        try {
            
            if (fStream != null) {
                ArrayList<String> header = new ArrayList<>();
                header = px.xmlParsing(fStream, "RESPON_BATAL>HEADER", "KD_KANTOR,KD_TPS,KD_TPS_ASAL,NO_PLP,TGL_PLP,NO_BATAL_PLP,TGL_BATAL_PLP");
                
                String[] split_header = header.get(0).split(",");
                model.setKd_kantor(split_header[0]);
                model.setKd_tps(split_header[1]);
                model.setKd_tps_asal(split_header[2]);
                model.setNo_batal_plp(split_header[3]);
                model.setTgl_batal_plp(split_header[4]);
                model.setNo_batal_plp(split_header[5]);
                model.setTgl_batal_plp(split_header[6]);
                
                String respon_id = sv.savedata_getresponbatalplptujuan_header(model);
                
                ArrayList<String> detil_kms = new ArrayList<>();
                detil_kms = px.xmlParsing(fStream, "RESPON_BATAL>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB");
                
                for (String detil : detil_kms) {
                    String[] split_kms = detil.split(",");
                    model.setJns_kms(split_kms[0]);
                    model.setJml_kms(split_kms[1]);
                    model.setNo_bl_awb(split_kms[2]);
                    model.setTgl_bl_awb(split_kms[3]);
                    sv.savedata_getresponbatalplptujuan_kms(model, respon_id);
                }
                
                ArrayList<String> detil_cont = new ArrayList<>();
                detil_cont = px.xmlParsing(fStream, "RESPONPLP>DETIL>CONT", "NO_CONT,UK_CONT");
                
                for (String detil : detil_cont) {
                    String[] split_cont = detil.split(",");
                    model.setNo_cont(split_cont[0]);
                    model.setUk_cont(split_cont[1]);
                    sv.savedata_getresponbatalplptujuan_cont(model, respon_id);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
