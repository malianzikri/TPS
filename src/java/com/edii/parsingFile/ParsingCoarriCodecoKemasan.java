/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingFile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelCoarriCodecoKemasan;
import com.edii.operation.db.operation;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingCoarriCodecoKemasan {

    private ParsingXML px;
    private ModelCoarriCodecoKemasan model;
    private SaveData sv;

    public String parseDocument(String fStream) throws Exception {
        String result = null;
        px = new ParsingXML();
        model = new ModelCoarriCodecoKemasan();
        sv = new operation();
        try {
            if (fStream != null) {
                String header = px.getStringParsingXml(fStream, "COCOCONT>HEADER", "KD_DOK,KD_TPS,"
                        + "NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER");
                String[] split_header = header.replace(";", "").split(",");
                model.setKd_dok(split_header[0]);
                model.setKd_tps(split_header[1]);
                model.setNm_angkut(split_header[3]);
                model.setNo_voy_flight(split_header[4]);
                model.setCall_sign(split_header[5]);
                model.setTgl_tiba(split_header[6]);
                model.setKd_gudang(split_header[7]);
                model.setRef_number(split_header[8]);
                String id = sv.savedata_coarricodecokemasan_header(model);


                String detil_kms = px.getStringParsingXml(fStream, "COCOKMS>KMS>KMS",
                        "NO_BL_AWB,TGL_BL_AWB,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,ID_CONSIGNEE,"
                        + "CONSIGNEE,BRUTO,NO_BC11,TGL_BC11,NO_POS_BC11,CONT_ASAL,SERI_KEMAS,"
                        + "KD_KEMAS,JML_KEMAS,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,"
                        + "WK_INOUT,KD_SAR_ANGKUT_INOUT,NO_POL,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,"
                        + "GUDANG_TUJUAN,KODE_KANTOR,NO_DAFTAR_PABEAN,TGL_DAFTAR_PABEAN,NO_SEGEL_BC,"
                        + "TGL_SEGEL_BC,NO_IJIN_TPS,TGL_IJIN_TPS");
                String[] split_detil_kms = detil_kms.split(";");
                for (String detil : split_detil_kms) {
                    String[] split_kms = detil.split(",");
                    model.setNo_bl_awb(split_kms[0]);
                    model.setTgl_bl_awb(split_kms[1]);
                    model.setNo_master_bl_awb(split_kms[2]);
                    model.setTgl_master_bl_awb(split_kms[3]);
                    model.setId_consignee(split_kms[4]);
                    model.setConsignee(split_kms[5]);
                    model.setBruto(split_kms[6]);
                    model.setNo_bc11(split_kms[7]);
                    model.setTgl_bc11(split_kms[8]);
                    model.setNo_pos_bc11(split_kms[9]);
                    model.setCont_asal(split_kms[10]);
                    model.setSeri_kemas(split_kms[11]);
                    model.setKd_kemas(split_kms[12]);
                    model.setJml_kemas(split_kms[13]);
                    model.setKd_timbun(split_kms[14]);
                    model.setKd_dok_inout(split_kms[15]);
                    model.setNo_dok_inout(split_kms[16]);
                    model.setTgl_dok_inout(split_kms[17]);
                    model.setWk_inout(split_kms[18]);
                    model.setKd_sar_angkut_inout(split_kms[19]);
                    model.setNo_pol(split_kms[20]);
                    model.setPel_muat(split_kms[21]);
                    model.setPel_transit(split_kms[22]);
                    model.setPel_bongkar(split_kms[23]);
                    model.setGudang_tujuan(split_kms[24]);
                    model.setKode_kantor(split_kms[25]);
                    model.setNo_daftar_pabean(split_kms[26]);
                    model.setTgl_daftar_pabean(split_kms[27]);
                    model.setNo_segel_bc(split_kms[28]);
                    model.setTgl_segel_bc(split_kms[29]);
                    model.setNo_ijin_tps(split_kms[30]);
                    model.setTgl_ijin_tps(split_kms[31]);
                    sv.savedata_coarricodecokemasan_kms(model, id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
