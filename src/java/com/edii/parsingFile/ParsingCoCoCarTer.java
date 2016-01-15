/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelCoCoCarTer;
import com.edii.operation.db.logDatabase;
import com.edii.operation.db.operation;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingCoCoCarTer {

    private ParsingXML px;
    private ModelCoCoCarTer model;
    private SaveData sv;
    logDatabase logDb;

    public String parseDocument(String fStream) throws Exception {

        String result = null;
        px = new ParsingXML();
        model = new ModelCoCoCarTer();
        logDb = new logDatabase();
        sv = new operation();
        try {

            if (fStream != null) {
                ArrayList<String> header = new ArrayList();
                header = px.xmlParsing(fStream, "COCOCAR>HEADER,", "KD_DOK,KD_TPS,"
                        + "NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER");
                for (String headers : header) {
                    String[] split_header = headers.split(",");
                    model.setKd_dok(split_header[0]);
                    model.setKd_tps(split_header[1]);
                    model.setNm_angkut(split_header[2]);
                    model.setNo_voy_flight(split_header[3]);
                    model.setCall_sign(split_header[4]);
                    model.setTgl_tiba(split_header[5]);
                    model.setKd_gudang(split_header[6]);
                    model.setRef_number(split_header[7]);
                    String id = sv.savedata_cococarter_header(model);
                    ArrayList<String> detil_cont = new ArrayList();
                    detil_cont = px.xmlParsing(fStream, "COCOCAR>DETIL>CAR",
                            "NO_BL_AWB,TGL_BL_AWB,ID_CONSIGNEE,CONSIGNEE,NO_BC11,TGL_BC11,NO_POS_BC11,VIN_NUMBER,"
                            + "NO_RANGKA,NO_MESIN,TIPE,WARNA,MERK,BRUTO,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,"
                            + "TGL_DOK_INOUT,WK_INOUT,KD_SAR_ANGKUT_INOUT,NO_POL,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,"
                            + "GUDANG_TUJUAN");
                    for (String detil : detil_cont) {
                        String[] split_cont = detil.split(",");
                        model.setNo_bl_awb(split_cont[0]);
                        model.setTgl_bl_awb(split_cont[1]);
                        model.setId_consignee(split_cont[2].replaceAll("[.-:]", ""));
                        model.setConsignee(split_cont[3]);
                        model.setNo_bc11(split_cont[4].replaceAll(" ", ""));
                        model.setTgl_bc11(split_cont[5]);
                        model.setNo_pos_bc11(split_cont[6]);
                        model.setVin_number(split_cont[7]);
                        model.setNo_rangka(split_cont[8]);
                        model.setNo_mesin(split_cont[9]);
                        model.setTipe(split_cont[10]);
                        model.setWarna(split_cont[11]);
                        model.setMerk(split_cont[12]);
                        model.setBruto(split_cont[13]);
                        model.setKd_timbun(split_cont[14]);
                        model.setKd_dok_inout(split_cont[15]);
                        model.setNo_dok_inout(split_cont[16]);
                        model.setTgl_dok_inout(split_cont[17]);
                        model.setWk_inout(split_cont[18]);
                        model.setKd_sar_angkut_inout(split_cont[19]);
                        model.setNo_pol(split_cont[20]);
                        model.setPel_muat(split_cont[21]);
                        model.setPel_transit(split_cont[22]);
                        model.setPel_bongkar(split_cont[23]);
                        model.setGudang_tujuan(split_cont[24]);
                        sv.savedata_cococarter_cont(model, id);
                    }
                }
                result = "SUCCESS";
            }
        } catch (Exception ex) {
            boolean hasil = logDb.excuteLogError(ex.getMessage(), "execute_class_parsing_coarri_codeco_container", "CoarriCodeco_Kemasan");
            result = "NOT SUCCESS";
        }
        return null;
    }
}
