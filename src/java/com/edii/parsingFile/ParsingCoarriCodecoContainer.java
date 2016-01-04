/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingFile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelCoarriCodecoContainer;
import com.edii.operation.db.operation;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingCoarriCodecoContainer {

    private ParsingXML px;
    private ModelCoarriCodecoContainer model;
    private SaveData sv;

    public String parseDocument(String fStream, String sementara) throws Exception {
        String result = null;
        px = new ParsingXML();
        model = new ModelCoarriCodecoContainer();
        sv = new operation();
        try {
            if (fStream != null) {
                String header = px.getStringParsingXml(fStream, "COCOCONT>HEADER,", "KD_DOK,KD_TPS,"
                        + "NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER,");
                String[] split_header = header.replace(";", "").split(",");
                model.setKd_dok(split_header[0]);
                model.setKd_tps(split_header[1]);
                model.setNm_angkut(split_header[2]);
                model.setNo_voy_flight(split_header[3]);
                model.setCall_sign(split_header[4]);
                model.setTgl_tiba(split_header[5]);
                model.setKd_gudang(split_header[6]);
                model.setRef_number(split_header[7]);
                String id = sv.savedata_coarricodecocontianer_header(model);

                String detil_cont = px.getStringParsingXml(fStream, "COCOCONT>DETIL>CONT",
                        "NO_CONT,UK_CONT,NO_SEGEL,JNS_CONT,NO_BL_AWB,TGL_BL_AWB,NO_MASTER_BL_AWB,"
                        + "TGL_MASTER_BL_AWB,ID_CONSIGNEE,CONSIGNEE,BRUTO,NO_BC11,TGL_BC11,NO_POS_BC11,"
                        + "KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,WK_INOUT,KD_SAR_ANGKUT_INOUT,"
                        + "NO_POL,FL_CONT_KOSONG,ISO_CODE,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,GUDANG_TUJUAN,"
                        + "KODE_KANTOR,NO_DAFTAR_PABEAN,TGL_DAFTAR_PABEAN,NO_SEGEL_BC,TGL_SEGEL_BC,"
                        + "NO_IJIN_TPS,TGL_IJIN_TPS");

                String[] split_detil_cont = detil_cont.split(";");
                for (String detil : split_detil_cont) {
                    String[] split_cont = detil.split(",");
                    model.setNo_cont(split_cont[0]);
                    model.setUk_cont(split_cont[1]);
                    model.setNo_segel(split_cont[2]);
                    model.setJns_cont(split_cont[3]);
                    model.setNo_bl_awb(split_cont[4]);
                    model.setTgl_bl_awb(split_cont[5]);
                    model.setNo_master_bl_awb(split_cont[6]);
                    model.setTgl_master_bl_awb(split_cont[7]);
                    model.setId_consignee(split_cont[8]);
                    model.setConsignee(split_cont[9]);
                    model.setBruto(split_cont[10]);
                    model.setNo_bc11(split_cont[11]);
                    model.setTgl_bc11(split_cont[12]);
                    model.setNo_pos_bc11(split_cont[13]);
                    model.setKd_timbun(split_cont[14]);
                    model.setKd_dok_inout(split_cont[15]);
                    model.setNo_dok_inout(split_cont[16]);
                    model.setTgl_dok_inout(split_cont[17]);
                    model.setWk_inout(split_cont[18]);
                    model.setKd_sar_angkut_inout(split_cont[19]);
                    model.setNo_pol(split_cont[20]);
                    model.setFl_cont_kosong(split_cont[21]);
                    model.setIso_code(split_cont[22]);
                    model.setPel_muat(split_cont[23]);
                    model.setPel_transit(split_cont[24]);
                    model.setPel_bongkar(split_cont[25]);
                    model.setGudang_tujuan(split_cont[26]);
                    model.setKode_kantor(split_cont[27]);
                    model.setNo_daftar_pabean(split_cont[28]);
                    model.setTgl_daftar_pabean(split_cont[29]);
                    model.setNo_segel_bc(split_cont[30]);
                    model.setTgl_segel_bc(split_cont[31]);
                    model.setNo_ijin_tps(split_cont[32]);
                    model.setTgl_ijin_tps(split_cont[33]);
                    sv.savedata_coarricodecocontianer_cont(model, id);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
