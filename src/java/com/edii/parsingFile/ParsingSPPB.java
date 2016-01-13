 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelSPPB;
import com.edii.operation.db.logDatabase;
import com.edii.operation.db.operation;
import com.edii.tools.Tanggalan;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingSPPB {

    private final ParsingXML px;
    private final ModelSPPB model;
    private final SaveData sv;
    logDatabase logDb;

    public ParsingSPPB() {
        px = new ParsingXML();
        model = new ModelSPPB();
        sv = new operation();
    }

    public String parseDocument(String fStream, String username) throws Exception {
        boolean duplicateFile;
        String result = "";
        String id = "";
        logDb = new logDatabase();
        boolean condition = false;
        Tanggalan tgl = new Tanggalan();
        try {
            ArrayList<String> list = new ArrayList<String>();
            if (fStream != null) {
                list = px.xmlParsing(fStream, "SPPB>HEADER", "CAR,NO_SPPB,TGL_SPPB,KD_KPBC,KD_KANTOR_PENGAWAS,KD_KANTOR_BONGKAR,NO_PIB,TGL_PIB"
                        + "NPWP_IMP,NAMA_IMP,ALAMAT_IMP,NPWP_PPJK,NAMA_PPJK,ALAMAT_PPJK,NM_ANGKUT,NO_VOY_FLIGHT,BRUTTO,NETTO,GUDANG,STATUS_JALUR,JML_CONT"
                        + "NO_BC11,TGL_BC11,NO_POS_BC11,NO_BL_AWB,TG_BL_AWB,NO_MASTER_BL_AWB,TG_MASTER_BL_AWB");
                for (String list_header : list) {
                    String[] split_header = list_header.split(",");
                    model.setCAR(split_header[0]);
                    model.setNO_SPPB(split_header[1]);
                    model.setTGL_SPPB(split_header[2]);
                    model.setTGL_SPPB(split_header[3]);
                    model.setKD_KPBC(split_header[4]);
                    model.setKD_KANTOR_PENGAWAS(split_header[5]);
                    model.setKD_KANTOR_BONGKAR(split_header[6]);
                    model.setNO_PIB(split_header[7]);
                    model.setTGL_PIB(split_header[8]);
                    model.setNPWP_IMP(split_header[9]);
                    model.setNAMA_IMP(split_header[10]);
                    model.setALAMAT_IMP(split_header[11]);
                    model.setNPWP_PPJK(split_header[12]);
                    model.setNAMA_PPJK(split_header[13]);
                    model.setALAMAT_PPJK(split_header[14]);
                    model.setNM_ANGKUT(split_header[15]);
                    model.setNO_VOY_FLIGHT(split_header[16]);
                    model.setBRUTTO(split_header[17]);
                    model.setNETTO(split_header[18]);
                    model.setGUDANG(split_header[19]);
                    model.setSTATUS_JALUR(split_header[20]);
                    model.setJML_CONT(split_header[21]);
                    model.setNO_BC11(split_header[22]);
                    model.setTGL_BC11(split_header[23]);
                    model.setNO_POS_BC11(split_header[24]);
                    model.setNO_BL_AWB(split_header[25]);
                    model.setTG_BL_AWB(split_header[26]);
                    model.setNO_MASTER_BL_AWB(split_header[27]);
                    model.setTG_MASTER_BL_AWB(split_header[28]);


                    duplicateFile = false;
                    if (sv.cekdata_sppb_header(model)) {
                        duplicateFile = true;
                    } else {
                        id = sv.savedata_sppb_header(model);
                    }

                    ArrayList<String> detil_kms = new ArrayList<>();
                    detil_kms = px.xmlParsing(fStream, "SPPB>DETIL>KMS", "CAR,JNS_KMS,MERK_KMS,JML_KMS");
                    for (String detil : detil_kms) {
                        String[] split_kms = detil.split(",");
                        model.setCAR(split_kms[0]);
                        model.setJNS_KMS(split_kms[1]);
                        model.setMERK_KMS(split_kms[2]);
                        model.setJML_KMS(split_kms[3]);
                        if (!duplicateFile) {
                            sv.savedata_sppb_kms(model);
                        }
                    }

                    ArrayList<String> detil_cont = new ArrayList<>();
                    detil_cont = px.xmlParsing(fStream, "SPJM>DETIL>CONT", "CAR,NO_CONT,SIZE,JNS_MUAT");
                    for (String detil : detil_cont) {
                        String[] split_cont = detil.split(",");
                        model.setCAR(split_cont[0]);
                        model.setNO_CONT(split_cont[1]);
                        model.setSIZE(split_cont[2]);
                        model.setJNS_MUAT(split_cont[3]);
                        if (!duplicateFile) {
                            sv.savedata_sppb_cont(model);
                        }
                    }
                    condition = true;
                }
                if (condition) {
                    logDb.insert_mblling("DOKPLP", tgl.UNIXNUMBER(), model.getCAR(), fStream);
                }
                result = "SUCCESS";
            }
        } catch (Exception ex) {
        } finally {

            return result;
        }
    }
}
