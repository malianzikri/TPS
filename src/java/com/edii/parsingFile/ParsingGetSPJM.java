/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelGetSPJM;
import com.edii.operation.db.operation;
import com.edii.tools.ExcuteProses;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingGetSPJM {

    private final ParsingXML px;
    private final ModelGetSPJM model;
    private final SaveData sv;

    public ParsingGetSPJM() {
        px = new ParsingXML();
        model = new ModelGetSPJM();
        sv = new operation();
    }

    public String parseDocument(String fStream) throws Exception {
        String result = "";
        String id = "";
        boolean duplicateFile;
        try {
            if (fStream != null) {
                ArrayList<String> header = new ArrayList<String>();
                header = px.xmlParsing(fStream, "SPJM>HEADER", "CAR,KD_KANTOR,NO_PIB,TGL_PIB,NPWP_IMP,NAMA_IMP,NPWP_PPJK,NAMA_PPJK,GUDANG,JML_CONT,NO_BC11,TGL_BC11,NO_POS_BC11,FL_KARANTINA,NM_ANGKUT,NO_VOY_FLIGHT");
                String[] split_header = header.get(0).split(",");
                model.setCAR(split_header[0]);
                model.setKD_KANTOR(split_header[1]);
                model.setNO_PIB(split_header[2]);
                model.setTGL_PIB(split_header[3]);
                model.setNPWP_IMP(split_header[4]);
                model.setNAMA_IMP(split_header[5]);
                model.setNPWP_PPJK(split_header[6]);
                model.setNAMA_PPJK(split_header[7]);
                model.setGUDANG(split_header[8]);
                model.setJML_CONT(split_header[9]);
                model.setNO_BC11(split_header[10]);
                model.setTGL_BC11(split_header[11]);
                model.setNO_POS_BC11(split_header[12]);
                model.setFL_KARANTINA(split_header[13]);
                model.setNM_ANGKUT(split_header[14]);
                model.setNO_VOY_FLIGHT(split_header[15]);

                duplicateFile = false;
                if (sv.cekdata_spjm_header(model)) {
                    duplicateFile = true;
                } else {
                    id = sv.savedata_spjm_header(model);
                }

                ArrayList<String> detil_kms = new ArrayList<>();
                detil_kms = px.xmlParsing(fStream, "SPJM>DETIL>KMS", "CAR,JNS_KMS,MERK_KMS,JML_KMS");
                for (String detil : detil_kms) {
                    String[] split_kms = detil.split(",");
                    model.setCAR(split_kms[0]);
                    model.setJNS_KMS(split_kms[1]);
                    model.setMERK_KMS(split_kms[2]);
                    model.setJML_KMS(split_kms[3]);
                    if (!duplicateFile) {
                        sv.savedata_spjm_kms(model);
                    }
                }

                ArrayList<String> detil_cont = new ArrayList<>();
                detil_cont = px.xmlParsing(fStream, "SPJM>DETIL>CONT", "CAR,NO_CONT,SIZE");
                for (String detil : detil_cont) {
                    String[] split_cont = detil.split(",");
                    model.setCAR(split_cont[0]);
                    model.setNO_CONT(split_cont[1]);
                    model.setSIZE(split_cont[2]);
                    if (!duplicateFile) {
                        sv.savedata_spjm_cont(model);
                    }
                }

                ArrayList<String> detil_dok = new ArrayList<>();
                detil_dok = px.xmlParsing(fStream, "SPJM>DETIL>DOK", "CAR,JNS_DOK,NO_DOK,TGL_DOK");
                for (String detil : detil_dok) {
                    String[] split_dok = detil.split(",");
                    model.setCAR(split_dok[0]);
                    model.setJNS_DOK(split_dok[1]);
                    model.setNO_DOK(split_dok[2]);
                    model.setTGL_DOK(split_dok[3]);
                    if (!duplicateFile) {
                        sv.savedata_spjm_dok(model);
                    }
                }
            }
        } catch (Exception ex) {
        } finally {
        }
        return result;
    }
}
